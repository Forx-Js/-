<template>
  <div>
    <h1>应用管理</h1>

    <el-button type="text" icon="el-icon-plus" @click="handleAddApp">添加应用</el-button>

    <el-table :data="appList" border style="width: 100%">
      <el-table-column prop="appName" label="应用名称" width="180" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="createUserName" label="创建人" width="180" />
      <el-table-column prop="formId" label="关联表单" width="180">
        <template slot-scope="scope">
          <el-button v-if="scope.row.formId == null" type="text" icon="el-icon-edit">关联表单</el-button>
          <el-button v-if="scope.row.formId != null" type="text" icon="el-icon-document">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="操作">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-edit">编辑</el-button>
          <el-button type="text" icon="el-icon-edit">授权</el-button>
          <router-link :to="{name: 'FormAdmin', params: {appId: scope.row.appId}}">
            <el-button type="text" icon="el-icon-edit">管理表单</el-button>
          </router-link>
          <!-- <el-button type="text" icon="el-icon-edit" @click="handleManageForm(scope.row)">管理表单</el-button> -->
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import appService from "@/service/app-service";

export default {
  data() {
    return {
      total: 0,
      appList: []
    };
  },
  mounted() {
    appService.queryPage().then(res => {
      console.debug(res)
      this.total = res.total;
      this.appList = res.data;
    });
  },
  methods: {
    handleManageForm(app) {
    },
    handleAddApp() {
      // TODO form 是操作后返回的数据，用于发送到服务端
      var form = {}
      appService.createApp(form)
        .then(() => appService.queryPage())
        .then(res => {
          console.debug(res)
          this.total = res.total;
          this.appList = res.data;
        })
        .then(() => {
          this.$message.success('添加成功')
        })
    }
  }
};
</script>
