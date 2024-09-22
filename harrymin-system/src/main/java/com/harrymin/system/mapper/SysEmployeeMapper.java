package com.harrymin.system.mapper;

import com.harrymin.common.core.domain.entity.SysEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工信息 数据层
 *
 * @author harrymin
 */
@Mapper
@Repository
public interface SysEmployeeMapper {
    /**
     * 根据条件分页查询员工列表
     *
     * @param sysEmployee 员工信息
     * @return 员工信息集合信息
     */
    public List<SysEmployee> selectEmployeeList(SysEmployee sysEmployee);

}
