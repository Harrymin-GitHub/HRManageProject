package com.harrymin.common.core.domain.entity;

import com.harrymin.common.annotation.Excel;
import com.harrymin.common.annotation.Excels;
import com.harrymin.common.core.domain.BaseEntity;
import com.harrymin.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 项目表 sys_project
 *
 * @author harrymin
 */
public class SysProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目基本信息表id
     */
    @Excel(name = "项目编号", cellType = Excel.ColumnType.NUMERIC, prompt = "项目Id")
    private Long projectId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /**
     * 项目编码id
     */
    @Excel(name = "项目编码")
    private String projectCode;

    /**
     * 项目简称
     */
    @Excel(name = "项目简称")
    private String projectShortName;

    /**
     * 项目全称
     */
    @Excel(name = "项目全称")
    private String projectWholeName;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String projectCustomName;

    /**
     * 申请部门
     */
    @Excel(name = "申请部门", type = Excel.Type.IMPORT)
    private Long projectApplyDept;

    /**
     * 承接部门
     */
    @Excel(name = "承接部门", type = Excel.Type.IMPORT)
    private Long projectTakeDept;

    private String projectApplyDeptName;

    private String projectTakeDeptName;

    /**
     * 项目状态
     */
    @Excel(name = "项目状态", readConverterExp = "1=开发中,2=暂停,3=部门变更,4=已结束,5=废止")
    private String projectStatus;

    /**
     * 项目申请日
     */
    @Excel(name = "项目申请日")
    private Date projectApplyDate;

    /**
     * 预计开始日
     */
    @Excel(name = "预计开始日")
    private Date projectStartPlan;

    /**
     * 预计完成日
     */
    @Excel(name = "预计完成日")
    private Date projectEndPlan;

    /**
     * 实际开始日
     */
    @Excel(name = "实际开始日")
    private Date projectStartDate;

    /**
     * 实际完成日
     */
    @Excel(name = "实际完成日")
    private Date projectEndDate;

    /**
     * 删除标志 (0代表存在 1代表删除)
     */
    private String projectIsDeleted;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    public SysProject() {

    }

    public SysProject(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Xss(message = "项目编码不能包含脚本字符")
    @NotBlank(message = "项目编码不能为空")
    @Size(min = 0, max = 30, message = "项目编码长度不能超过30个字符")
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Xss(message = "项目简称不能包含脚本字符")
    @Size(min = 0, max = 10, message = "项目简称长度不能超过10个字符")
    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    @Xss(message = "项目全称不能包含脚本字符")
    @NotBlank(message = "项目全称不能为空")
    @Size(min = 0, max = 20, message = "项目全称长度不能超过20个字符")
    public String getProjectWholeName() {
        return projectWholeName;
    }

    public void setProjectWholeName(String projectWholeName) {
        this.projectWholeName = projectWholeName;
    }

    public String getProjectCustomName() {
        return projectCustomName;
    }

    public void setProjectCustomName(String projectCustomName) {
        this.projectCustomName = projectCustomName;
    }

    public Long getProjectApplyDept() {
        return projectApplyDept;
    }

    public void setProjectApplyDept(Long projectApplyDept) {
        this.projectApplyDept = projectApplyDept;
    }

    public Long getProjectTakeDept() {
        return projectTakeDept;
    }

    public void setProjectTakeDept(Long projectTakeDept) {
        this.projectTakeDept = projectTakeDept;
    }

    public String getProjectApplyDeptName() {
        return projectApplyDeptName;
    }

    public void setProjectApplyDeptName(String projectApplyDeptName) {
        this.projectApplyDeptName = projectApplyDeptName;
    }

    public String getProjectTakeDeptName() {
        return projectTakeDeptName;
    }

    public void setProjectTakeDeptName(String projectTakeDeptName) {
        this.projectTakeDeptName = projectTakeDeptName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getProjectApplyDate() {
        return projectApplyDate;
    }

    public void setProjectApplyDate(Date projectApplyDate) {
        this.projectApplyDate = projectApplyDate;
    }

    public Date getProjectStartPlan() {
        return projectStartPlan;
    }

    public void setProjectStartPlan(Date projectStartPlan) {
        this.projectStartPlan = projectStartPlan;
    }

    public Date getProjectEndPlan() {
        return projectEndPlan;
    }

    public void setProjectEndPlan(Date projectEndPlan) {
        this.projectEndPlan = projectEndPlan;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProjectIsDeleted() {
        return projectIsDeleted;
    }

    public void setProjectIsDeleted(String projectIsDeleted) {
        this.projectIsDeleted = projectIsDeleted;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectId", getProjectId())
                .append("deptId", getDeptId())
                .append("projectCode", getProjectCode())
                .append("projectShortName", getProjectShortName())
                .append("projectWholeName", getProjectWholeName())
                .append("projectCustomName", getProjectCustomName())
                .append("projectApplyDeptName", getProjectApplyDeptName())
                .append("projectTakeDeptName", getProjectTakeDeptName())
                .append("projectStatus", getProjectStatus())
                .append("projectApplyDate", getProjectApplyDate())
                .append("projectStartPlan", getProjectStartPlan())
                .append("projectEndPlan", getProjectEndPlan())
                .append("projectStartDate", getProjectStartDate())
                .append("projectEndDate", getProjectEndDate())
                .append("projectIsDeleted", getProjectIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("dept", getDept())
                .toString();
    }
}
