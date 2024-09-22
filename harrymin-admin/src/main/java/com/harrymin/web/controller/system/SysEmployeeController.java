package com.harrymin.web.controller.system;


import com.harrymin.common.annotation.Log;
import com.harrymin.common.core.controller.BaseController;
import com.harrymin.common.core.domain.AjaxResult;
import com.harrymin.common.core.domain.entity.SysDept;
import com.harrymin.common.core.domain.entity.SysEmployee;
import com.harrymin.common.core.page.TableDataInfo;
import com.harrymin.common.enums.BusinessType;
import com.harrymin.system.service.ISysDeptService;
import com.harrymin.system.service.ISysEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工信息
 *
 * @author harrymin
 */
@RestController
@RequestMapping("/system/employee")
public class SysEmployeeController extends BaseController {
    @Autowired
    private ISysEmployeeService employeeService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取员工列表
     */
    @PreAuthorize("@ss.hasPermi('system:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEmployee employee) {
        startPage();
        List<SysEmployee> list = employeeService.selectEmployeeList(employee);
        return getDataTable(list);
    }

    /**
     * 新增员工
     */
    @PreAuthorize("@ss.hasPermi('system:employee:add')")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysEmployee employee) {
        return null;
    }

    /**
     * 修改员工
     */
    @PreAuthorize("@ss.hasPermi('system:employee:edit')")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysEmployee employee) {
        return null;
    }

    /**
     * 删除员工
     */
    @PreAuthorize("@ss.hasPermi('system:employee:remove')")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{employeeIds}")
    public AjaxResult remove(@PathVariable Long[] employeeIds) {
        return null;
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:employee:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept) {
        return success(deptService.selectDeptTreeList(dept));
    }
}
