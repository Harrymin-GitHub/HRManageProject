<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--员工数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="员工编号" prop="employeeName">
            <el-input
              v-model="queryParams.employeeCode"
              placeholder="请输入员工编号"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="员工名称" prop="phonenumber">
            <el-input
              v-model="queryParams.employeeName"
              placeholder="请输入员工名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="员工状态" prop="employeeStatus">
            <el-select
              v-model="queryParams.employeeStatus"
              placeholder="员工状态"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:employee:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:employee:edit']"
            >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:employee:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:employee:import']"
            >导入
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:employee:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="员工id" align="center" key="employeeId" prop="employeeId" v-if="showEmployeeId"/>
          <el-table-column label="员工编号" align="center" key="employeeCode" prop="employeeCode" v-if="columns[0].visible"/>
          <el-table-column label="员工名称" align="center" key="employeeName" prop="employeeName" v-if="columns[1].visible"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="员工职责" align="center" key="employeeDuty" prop="employeeDuty" v-if="columns[2].visible"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="员工现住址" align="center" key="employeeAddress" prop="employeeAddress"
                           v-if="columns[4].visible" width="120"/>
          <el-table-column label="员工状态" align="center" key="employeeStatus" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.employeeStatus"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.employeeId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:employee:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:employee:remove']"
              >删除
              </el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                           v-hasPermi="['system:employee:moreDetail']">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleMoreDetail" icon="el-icon-view"
                                    v-hasPermi="['system:employee:moreDetail']">员工详情
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改员工配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 员工导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的员工数据
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
import {
  deptTreeSelect,
  listEmployee,
} from "@/api/system/employee";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Employee",
  dicts: ['sys_normal_disable', 'sys_employee_sex'],
  components: {Treeselect},
  data() {
    return {
      // 员工id列不显示
      showEmployeeId: false,
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
      // 员工表格数据
      employeeList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 员工导入参数
      upload: {
        // 是否显示弹出层（员工导入）
        open: false,
        // 弹出层标题（员工导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的员工数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/employee/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        employeeCode: undefined,
        employeeName: undefined,
        employeeStatus: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        {key: 0, label: `员工编号`, visible: true},
        {key: 1, label: `员工名称`, visible: true},
        {key: 2, label: `员工职责`, visible: true},
        {key: 3, label: `部门`, visible: true},
        {key: 4, label: `手机号码`, visible: true},
        {key: 5, label: `员工状态`, visible: true},
        {key: 6, label: `创建时间`, visible: true}
      ],
      // 表单校验
      rules: {

      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询员工列表 */
    getList() {
      this.loading = true;
      listEmployee(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.employeeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
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
    // 员工状态修改
    handleStatusChange(row) {

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        employeeId: undefined,
        employeeCode: undefined,
        employeeName: undefined,
        employeeDuty: undefined,
        employeeStatus: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.employeeId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleMoreDetail":
          this.handleMoreDetail(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {

    },
    /** 修改按钮操作 */
    handleUpdate(row) {

    },
    /** 查看员工详情操作 */
    handleMoreDetail: function (row) {
      const employeeId = row.employeeId;
      this.$router.push("/system/employee/moreDetail/" + employeeId);
    },
    /** 提交按钮 */
    submitForm: function () {

    },
    /** 删除按钮操作 */
    handleDelete(row) {

    },
    /** 导出按钮操作 */
    handleExport() {

    },
    /** 导入按钮操作 */
    handleImport() {

    },
    /** 下载模板操作 */
    importTemplate() {

    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {

    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {

    },
    // 提交上传文件
    submitFileForm() {

    }
  }
};
</script>
