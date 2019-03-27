<template>
  <div>
    <div>
      <label>流程ID</label>
      <el-input v-model="processID" placeholder="请输入流程ID" />
      <label>节点ID</label>
      <el-input v-model="nodeID" placeholder="请输入节点ID" />
      <label>运行实例ID</label>
      <el-input v-model="runtimeID" placeholder="请输入实例ID" />
      <button @click="handleLoadJson">加载表单</button>
    </div>
    <fm-generate-form ref="generateForm" :data="jsonData" :value="values" :remote="remoteFuncs" />
    <div>
      <button @click="handleOK">获取数据</button>
    </div>
  </div>
</template>

<script>
import Vue from "vue";

import { GenerateForm } from "form-making";
import "element-ui/lib/theme-chalk/index.css";
import "form-making/dist/FormMaking.css";
import formService from '@/service/form-service'

Vue.component(GenerateForm.name, GenerateForm);

export default {
  name: "FormShow",
  data() {
    return {
      processID: '',
      nodeID: '',
      formConfigID: '',
      runtimeID: '',
      json: "",
      values: {
        input_1549347004000_61695: "222",
        textarea_1549347064000_11150: "3333"
      },
      remoteFuncs: {
        func_test(resolve) {
          console.log("aaa");
          resolve();
        }
      }
    };
  },
  computed: {
    jsonData: function () {
      return !this.json || this.json === ""
        ? {
          list: [],
          config: {
            labelPosition: null
          }
        }
        : JSON.parse(this.json);
    }
  },
  methods: {
    handleLoadJson() {
      var processID = this.processID
      var nodeID = this.nodeID
      if (!processID || processID === '') {
        this.$alert('请输入要加载的流程ID', '')
        return
      }
      if (!nodeID || nodeID === '') {
        this.$alert('请输入要加载的节点ID', '')
        return
      }
      // TODO 查询表单的数据
      formService.queryForm(processID, nodeID)
        .then(res => {
          console.debug('return json-->')
          console.debug(res)
          this.json = res.formJson
          this.formConfigID = res.formConfigID
        })
        .then(() => formService.queryFormData(this.runtimeID, this.formConfigID))
        .then(res => {
          if (!res || !res.dataJson) {
            return
          }
          this.values = JSON.parse(res.dataJson)
        })
    },
    handleOK() {
      this.$refs.generateForm
        .getData()
        .then(data => {
          console.log("success");
          console.log(JSON.stringify(data));
          // TODO 保存数据
          return formService.saveFormData(this.runtimeID, this.formConfigID, JSON.stringify(data))
        })
        .then(() => {
          this.$message({
            message: '保存成功',
            type: 'success',
            onClose: function () {
              // TODO 在这里返回到列表页
              console.log('aaaaaa-------------bbbbbbbbb')
            }
          })
        })
        .catch(e => {
          // 数据校验失败
          console.log("failed: ");
          console.log(e);
        });
    }
  }
};
</script>

<style >
</style>
