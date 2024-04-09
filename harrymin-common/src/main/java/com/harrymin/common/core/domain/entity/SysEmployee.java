package com.harrymin.common.core.domain.entity;

import com.harrymin.common.annotation.Excel;

import java.util.Date;

/**
 * 项目人员角色表 sys_employee
 *
 * @author Harrymin
 */
public class SysEmployee {

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
     * 考勤ID
     */
    @Excel(name = "考勤序号", cellType = Excel.ColumnType.NUMERIC, prompt = "考勤编号")
    private Long attendanceId;

    /**
     * 员工编码
     */
    private String employeeCode;

    /**
     * 员工职级
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

}
