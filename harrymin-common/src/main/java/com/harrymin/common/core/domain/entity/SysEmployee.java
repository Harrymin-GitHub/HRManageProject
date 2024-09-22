package com.harrymin.common.core.domain.entity;

import com.harrymin.common.annotation.Excel;
import com.harrymin.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 项目人员角色表 sys_employee
 *
 * @author Harrymin
 */
public class SysEmployee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @Excel(name = "员工序号", cellType = Excel.ColumnType.NUMERIC, prompt = "员工编号")
    private Long employeeId;

    /**
     * 用户ID
     */
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /**
     * 考勤ID
     */
    @Excel(name = "考勤序号", cellType = Excel.ColumnType.NUMERIC, prompt = "考勤编号")
    private Long attendanceId;

    /**
     * 员工编码
     */
    private String employeeCode;

    /**
     * 员工状态
     */
    private String employeeStatus;

    /**
     * 员工职责
     */
    private String employeeDuty;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工的姓
     */
    private String employeeSurname;

    /**
     * 员工的名
     */
    private String employeeLastname;

    /**
     * 员工生日
     */
    private Date employeeBirthday;

    /**
     * 员工籍贯
     */
    private String employeeNativePlace;

    /**
     * 员工户口所在地
     */
    private String employeeResidence;

    /**
     * 员工现住址
     */
    private String employeeAddress;

    /**
     * 员工身份证号
     */
    private String employeeIDCard;

    /**
     * 员工毕业学校
     */
    private String employeeSchool;

    /**
     * 员工毕业时间
     */
    private Date employeeGraduationDate;

    /**
     * 员工最终学历
     */
    private String employeeEducation;

    /**
     * 员工所学专业
     */
    private String employeeMajor;

    /**
     * 员工婚否
     */
    private int employeeIsMarried;

    /**
     * 员工入职日期
     */
    private Date employeeJoinedDate;

    /**
     * 员工签订合同年数
     */
    private float employeeContactYears;

    /**
     * 员工工资
     */
    private float employeeSalary;

    /**
     * 员工是否参与项目
     */
    private int employeeIsProjected;

    /**
     * 员工入项时间
     */
    private int employeeJoinProjectDate;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeDuty() {
        return employeeDuty;
    }

    public void setEmployeeDuty(String employeeDuty) {
        this.employeeDuty = employeeDuty;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public String getEmployeeLastname() {
        return employeeLastname;
    }

    public void setEmployeeLastname(String employeeLastname) {
        this.employeeLastname = employeeLastname;
    }

    public Date getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(Date employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeNativePlace() {
        return employeeNativePlace;
    }

    public void setEmployeeNativePlace(String employeeNativePlace) {
        this.employeeNativePlace = employeeNativePlace;
    }

    public String getEmployeeResidence() {
        return employeeResidence;
    }

    public void setEmployeeResidence(String employeeResidence) {
        this.employeeResidence = employeeResidence;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeIDCard() {
        return employeeIDCard;
    }

    public void setEmployeeIDCard(String employeeIDCard) {
        this.employeeIDCard = employeeIDCard;
    }

    public String getEmployeeSchool() {
        return employeeSchool;
    }

    public void setEmployeeSchool(String employeeSchool) {
        this.employeeSchool = employeeSchool;
    }

    public Date getEmployeeGraduationDate() {
        return employeeGraduationDate;
    }

    public void setEmployeeGraduationDate(Date employeeGraduationDate) {
        this.employeeGraduationDate = employeeGraduationDate;
    }

    public String getEmployeeEducation() {
        return employeeEducation;
    }

    public void setEmployeeEducation(String employeeEducation) {
        this.employeeEducation = employeeEducation;
    }

    public String getEmployeeMajor() {
        return employeeMajor;
    }

    public void setEmployeeMajor(String employeeMajor) {
        this.employeeMajor = employeeMajor;
    }

    public int getEmployeeIsMarried() {
        return employeeIsMarried;
    }

    public void setEmployeeIsMarried(int employeeIsMarried) {
        this.employeeIsMarried = employeeIsMarried;
    }

    public Date getEmployeeJoinedDate() {
        return employeeJoinedDate;
    }

    public void setEmployeeJoinedDate(Date employeeJoinedDate) {
        this.employeeJoinedDate = employeeJoinedDate;
    }

    public float getEmployeeContactYears() {
        return employeeContactYears;
    }

    public void setEmployeeContactYears(float employeeContactYears) {
        this.employeeContactYears = employeeContactYears;
    }

    public float getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(float employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeIsProjected() {
        return employeeIsProjected;
    }

    public void setEmployeeIsProjected(int employeeIsProjected) {
        this.employeeIsProjected = employeeIsProjected;
    }

    public int getEmployeeJoinProjectDate() {
        return employeeJoinProjectDate;
    }

    public void setEmployeeJoinProjectDate(int employeeJoinProjectDate) {
        this.employeeJoinProjectDate = employeeJoinProjectDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("employeeId", getEmployeeId())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("attendanceId", getAttendanceId())
                .append("employeeCode", getEmployeeCode())
                .append("employeeStatus", getEmployeeStatus())
                .append("employeeDuty", getEmployeeDuty())
                .append("employeeName", getEmployeeName())
                .append("employeeSurname", getEmployeeSurname())
                .append("employeeLastname", getEmployeeLastname())
                .append("employeeBirthday", getEmployeeBirthday())
                .append("employeeNativePlace", getEmployeeNativePlace())
                .append("employeeResidence", getEmployeeResidence())
                .append("employeeAddress", getEmployeeAddress())
                .append("employeeIDCard", getEmployeeIDCard())
                .append("employeeSchool", getEmployeeSchool())
                .append("employeeGraduationDate", getEmployeeGraduationDate())
                .append("employeeEducation", getEmployeeEducation())
                .append("employeeMajor", getEmployeeMajor())
                .append("employeeIsMarried", getEmployeeIsMarried())
                .append("employeeJoinedDate", getEmployeeJoinedDate())
                .append("employeeContactYears", getEmployeeContactYears())
                .append("employeeSalary", getEmployeeSalary())
                .append("employeeIsProjected", getEmployeeIsProjected())
                .append("employeeJoinProjectDate", getEmployeeJoinProjectDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }

}
