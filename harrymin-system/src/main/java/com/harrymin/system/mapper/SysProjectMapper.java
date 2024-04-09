package com.harrymin.system.mapper;

import com.harrymin.common.core.domain.entity.SysProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目信息 数据层
 *
 * @author harrymin
 */
@Mapper
@Repository
public interface SysProjectMapper {

    /**
     * 根据条件分页查询项目列表
     *
     * @param project 项目信息
     * @return 项目信息集合信息
     */
    List<SysProject> selectProjectList(@Param("project") SysProject project);

    /**
     * 通过项目ID查询用户
     *
     * @param projectId 项目ID
     * @return 项目对象信息
     */
    SysProject selectProjectById(@Param("projectId") Long projectId);

    /**
     * 校验项目编码是否唯一
     *
     * @param projectCode 项目编码
     * @return 结果
     */
    SysProject checkProjectCodeUnique(@Param("projectCode") String projectCode);

    /**
     * 通过项目编码查询项目
     *
     * @param projectCode 项目编码
     * @return 项目对象信息
     */
    SysProject selectProjectByProjectCode(@Param("projectCode") String projectCode);

    /**
     * 新增项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    int insertProject(@Param("project") SysProject project);

    /**
     * 修改项目信息
     *
     * @param project 项目信息
     * @return 结果
     */
    int updateProject(@Param("project") SysProject project);

    /**
     * 批量删除项目信息
     *
     * @param projectIds 需要删除的项目ID
     * @return 结果
     */
    int deleteProjectByIds(@Param("projectIds") Long[] projectIds);

    /**
     * 根据申请部门ID查询申请部门名称
     *
     * @param projectApplyDept 申请部门ID
     * @return 申请部门名称
     */
    String selectApplyDeptNameById(@Param("projectApplyDept") Long projectApplyDept);

    /**
     * 根据承接部门ID查询承接部门名称
     *
     * @param projectTakeDept 承接部门ID
     * @return 承接部门名称
     */
    String selectTakeDeptNameById(@Param("projectTakeDept") Long projectTakeDept);
}
