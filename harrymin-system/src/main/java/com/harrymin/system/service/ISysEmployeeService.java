package com.harrymin.system.service;

import com.harrymin.common.core.domain.entity.SysEmployee;

import java.util.List;

/**
 * 员工信息 服务层
 *
 * @author harrymin
 */
public interface ISysEmployeeService {
    /**
     * 根据条件分页查询员工列表
     *
     * @param employee 员工信息
     * @return 员工信息集合信息
     */
    public List<SysEmployee> selectEmployeeList(SysEmployee employee);

}
