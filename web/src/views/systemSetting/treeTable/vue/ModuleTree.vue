<template>
    <el-table
      :data="data"
      border
      style="width: 100%"
      :row-style="showTr"
      row-key="moduleID"
      align="left"
      >
        <el-table-column 
          v-for="(column, index) in columns" 
          width="300" 
          :key="column.dataIndex"
          :label="column.text">
          <template slot-scope="scope" >  
            <span v-show="spaceIconShow(index,scope.row)" v-for="(space, levelIndex) in scope.row._level" class="ms-tree-space" :key="levelIndex"></span>
            <div style="display:inline-block;margin-right:5px" class="button is-outlined is-primary is-small" v-if="toggleIconShow(index,scope.row)" @click="toggle(scope.$index)">
              <!-- <i v-if="scope.row._expanded" class="el-icon-star-on" aria-hidden="true"> -->
            <!-- <i><img src="../../../layout/components/images/paper.png" alt=""></i> -->
              <!-- <i v-if="!scope.row._expanded" class="el-icon-star-on" aria-hidden="true"></i> -->
            </div>
            <!-- <i v-else-if="index===0" class="el-icon-star-off" aria-hidden="true" style="width:20px;height:20px"></i> -->
            <i v-else-if="index===0"><img src="" alt=""></i>
              <!-- 下面是模块名称 -->
            {{scope.row[column.dataIndex]}}    
            <!-- ../../../layout/components/images/tree.png     -->
          </template>
        </el-table-column>
        <el-table-column  label="是否可见" width="100" align="center">
          <template slot-scope="scope">
            <el-checkbox
            v-model="scope.row.show"
            v-bind="{'checked': scope.row.show? true : false}"
            @change="showChange(scope)"
            ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="是否可编辑" align="center" width="150">
          <template slot-scope="scope">
            <el-checkbox
            v-model="scope.row.edit"
            v-bind="{'checked': scope.row.edit? true : false}"
            @change="editChange(scope)"
            ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="操作"  v-if="treeType === 'normal'" width="476">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="danger"
              style="background:white;border:1px solid #dcdfe6;color:#606266"
              @click="handlechange(scope)">
              修改
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope)">
              删除
            </el-button>
            <el-button
              size="small"
              type="danger"
              style="background:#67c23a;border:none"
              @click="handleAdd(scope)">
              添加下级树结构
            </el-button>
          </template>
        </el-table-column>
    </el-table>   
</template>
<script>
  import Utils from '../utils/index.js'
  import Sortable from 'sortablejs'
//  import Vue from 'vue'
  export default {
    name:"module-tree",
    props: {
// 该属性是确认父组件传过来的数据是否已经是树形结构了，如果是，则不需要进行树形格式化
      treeStructure: {
        type: Boolean,
        default: function () {
          return false
        }
      },
// 这是相应的字段展示
      columns: {
        type: Array,
        default: function () {
          return []
        }
      },
// 这是数据源
      dataSource: {
        type: Array,
        default: function () {
          return []
        }
      },
// 这个作用是根据自己需求来的，比如在操作中涉及相关按钮编辑，删除等，需要向服务端发送请求，则可以把url传过来
      requestUrl: {
        type: String,
        default: function () {
          return ''
        }
      },
// 这个是是否展示操作列
      treeType: {
        type: String,
        default: function () {
          return 'normal'
        }
      },
// 是否默认展开所有树
      defaultExpandAll: {
        type: Boolean,
        default: function () {
          return false
        }
      }
    },
    data () {
      return {
        selectArr:[],
        selectArr1:[],
        sysModuledata:[],
        sysModuledata2:[],
        sysModuledata3:[],
        sysModuledata4:[],
        sysModuledata5:[],
        sysModuledata6:[],
        module_ID:"",
        dataARR1:"",

      };
    },
    mounted() {
        this.rowDrop()
    },
    computed: {
    // 格式化数据源
      data: function () {
        let me = this
        me.selectArr=[]
        if (me.treeStructure){
          let data = Utils.MSDataTransfer.treeToArray(me.dataSource, null, null, me.defaultExpandAll)
              for(var i=0;i<data.length;i++){
                        if(data[i].limit==1){
                            me.$set(data[i], "edit", true)
                            me.$set(data[i], "show", true)
                            me.selectArr.push(data[i])
                        }else if(data[i].limit==2){
                            me.$set(data[i], "edit", false)
                            me.$set(data[i], "show", true)
                            me.selectArr.push(data[i])
                        }else{
                            me.$set(data[i], "edit", false)
                            me.$set(data[i], "show", false)
                        }
                }
          for(var i=me.selectArr.length-1;i>=0;i--){
            if(me.selectArr.indexOf(me.selectArr[i])!==me.selectArr.lastIndexOf(me.selectArr[i])){
              me.selectArr.splice(i,1);
            }
          }
          me.$emit("dd",me.selectArr)
          return data
        }
        return me.dataSource
      }
    },
    methods: {
      showTr: function (row, index) {
        let show = (row.row._parent ? (row.row._parent._expanded && row.row._parent._show) : true)
        row.row._show = show
        return show ? '' : 'display:none;'
      },
    // 展开下级
      toggle: function (trIndex) {
        let me = this
        let record = me.data[trIndex]
        record._expanded = !record._expanded
      },
      //行拖拽
      rowDrop(){
          const tbody = document.querySelector('.el-table__body-wrapper tbody')
          const _this = this
          _this.selectArr1=[]
          Sortable.create(tbody, {    
              onEnd({ newIndex, oldIndex }) {
                console.log(oldIndex,newIndex,"888")
                const currRow = _this.data.splice(oldIndex, 1)[0]
                console.log(currRow,"拖拽的是谁")
                // console.log(_this.data,"000")
                var a=currRow.pID
                console.log(a,"000")
                // a是获取到的pid
                _this.data.splice(newIndex, 0, currRow)
                _this.selectArr1=[]
                for(var i=0;i<_this.data.length;i++){
                  if(_this.data[i].pID==a){
                    _this.selectArr1.push(_this.data[i])
                    console.log(_this.selectArr1)
                  }
                }
                console.log(_this.selectArr1,"222")
                _this.dataArr1="";
                for(var j = 0;j<_this.selectArr1.length; j++){
                    _this.selectArr1[j].sortNum=j+1
                    // console.log()
                    _this.dataArr1 +=_this.selectArr1[j].moduleID+","+_this.selectArr1[j].sortNum+"|";
                }
                _this.remove()
                console.log(_this.dataArr1,"33")
              }
          })
      },
      remove(){
            let _this=this;
            console.log(_this.dataArr1,"33")
            _this.axios
            .post(_this.testIP + "/module/update/sortnum",{
              keyWord:_this.dataArr1,
            })
            .then(function(res) {
                if(res.data.result == "0") {
                    // console.log(res.data.data,"提交");
                }else{
                    _this.$message({duration:1000,
                        message: "排序失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
      },
      
    // 显示层级关系的空格和图标
      spaceIconShow (index,record) {
        let me = this
        if (me.treeStructure && index === 0) {
          return true
        }
        return false
      },
    // 点击展开和关闭的时候，图标的切换
      toggleIconShow (index, record) {
        let me = this
        if (me.treeStructure && index === 0 && record.sysModuleQueryVo && record.sysModuleQueryVo.length > 0) {
          return true
        }
        return false
      },
      selectSingle(val){
        let me = this 
        me.selectArr = val;
      },
      // 复选框 可见
      showChange(scope){
        let me = this
        me.sysModuledata=scope.row.sysModuleQueryVo
        // console.log(scope,"1111")
        // console.log(scope.row.moduleID,"111")
        me.module_ID=scope.row.moduleID
        // console.log(me.module_ID)
        // 不可见就一定不可编辑

        if(scope.row.show==true&&scope.row.edit==true){
                scope.row.limit=1
        }else if(scope.row.show==true&&scope.row.edit==false){
                scope.row.limit=2
        }else if(scope.row.show==false&&scope.row.edit==true){
                scope.row.limit=3
        }else{
                scope.row.limit=3
        }

        if(scope.row.limit==1||scope.row.limit==2){
            me.selectArr.push(scope.row)
            for(var i=me.selectArr.length-1;i>=0;i--){
              if(me.selectArr.indexOf(me.selectArr[i])!==me.selectArr.lastIndexOf(me.selectArr[i])){
                  me.selectArr.splice(i,1);
              }
          }
        }else{
          let index1=scope.row.moduleID;
          me.removeByValue(me.selectArr,"moduleID",index1)
        }

        //有子级还有父级
        if(scope.row.sysModuleQueryVo!=null&&scope.row._parent!=undefined){
            if(scope.row.show==true){
              for(var j=0;j<me.sysModuledata.length;j++){
                // 所有的儿子1
                me.sysModuledata[j].limit=2
                // 所有的孙子1
                me.sysModuledata2=me.sysModuledata[j].sysModuleQueryVo
                console.log(me.sysModuledata2,"1111")
                if(me.sysModuledata2!=null){
                    for(var l=0;l<me.sysModuledata2.length;l++){
                    me.sysModuledata2[l].limit=2
                    // 所有的重孙1
                    me.sysModuledata3=me.sysModuledata2[l].sysModuleQueryVo
                    if(me.sysModuledata3!=null){
                        for(var t=0;t<me.sysModuledata3.length;t++){
                            me.sysModuledata3[t].limit=2
                        }
                    }                
                  }
                }
              }
              // 自己的父级1
              scope.row._parent.limit=2
            }else{
              // 所有的子级1
              for(var j=0;j<me.sysModuledata.length;j++){
                me.sysModuledata[j].limit=3
                me.sysModuledata2=me.sysModuledata[j].sysModuleQueryVo
                console.log(me.sysModuledata2,"2222")
                if(me.sysModuledata2!=null){
                  for(var l=0;l<me.sysModuledata2.length;l++){
                      me.sysModuledata2[l].limit=3
                  // 所有的重孙1
                      me.sysModuledata3=me.sysModuledata2[l].sysModuleQueryVo
                      if(me.sysModuledata3!=null){
                        for(var t=0;t<me.sysModuledata3.length;t++){
                            me.sysModuledata3[t].limit=3
                        }
                      }
                  }
                }
              }
              // scope.row._parent.limit=3
            }
          // 有子级没有父级
        }else if(scope.row.sysModuleQueryVo!=null&&scope.row._parent==undefined){
                if(scope.row.show==true){
                  for(var j=0;j<me.sysModuledata.length;j++){
                    // 所有的儿子2
                    me.sysModuledata[j].limit=2
                    // 所有的孙子2
                    me.sysModuledata2=me.sysModuledata[j].sysModuleQueryVo
                    for(var l=0;l<me.sysModuledata2.length;l++){
                      me.sysModuledata2[l].limit=2
                      // 所有的重孙2
                      me.sysModuledata3=me.sysModuledata2[l].sysModuleQueryVo
                      if(me.sysModuledata3!=null){
                          for(var t=0;t<me.sysModuledata3.length;t++){
                              me.sysModuledata3[t].limit=2
                          }
                      }
                      
                    }
                    
                  }
                }else{
                      for(var j=0;j<me.sysModuledata.length;j++){
                        me.sysModuledata[j].limit=3
                        me.sysModuledata2=me.sysModuledata[j].sysModuleQueryVo
                        for(var l=0;l<me.sysModuledata2.length;l++){
                          me.sysModuledata2[l].limit=3
                      // 所有的重孙2
                          me.sysModuledata3=me.sysModuledata2[l].sysModuleQueryVo
                          if(me.sysModuledata3!=null){
                            for(var t=0;t<me.sysModuledata3.length;t++){
                                me.sysModuledata3[t].limit=3
                            }
                          }
                        }                
                      }
                }
        }
      
        // 侧边栏的第一个和菜单一致
        // if(scope.row.moduleID==me.module_ID&&scope.row.show==true){
        //         console.log(me.sysModuledata,"5555")
        //             for(var y=0;y<me.sysModuledata.length;y++){
        //               if(me.sysModuledata[y].moduleID=='c32c2a04aa194430b9bd4f3aa63f0679'){
        //                       me.sysModuledata[y].limit=2
        //                 }
        //         }
        // // }else{

        // }

      },
      // 复选框 可编辑
      editChange(scope){
        let me=this
        console.log(scope.row)
        me.sysModuledata4=scope.row.sysModuleQueryVo
        if(scope.row.sysModuleQueryVo!=null&&scope.row._parent!=undefined){
            // console.log(me.sysModuledata4,"777")
            if(scope.row.edit==true){
              // 所有的子级
              for(var j=0;j<me.sysModuledata4.length;j++){
                me.sysModuledata4[j].limit=1
              }
              // 自己的父级
              scope.row._parent.limit=1
            }else{
              // 所有的子级
              for(var j=0;j<me.sysModuledata4.length;j++){
                me.sysModuledata4[j].limit=3
              }
            }
          // 有子级没有父级
        }else if(scope.row.sysModuleQueryVo!=null&&scope.row._parent==undefined){
                // console.log(me.sysModuledata4,"777")
                if(scope.row.edit==true){
                  for(var j=0;j<me.sysModuledata4.length;j++){
                    // 所有的儿子
                    me.sysModuledata4[j].limit=1
                    // 所有的孙子
                    me.sysModuledata5=me.sysModuledata4[j].sysModuleQueryVo
                    for(var l=0;l<me.sysModuledata5.length;l++){
                      me.sysModuledata5[l].limit=1
                      // 所有的重孙
                      me.sysModuledata6=me.sysModuledata5[l].sysModuleQueryVo
                      if(me.sysModuledata6!=null){
                          for(var t=0;t<me.sysModuledata6.length;t++){
                              me.sysModuledata6[t].limit=1
                          }
                      }
                      
                    }
                    
                  }
                }else{
                      for(var j=0;j<me.sysModuledata4.length;j++){
                        me.sysModuledata4[j].limit=3
                        me.sysModuledata5=me.sysModuledata4[j].sysModuleQueryVo
                        for(var l=0;l<me.sysModuledata5.length;l++){
                          me.sysModuledata5[l].limit=3
                      // 所有的重孙
                          me.sysModuledata6=me.sysModuledata5[l].sysModuleQueryVo
                          if(me.sysModuledata6!=null){
                            for(var t=0;t<me.sysModuledata6.length;t++){
                                me.sysModuledata6[t].limit=3
                            }
                          }
                        }                
                      }
                }
        }
        // 如果有可编辑 不可见时 就会自动帮你清除掉
        if(scope.row.show==true&&scope.row.edit==true){
                scope.row.limit=1
        }else if(scope.row.show==true&&scope.row.edit==false){
                scope.row.limit=2
        }else if(scope.row.edit==true&&scope.row.show==false){
              scope.row.limit=3
        }else{
            scope.row.limit=3
        }

        if(scope.row.limit==1||scope.row.limit==2){
          me.selectArr.push(scope.row)
            for(var i=me.selectArr.length-1;i>=0;i--){
            if(me.selectArr.indexOf(me.selectArr[i])!==me.selectArr.lastIndexOf(me.selectArr[i])){
              me.selectArr.splice(i,1);
            }
          }
        }else{
          let index2=scope.row.moduleID;
          me.removeByValue(me.selectArr,"moduleID",index2)
        }
      },
      // 复选框不勾选时需要调用的函数
      removeByValue(arr, attr, value) {  //数组，属性，属性值
                var index=0;
                for(var i in arr){
                    if(arr[i][attr]==value){
                        index=i;
                        break;
                    }
                }
                arr.splice(index,1);
      },
      // 删除
      handleDelete(scope) {
        this.$emit('del',scope.row.moduleID)
      },
      // 添加
      handleAdd(scope) {
          this.$emit('connect',scope.row.moduleID)
      },
      // 修改
      handlechange(scope){
          this.$emit('handlechange',scope.row.moduleID)
      },
    }
  }
</script>
<style scoped>
  .ms-tree-space{
    position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    width: 18px;
    height: 14px;
  }
  .ms-tree-space::before{content: ""}
  table td{
    line-height: 26px;
    
  }
.el-icon-remove-outline:before {
    content: "\E63C";
    font-size: 22px;
}
.el-icon-circle-plus-outline:before {
    content: "\E602";
    font-size: 22px;
}
</style>