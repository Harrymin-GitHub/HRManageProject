package com.harrymin.system.service.impl;

import com.harrymin.common.annotation.DataScope;
import com.harrymin.common.core.domain.entity.SysEmployee;
import com.harrymin.system.mapper.SysEmployeeMapper;
import com.harrymin.system.service.ISysEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工管理 服务实现
 *
 * @author harrymin
 */
@Service
public class SysEmployeeServiceImpl implements ISysEmployeeService {
    private static final Logger log = LoggerFactory.getLogger(SysEmployeeServiceImpl.class);

    @Autowired
    private SysEmployeeMapper employeeMapper;

    /**
     * 根据条件分页查询员工列表
     *
     * @param employee 员工信息
     * @return 员工信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", employeeAlias = "emp")
    public List<SysEmployee> selectEmployeeList(SysEmployee employee) {
        return employeeMapper.selectEmployeeList(employee);
    }

}
