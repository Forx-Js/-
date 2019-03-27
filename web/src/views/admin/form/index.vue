<template>
  <div>
    <h1>表单管理</h1>
    <el-table :data="formList" border style="width: 100%">
      <el-table-column prop="formName" label="表单名称" width="180" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="createUserName" label="创建人" width="180" />
      <el-table-column prop="address" label="操作">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-document">预览</el-button>
          <el-button type="text" icon="el-icon-edit">编辑</el-button>
          <router-link :to="{name: 'formBuilder', params: {formId: scope.row.formId}}">
            <el-button type="text" icon="el-icon-edit">设计表单</el-button>
          </router-link>
          <el-button type="text" icon="el-icon-edit" @click="handleFieldPermission(scope.row)">字段权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <field-permission-dlg ref="fieldPermissionDlg" />
  </div>
</template>

<script>
import formService from "@/service/form-service";
import fieldPermissionDlg from './fieldPermissionDlg'

export default {
  components: { fieldPermissionDlg },
  data() {
    return {
      appId: '',
      total: 0,
      formList: []
    };
  },
  mounted() {
    this.appId = this.$route.params.appId
    formService.queryPage(this.appId)
      .then(res => {
        this.total = res.total;
        this.formList = res.data;
      });
  },
  methods: {
    handleFieldPermission(form) {
      this.$refs.fieldPermissionDlg.show(form.formId)
    }
  }
};
</script>
