<template>
  <div>
    <el-dialog :visible.sync="showDialog" width="50%">
      <el-row class="form-border">
        <el-col :span="6">
          <ul>
            <li v-for="roleItem in roles" :key="roleItem.roleId" :class="roleItem.roleId === selectedRoleId ? 'selected-role' : 'role'">
              <el-button type="text" @click="handleSelectedRoleChange(roleItem)">
                {{ roleItem.roleName }}
              </el-button>
            </li>
          </ul>
        </el-col>
        <el-col :span="12">
          <el-table ref="fieldTable" :data="fields" border @selection-change="handleFieldsSelectionChange">
            <el-table-column prop="fieldId" type="selection" label="" width="50" />
            <el-table-column prop="fieldName" label="表单名称" width="180" />
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button type="default" @click="showDialog = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import formService from '@/service/form-service'
import userAdminService from '@/service/user-admin-service'

export default {
  data() {
    return {
      showDialog: false,
      formId: '',
      roles: [{
        roleId: '*',
        roleName: '所有用户'
      }],
      fields: [{
        fieldId: 'title',
        fieldName: '标题'
      },
      {
        fieldId: 'subTitle',
        fieldName: '副标题'
      }],
      selected: [],
      selectedRole: '',
      selectedRoleId: ''
    }
  },

  mounted() {
  },
  methods: {
    // 对外的接口
    show(formId) {
      this.formId = formId
      this.reset()
      userAdminService.queryAllRoles()
        .then(res => { this.roles = res || [] })
        .then(() => formService.queryFields(this.formId))
        .then(res => { this.fields = res || [] })
      this.showDialog = true
    },
    reset() {
      this.roles = []
      this.fields = []
      this.selected = []
      this.selectedRole = null
      this.selectedRoleId = ''
    },
    handleFieldsSelectionChange(selectedItems) {
      this.selected = selectedItems
    },
    handleSelectedRoleChange(role) {
      if (!role) {
        this.selectedRole = null
        return
      }
      this.selectedRole = role
      this.selectedRoleId = role.roleId
      formService.queryRoleFormFields(this.selectedRoleId, this.formId)
        .then(roleFields => { this.refreshFieldsSelection(roleFields) })
    },
    refreshFieldsSelection(roleFields) {
      if (roleFields && roleFields.length > 0) {
        this.fields.forEach(field => {
          if (roleFields.some(item => item.fieldId === field.fieldId)) {
            this.$refs.fieldTable.toggleRowSelection(field, true)
          } else {
            this.$refs.fieldTable.toggleRowSelection(field, false)
          }
        })
      } else {
        this.fields.forEach(field => {
          this.$refs.fieldTable.toggleRowSelection(field, true)
        })
      }
    },
    handleSubmit() {
      // TODO
      this.showDialog = false
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.role {
  font-weight: normal;
}
.selected-role {
  color: #ff0000;
  font-weight: bold;
}
</style>

