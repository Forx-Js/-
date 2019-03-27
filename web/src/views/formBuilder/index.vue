<template>
  <div class="fm-container">
    <!-- <fm-making-form ref="ctrl" preview generate-code generate-json @getJSON="getJSON"> -->
    <fm-making-form ref="ctrl" preview @getJSON="getJSON">
      <template slot="action">
        <el-button type="text" icon="el-icon-check" @click="handleOK">保存表单</el-button>
      </template>
    </fm-making-form>
  </div>
</template>

<script>
import Vue from "vue";
import { MakingForm } from "form-making";
import "element-ui/lib/theme-chalk/index.css";
import "form-making/dist/FormMaking.css";
import formService from '@/service/form-service'

Vue.component(MakingForm.name, MakingForm);

export default {
  name: "FormBuilder",
  data() {
    return {
      configId: null,
      jsonData: null,
      values: {},
      remoteFuncs: {
        func_test(resolve) {
          resolve();
        }
      }
    };
  },
  created() {
    var processID = this.$route.params && this.$route.params.processID
    var nodeID = this.$route.params && this.$route.params.nodeID
    formService.queryForm(processID || '111', nodeID || '11')
      .then(data => {
        var json = data.formJson
        console.log(json)
        // TODO
        this.jsonData = JSON.parse(json)
      })
  },
  methods: {
    getJSON(json) {
      console.log(json);
    },
    handleOK() {
      console.log(JSON.stringify(this.$refs.ctrl.widgetForm));
      this.$confirm('确定保存吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var json = JSON.stringify(this.$refs.ctrl.widgetForm);
        // TODO 需要替换第 1、2 个参数
        var processID = this.$route.params && this.$route.params.processID
        var nodeID = this.$route.params && this.$route.params.nodeID
        formService.createForm(processID, nodeID, json)
          .then(configId => {
            this.configId = configId
            this.$message({
              message: '保存成功',
              showClose: true,
              type: 'success'
            });
          })
      })
    }
  }
};
</script>

<style >
.el-container {
  height: auto !important;
}
</style>
