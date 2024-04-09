package com.harrymin.web.controller.system;

import com.harrymin.common.annotation.Log;
import com.harrymin.common.core.controller.BaseController;
import com.harrymin.common.core.domain.AjaxResult;
import com.harrymin.common.core.domain.entity.SysDept;
import com.harrymin.common.core.domain.entity.SysProject;
import com.harrymin.common.core.page.TableDataInfo;
import com.harrymin.common.enums.BusinessType;
import com.harrymin.common.utils.StringUtils;
import com.harrymin.common.utils.poi.ExcelUtil;
import com.harrymin.system.service.ISysDeptService;
import com.harrymin.system.service.ISysProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目信息
 *
 * @author harrymin
 */
@RestController
@RequestMapping("/system/project")
public class SysProjectController extends BaseController {

    @Autowired
    private ISysProjectService projectService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProject project) {
        startPage();
        List<SysProject> list = projectService.getProjectList(project);
        return getDataTable(list);
    }

    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:project:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProject project) {
        List<SysProject> list = projectService.getProjectList(project);
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        util.exportExcel(response, list, "项目数据");
    }

    @Log(title = "项目管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:project:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        List<SysProject> projectList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = projectService.importProject(projectList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据项目编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:project:query')")
    @GetMapping(value = {"/", "/{projectId}"})
    public AjaxResult getInfo(@PathVariable(value = "projectId", required = false) Long projectId) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(projectId)) {
            SysProject sysProject = projectService.getProjectById(projectId);
            ajax.put(AjaxResult.DATA_TAG, sysProject);
        }
        return ajax;
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysProject project) {
        if (!projectService.checkProjectCodeUnique(project)) {
            return error("新增项目'" + project.getProjectCode() + "'失败，项目编号已存在");
        }
        project.setCreateBy(getUsername());
        project.setProjectIsDeleted("0");
        return toAjax(projectService.addProject(project));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysProject project) {
        project.setUpdateBy(getUsername());
        return toAjax(projectService.modifyProject(project));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds) {
        return toAjax(projectService.removeProjectByIds(projectIds));
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept) {
        return success(deptService.selectDeptTreeList(dept));
    }

}
