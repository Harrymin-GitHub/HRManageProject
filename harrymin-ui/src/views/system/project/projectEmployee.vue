<template>
  <div class="app-container">
    <h4 class="form-header h4">在项员工</h4>
    <el-form ref="employeeFormRef" :model="employeeForm" label-width="80px">
      <el-row>
        <el-col :span="8" :offset="2">
          <el-form-item label="员工编码" prop="employeeCode">
            <el-input v-model="employeeForm.employeeCode" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="员工姓名" prop="employeeName">
            <el-input v-model="employeeForm.employeeName" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="2">
          <el-form-item label="入项时间" prop="employeeJoinProjectDate">
            <el-input v-model="employeeForm.employeeJoinProjectDate" disabled/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <h4 class="form-header h4">员工信息</h4>
    <el-form :model="queryEmployeeParams" ref="queryEmployeeForm" size="small"
             :inline="true" v-show="showEmployeeSearch"
             label-width="68px">
      <el-form-item label="员工编码" prop="employeeCode">
        <el-input v-model="queryEmployeeParams.employeeCode"
                  placeholder="请输入员工编码"
                  clearable style="width: 240px"
                  @keyup.enter.native="handleEmployeeQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleEmployeeQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" @dblclick="adbClickRow" ref="table"
              @selection-change="handleSelectionChange"
              :data="employeeList">
      <el-table-column type="selection" :reserve-selection="true" width="55"/>
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="员工编号" align="center" prop="employeeId"/>
      <el-table-column label="员工编码" align="center" prop="employeeCode"/>
      <el-table-column label="员工姓名" align="center" prop="employeeName"/>
      <el-table-column label="员工职责" align="center" prop="employeeDuty"/>
      <el-table-column label="入项状态" align="center" prop="employeeIsProjected"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleEnterProject(scope.row)"
                     v-hasPermi="['system:project:enter']" v-if="(scope.row.employeeIsProjected === '未入项')">入项
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleExitProject(scope.row)"
                     v-hasPermi="['system:project:exit']" v-if="(scope.row.employeeIsProjected === '已入项')">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total"
                :page.sync="pageNum"
                :limit.sync="pageSize"/>

    <el-form label-width="100px">
      <el-form-item style="text-align: center; margin-left:-120px; margin-top:30px;">
        <el-button type="primary" @click="save()">保存</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "ProjectEmployee",
  data() {
    return {};
  },
  created() {
  },
  methods: {
    /** 双击行查看用户详细信息 */
    adbClickRow(row) {
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
    },
    /** 提交按钮 */
    save() {
    },
    /** 关闭按钮 */
    close() {
    },
  },
};
</script>
