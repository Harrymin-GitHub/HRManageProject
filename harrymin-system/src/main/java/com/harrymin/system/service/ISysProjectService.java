package com.harrymin.system.service;

import com.harrymin.common.core.domain.entity.SysProject;

import java.util.List;

/**
 * 项目信息 服务层
 *
 * @author harrymin
 */
public interface ISysProjectService {
    /**
     * 根据条件分页查询项目列表
     *
     * @param project 用户信息
     * @return 项目信息集合信息
     */
    List<SysProject> getProjectList(SysProject project);

    /**
     * 导入项目数据
     *
     * @param projectList       项目数据列表
     * @param isUpdateSupport   是否更新支持，如果已存在，则进行更新数据
     * @param operName          操作项目
     * @return 结果
     */
    String importProject(List<SysProject> projectList, boolean isUpdateSupport, String operName);

    /**
     * 通过项目ID查询用户
     *
     * @param projectId 项目ID
     * @return 项目对象信息
     */
    SysProject getProjectById(Long projectId);

    /**
     * 校验项目编码是否唯一
     *
     * @param project 项目信息
     * @return 结果
     */
    boolean checkProjectCodeUnique(SysProject project);

    /**
     * 新增项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    int addProject(SysProject project);

    /**
     * 修改项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    int modifyProject(SysProject project);

    /**
     * 批量删除项目信息
     *
     * @param projectIds 需要删除的项目ID
     * @return 结果
     */
    int removeProjectByIds(Long[] projectIds);
}
