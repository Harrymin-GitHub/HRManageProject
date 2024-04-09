package com.harrymin.system.service.impl;

import com.harrymin.common.constant.ProjectConstants;
import com.harrymin.common.core.domain.entity.SysProject;
import com.harrymin.common.exception.ServiceException;
import com.harrymin.common.utils.StringUtils;
import com.harrymin.system.mapper.SysProjectMapper;
import com.harrymin.system.service.ISysProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目管理 服务实现
 *
 * @author harrymin
 */
@Service
public class SysProjectServiceImpl implements ISysProjectService {

    private static final Logger log = LoggerFactory.getLogger(SysProjectServiceImpl.class);

    @Autowired
    private SysProjectMapper projectMapper;

    /**
     * 根据条件分页查询项目列表
     *
     * @param project 用户信息
     * @return 项目信息集合信息
     */
    @Override
    public List<SysProject> getProjectList(SysProject project) {
        List<SysProject> projectInfo = projectMapper.selectProjectList(project);
        projectInfo.stream().peek(item -> {
            item.setProjectApplyDeptName(projectMapper.selectApplyDeptNameById(item.getProjectApplyDept()));
            item.setProjectTakeDeptName(projectMapper.selectTakeDeptNameById(item.getProjectTakeDept()));
        }).collect(Collectors.toList());
        return projectInfo;
    }

    @Override
    public String importProject(List<SysProject> projectList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(projectList) || projectList.size() == 0) {
            throw new ServiceException("导入项目数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysProject project : projectList) {
            try {
                // 验证是否存在这个用户
                SysProject p = projectMapper.selectProjectByProjectCode(project.getProjectCode());
                if (StringUtils.isNull(p)) {
                    project.setCreateBy(operName);
                    projectMapper.insertProject(project);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、项目编码 " + project.getProjectCode() + " 导入成功");
                } else if (isUpdateSupport) {
                    project.setProjectId(p.getProjectId());
                    project.setUpdateBy(operName);
                    projectMapper.updateProject(project);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、项目编码 " + project.getProjectCode() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、项目编码 " + project.getProjectCode() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目编码 " + project.getProjectCode() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 通过项目ID查询用户
     *
     * @param projectId 项目ID
     * @return 项目对象信息
     */
    @Override
    public SysProject getProjectById(Long projectId) {
        return projectMapper.selectProjectById(projectId);
    }

    /**
     * 校验项目编码是否唯一
     *
     * @param project 项目信息
     * @return 结果
     */
    @Override
    public boolean checkProjectCodeUnique(SysProject project) {
        Long projectId = StringUtils.isNull(project.getProjectId()) ? -1L : project.getProjectId();
        SysProject info = projectMapper.checkProjectCodeUnique(project.getProjectCode());
        if (StringUtils.isNotNull(info) && info.getProjectId().longValue() != projectId.longValue()) {
            return ProjectConstants.NOT_UNIQUE;
        }
        return ProjectConstants.UNIQUE;
    }

    /**
     * 新增项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    @Override
    @Transactional
    public int addProject(SysProject project) {
        int rows = projectMapper.insertProject(project);
        return Math.max(rows, 0);
    }

    /**
     * 修改项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    @Override
    @Transactional
    public int modifyProject(SysProject project) {
        return projectMapper.updateProject(project);
    }

    /**
     * 批量删除项目信息
     *
     * @param projectIds 需要删除的项目ID
     * @return 结果
     */
    @Override
    @Transactional
    public int removeProjectByIds(Long[] projectIds) {
        return projectMapper.deleteProjectByIds(projectIds);
    }

}
