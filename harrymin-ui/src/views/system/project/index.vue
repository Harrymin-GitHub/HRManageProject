<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName"
                    placeholder="请输入部门名称"
                    clearable
                    size="small"
                    prefix-icon="el-icon-search"
                    style="margin-bottom: 20px"/>
        </div>
        <div class="head-container">
          <el-tree :data="deptOptions" :props="defaultProps"
                   :expand-on-click-node="false"
                   :filter-node-method="filterNode"
                   ref="tree" node-key="id"
                   default-expand-all highlight-current
                   @node-click="handleNodeClick"/>
        </div>
      </el-col>
      <!--项目数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="项目编码" prop="projectCode">
            <el-input v-model="queryParams.projectCode"
                      placeholder="请输入项目编码"
                      clearable style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="项目全称" prop="projectWholeName">
            <el-input v-model="queryParams.projectWholeName"
                      placeholder="请输入项目全称"
                      clearable style="width: 240px"
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="项目状态" prop="projectStatus">
            <el-select v-model="queryParams.projectStatus"
                       placeholder="选择项目状态"
                       clearable style="width: 240px"
                       @keyup.enter.native="handleQuery">
              <el-option
                v-for="dict in dict.type.sys_project_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目周期">
            <el-date-picker v-model="projectDateRange"
                            style="width: 240px"
                            value-format="yyyy-MM-dd"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="实际开始日期"
                            end-placeholder="实际结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                       v-hasPermi="['system:project:add']">新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                       v-hasPermi="['system:project:edit']">修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                       v-hasPermi="['system:project:remove']">删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport"
                       v-hasPermi="['system:project:import']">导入
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                       v-hasPermi="['system:project:export']">导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getProjectList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="项目编号" align="center" key="projectId" prop="projectId"
                           v-if="columns[0].visible"/>
          <el-table-column label="项目编码" align="center" key="projectCode" prop="projectCode"
                           v-if="columns[1].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="项目简称" align="center" key="projectShortName" prop="projectShortName"
                           v-if="columns[2].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="项目全称" align="center" key="projectWholeName" prop="projectWholeName"
                           v-if="columns[3].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="客户名称" align="center" key="projectCustomName" prop="projectCustomName"
                           v-if="columns[4].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="申请部门" align="center" key="projectApplyDeptName" prop="projectApplyDeptName"
                           v-if="columns[5].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="承接部门" align="center" key="projectTakeDeptName" prop="projectTakeDeptName"
                           v-if="columns[6].visible" :show-overflow-tooltip="true"/>
          <el-table-column label="项目申请日" align="center" key="projectApplyDate" prop="projectStartPlan"
                           v-if="columns[7].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.projectApplyDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="预计开始日" align="center" key="projectStartPlan" prop="projectStartPlan"
                           v-if="columns[8].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.projectStartPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="预计完成日" align="center" key="projectEndPlan" prop="projectEndPlan"
                           v-if="columns[9].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.projectEndPlan, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际开始日" align="center" key="projectStartDate" prop="projectStartDate"
                           v-if="columns[10].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.projectStartDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际完成日" align="center" key="projectEndDate" prop="projectEndDate"
                           v-if="columns[11].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.projectEndDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="项目状态" align="center" key="projectStatus" prop="projectStatus"
                           v-if="columns[12].visible" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <p v-if="scope.row.projectStatus === '1'">开发中</p>
              <p v-if="scope.row.projectStatus === '2'">暂停</p>
              <p v-if="scope.row.projectStatus === '3'">部门变更</p>
              <p v-if="scope.row.projectStatus === '4'">已结束</p>
              <p v-if="scope.row.projectStatus === '5'">废止</p>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit"
                         @click="handleUpdate(scope.row)"
                         v-hasPermi="['system:project:edit']">修改
              </el-button>
              <el-button size="mini" type="text" icon="el-icon-delete"
                         @click="handleDelete(scope.row)"
                         v-hasPermi="['system:project:remove']">删除
              </el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                           v-hasPermi="['system:project:edit']">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleEmployee" icon="el-icon-circle-check"
                                    v-hasPermi="['system:project:edit']">分配员工
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize" @pagination="getProjectList"/>
      </el-col>
    </el-row>

    <!-- 添加或修改项目配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="projectFormRef" :model="projectForm" :rules="projectRules" :inline="true" label-width="85px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input
                v-model="projectForm.projectCode" placeholder="填写项目编码" maxlength="30"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目简称" prop="projectShortName">
              <el-input
                v-model="projectForm.projectShortName" placeholder="填写项目简称" maxlength="30"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目全称" prop="projectWholeName">
              <el-input
                v-model="projectForm.projectWholeName" placeholder="填写项目全称" maxlength="30"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="projectCustomName">
              <el-input
                v-model="projectForm.projectCustomName" placeholder="填写客户名称" maxlength="30"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请部门" prop="projectApplyDept">
              <treeselect v-model="projectForm.projectApplyDept"
                          :options="applyDeptOptions" :show-count="true"
                          placeholder="请选择申请部门"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="承接部门" prop="projectTakeDept">
              <treeselect v-model="projectForm.projectTakeDept"
                          :options="takeDeptOptions" :show-count="true"
                          placeholder="请选择承接部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目状态" prop="projectStatus">
              <el-select v-model="projectForm.projectStatus" placeholder="选择项目状态" maxlength="30">
                <el-option
                  v-for="dict in dict.type.sys_project_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目申请日" prop="projectApplyDate">
              <el-date-picker
                v-model="projectForm.projectApplyDate"
                placeholder="选择日期" maxlength="10"
                type="date" value-format="yyyy-MM-dd"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="预计开始日" prop="projectStartPlan">
              <el-date-picker
                v-model="projectForm.projectStartPlan" placeholder="选择日期" maxlength="30"
                type="date" value-format="yyyy-MM-dd"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预计完成日" prop="projectEndPlan">
              <el-date-picker
                v-model="projectForm.projectEndPlan" placeholder="选择日期" maxlength="30"
                type="date" value-format="yyyy-MM-dd"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="实际开始日" prop="projectStartDate">
              <el-date-picker
                v-model="projectForm.projectStartDate" placeholder="选择日期" maxlength="30"
                type="date" value-format="yyyy-MM-dd"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际完成日" prop="projectEndDate">
              <el-date-picker
                v-model="projectForm.projectEndDate" placeholder="选择日期" maxlength="30"
                type="date" value-format="yyyy-MM-dd"
                :disabled="(projectForm.projectStatus === '4' || projectForm.projectStatus === '3' || projectForm.projectStatus === '5')"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>

    <!-- 项目导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的项目数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {addProject, delProject, deptTreeSelect, getProject, listProject, updateProject} from "@/api/system/project";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";
import {parseTime} from "@/utils/harrymin";

export default {
  name: "Project",
  dicts: ['sys_project_type', 'sys_project_status'],
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目表格数据
      projectList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      applyDeptOptions: undefined,
      takeDeptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 项目周期
      projectDateRange: [],
      // 表单参数
      projectForm: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 项目导入参数
      upload: {
        // 是否显示弹出层（项目导入）
        open: false,
        // 弹出层标题（项目导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的项目数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/project/importData"
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectCode: undefined,
        projectWholeName: undefined,
        projectStatus: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        {key: 0, label: `项目编号`, visible: true},
        {key: 1, label: `项目编码`, visible: true},
        {key: 2, label: `项目简称`, visible: true},
        {key: 3, label: `项目全称`, visible: true},
        {key: 4, label: `客户名称`, visible: true},
        {key: 5, label: `申请部门`, visible: true},
        {key: 6, label: `承接部门`, visible: true},
        {key: 7, label: `项目申请日`, visible: true},
        {key: 8, label: `预计开始日`, visible: true},
        {key: 9, label: `预计完成日`, visible: true},
        {key: 10, label: `实际开始日`, visible: true},
        {key: 11, label: `实际完成日`, visible: true},
        {key: 12, label: `项目状态`, visible: true}
      ],
      // 表单校验
      projectRules: {
        projectCode: [
          {required: true, message: "项目编码不能为空", trigger: "blur"},
          {min: 0, max: 30, message: '项目编码长度必须介于 0 和 30 之间', trigger: 'blur'}
        ],
        projectShortName: [
          {min: 0, max: 10, message: '项目简称长度必须介于 0 和 10 之间', trigger: 'blur'}
        ],
        projectWholeName: [
          {required: true, message: "项目全称不能为空", trigger: "blur"},
          {min: 0, max: 20, message: '项目全称长度必须介于 0 和 20 之间', trigger: 'blur'}
        ]
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getProjectList();
    this.getDeptTree();
  },
  methods: {
    parseTime,
    /** 查询项目列表 */
    getProjectList() {
      this.loading = true;
      listProject(this.addDateRange(this.queryParams, this.projectDateRange)).then(response => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
        this.applyDeptOptions = response.data;
        this.takeDeptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.projectForm = {
        projectId: undefined,
        deptId: undefined,
        projectCode: undefined,
        projectShortName: undefined,
        projectWholeName: undefined,
        projectCustomName: undefined,
        projectApplyDept: undefined,
        projectTakeDept: undefined,
        projectStatus: undefined,
        projectApplyDate: undefined,
        projectStartPlan: undefined,
        projectEndPlan: undefined,
        projectStartDate: undefined,
        projectEndDate: undefined
      };
      this.resetForm("projectForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getProjectList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.projectDateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleCommand(command, row) {
      switch (command) {
        case "handleEmployee":
          this.handleEmployee(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getProject().then(response => {
        this.open = true;
        this.title = "添加项目";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectId = row.projectId || this.ids;
      getProject(projectId).then(response => {
        this.projectForm = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 分配项目人员 */
    handleEmployee: function (row) {
      const projectId = row.projectId
      this.$router.push("/system/project-employee/employee/" + projectId);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["projectFormRef"].validate(valid => {
        if (valid) {
          if (this.projectForm.projectId !== undefined) {
            updateProject(this.projectForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getProjectList();
            });
          } else {
            addProject(this.projectForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getProjectList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const projectIds = row.projectId || this.ids;
      this.$modal.confirm('是否确认删除项目编号为"' + projectIds + '"的数据项？').then(function () {
        return delProject(projectIds);
      }).then(() => {
        this.getProjectList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "项目导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/project/importTemplate', {}, `project_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>"
        + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getProjectList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
