<template>
  <div class="hoursApproval">
    <el-container>
      <el-header style="height: 61px">
        <el-row :gutter="20" class="tab">
          <el-col :span="1.5" :class="[index === cur?'active':'']" v-for="(item , index) in list" :key="index">
            <div @click="change(index)">
              <b>{{item.name}}</b>
            </div>
          </el-col>
          <el-col :span="2" :offset="1">
            <ul class="button">
              <li class="Project" @click="getProject" :class="[type=== 1?'click':'']">项目模式</li>
              <li class="person" @click="getPerson" :class="[type=== 2?'click':'']">人员模式</li>
            </ul>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="height:768px;">
        <el-form ref="formInline" :model="formInline" :inline="true" size="medium" class="form-inline" style="height:66px;border-bottom:1px dashed #aaa;padding-left:10px;padding-top:20px">
          <!-- 人员范围 -->
          <el-form-item>
            <div class="input1">
              <input type="text" :placeholder="this.srk">
              <p class="ss1" @click="queryEvent4()">搜索</p>
            </div>
          </el-form-item>
          <el-form-item>
            <div class="block" style="margin-left:123px">
              <span class="demonstration">选择日期:</span>
              <el-date-picker v-model="value1" type="date" placeholder="选择日期">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <div class="block">
              <span class="demonstration">至</span>
              <el-date-picker v-model="value2" type="date" style="margin-left:9px" placeholder="选择日期">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item class="item-button1" style="margin-left:5px">
            <el-button type="primary" @click="addhoursEvent">筛选</el-button>
            <el-button @click="Submission">全选</el-button>
          </el-form-item>
          <el-form-item class="item-button" v-show="show">
            <el-button type="warning" @click="addhoursEvent">驳回</el-button>
            <el-button type="success" @click="Submission">通过</el-button>
          </el-form-item>
        </el-form>
        <!-- 表格 -->
        <div class="main-table-minHeight" style="padding:60px 223px">
          <el-table :data="items.data" v-for="(items, index) in lists1" :key="index" border  max-height="174" size="mini"
            @header-click="headerclick" class="active2" style="width: 1400px" :header-cell-style="getRowClass">
            <el-table-column :label="items.name" align="center" width="150">
              <!-- 数据的遍历  scope.row就代表数据的每一个对象-->
              <template slot-scope="scope">
                <span>{{scope.row.userName}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="content" class-name="view_td" label="任务" align="center"></el-table-column>
            <el-table-column prop="duration" label="任务时长" width="120" align="center"></el-table-column>
            <el-table-column prop="missiontime" label="任务时间" width="120" align="center"></el-table-column>
            <el-table-column prop="currentdate" label="提交时间" width="120" align="center"></el-table-column>
            <el-table-column label="操作"  align="center" v-if="type==1">
              <template slot-scope="scope" >
                <el-button @click.native.prevent="editEvent(scope.$index, scope.row)" type="text" size="small">审批记录</el-button>
              </template>
            </el-table-column>
            <el-table-column :label="'总时长：'+items.time" align="center" v-if="type==2">
              <template slot-scope="scope">
                <el-button @click.native.prevent="editEvent(scope.$index, scope.row)" type="text" size="small">审批记录</el-button>
              </template>
            </el-table-column>
            <el-table-column type="selection" align="center" width="55">
            </el-table-column>
            
          </el-table>
        </div>
        <!-- <table v-for="item in items" :key="item.index" class="datatab" border="1" cellspacing="0" cellpadding="5" align="center">
          <thead>
              <tr>
                  <th>{{item.ranking}}</th>
                  <th>标题2</th>
                  <th>标题3</th>
                  <th>标题4</th>
                  <el-checkbox label="" name="type" ></el-checkbox>
                  <th @click="filterHandler">+</th>
              </tr>
          </thead>
          <tbody>
              <tr v-for="item in items" :key="item.index">
                  <td align="center">{{ item.ranking  }}</td>
                  <td align="center">{{ item.point  }}</td>
                  <td align="center">{{ item.number  }}</td>
                  <td align="center">{{ item.sales_volume  }}</td>
                  <td><el-checkbox label="" name="type" v-model="item.sales_volume" v-bind="{'checked': item.sales_volume==true ? true : false}"></el-checkbox></td>
              </tr>
          </tbody>
        </table> -->
        <!-- 弹出 -->
        <el-dialog title="编辑项目" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
          <el-form :model="personForm" ref="personForm" :inline="true">
            <el-form-item label="请选择项目：">
              <el-select filterable v-model="personForm.projectName" placeholder="请选择">
                <el-option v-for="item in projectlist" :key="item.id" :label="item.projectName" :value="item.projectName"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="请填写任务内容：">
              <el-input v-model="personForm.content" placeholder="请输入内容" type="textarea" style="width:535px"></el-input>
            </el-form-item>
            <el-form-item label="请填写任务时长：" prop="note">
              <el-input v-model="personForm.duration" placeholder="请输入内容" type="textarea" style="width:535px"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible=false">取 消</el-button>
            <el-button type="primary" @click="add_tem_event">确 定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>

  </div>
</template>
<script>
  import manhourservice from "@/service/man-hour-service";
  export default {
    data() {
      return {
        formInline: {
          capitalCreditID: "",
        },
        zjxyOptions: [],
        dialogVisible: false,
        customer_Mess_Data: [],
        personForm: {},
        projectlist: [],
        type: 1,
        input4: '',
        value1: '',
        value2: '',
        show:true,
        srk:"请输入项目名称",
        height: "100%",
        activeNames: ['1'],
        list: [{
            name: "待审批",
            id: 0
          },
          {
            name: "已审批",
            id: 1
          },
          {
            name: "抄送我",
            id: 2
          }
        ],
        lists1:[],
        // 待审批项目模式
        lists: [{
            name: "待qq",
            time: "3小时",
            id: 0,
            data: [{
                userName: 'admin1',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              }
            ]
          },
          {
            name: "已qqq",
            id: 1,
            time: "3小时",
            data: [{
                userName: 'admin3',
              },
              {
                userName: 'jason4',
              },
              {
                userName: 'jason4',
              }
            ]
          },
          {
            name: "抄qqq",
            id: 2,
            time: "3小时",
            data: [{
                userName: 'admin5',
              },
              {
                userName: 'jason6',
              },
              {
                userName: 'jason4',
              }
            ]
          }
        ],
        // 待审批人员模式
        listss: [{
            name: "小明",
            time: "3小时",
            id: 0,
            data: [{
                userName: 'admin1',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              }
            ]
          },
          {
            name: "小红",
            id: 1,
            time: "3小时",
            data: [{
                userName: 'admin3',
              },
              {
                userName: 'jason4',
              },
              {
                userName: 'jason4',
              }
            ]
          },
          {
            name: "小兰",
            id: 2,
            time: "3小时",
            data: [{
                userName: 'admin5',
              },
              {
                userName: 'jason6',
              },
              {
                userName: 'jason4',
              }
            ]
          }
        ],
        // 已审批项目模式
        listss1: [{
            name: "待",
            time: "3小时",
            id: 0,
            data: [{
                userName: 'admin1',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              }
            ]
          },
          {
            name: "已",
            id: 1,
            time: "3小时",
            data: [{
                userName: 'admin3',
              },
              {
                userName: 'jason4',
              },
              {
                userName: 'jason4',
              }
            ]
          },
          {
            name: "抄",
            id: 2,
            time: "3小时",
            data: [{
                userName: 'admin5',
              },
              {
                userName: 'jason6',
              },
              {
                userName: 'jason4',
              }
            ]
          }
        ],
        // 已审批人员模式
        listsss2: [{
            name: "小明",
            time: "3小时",
            id: 0,
            data: [{
                userName: 'admin1',
              },
              {
                userName: 'jason2',
              },
              {
                userName: 'jason2',
              }
            ]
          },
          {
            name: "小红",
            id: 1,
            time: "3小时",
            data: [{
                userName: 'admin3',
              },
              {
                userName: 'jason4',
              },
              {
                userName: 'jason4',
              }
            ]
          },
          {
            name: "小兰",
            id: 2,
            time: "3小时",
            data: [{
                userName: 'admin5',
              },
              {
                userName: 'jason6',
              },
              {
                userName: 'jason4',
              }
            ]
          }
        ],
        cur: 0,
        items: [{
          ranking: '1',
          point: '阿里巴巴',
          number: '1000',
          sales_volume: true
        }, {
          ranking: '2',
          point: '阿里巴巴',
          number: '1000',
          sales_volume: '2000'
        }, {
          ranking: '3',
          point: '阿里巴巴',
          number: '1000',
          sales_volume: '2000'
        }, {
          ranking: '4',
          point: '阿里巴巴',
          number: '1000',
          sales_volume: '2000'
        }, {
          ranking: '5',
          point: '阿里巴巴',
          number: '1000',
          sales_volume: '2000'
        }],
        data: [{
            userName: 'admin',
          },
          {
            userName: 'jason',
          }
        ]
      };
    },
    // computed: {},
    created() {
      this.getStatus();
      this.queryEvent();
      this.lists1=this.lists
    },
    methods: {
      getRowClass({
        row,
        column,
        rowIndex,
        columnIndex
      }) {
        if (rowIndex === 0) {
          return 'background:#8ea7d1;color:white;text-align:center;font-weight:300;'
        } else {
          return ''
        }
      },
      // 已审批 项目模式
      getApprovalProject(){
        this.lists1=this.listss1
        this.type=1
        this.srk="请输入项目名称"
        this.show=false
      },

      // 已审批 人员模式
      getApprovalPerson(){
        this.lists1=this.listss1
        this.type=1
        this.srk="请输入项目名称"
        this.show=false
      },
      // 抄送我
      Submission(){

      },
      // 项目模式  需要判断是待审批还是已审批
      getProject() {
        console.log(this.cur,"cur")
        this.type=1
        this.srk="请输入项目名称"
        this.show=true
        if(this.cur==0){
          this.lists1=this.lists
          
        }else if(this.cur==1){
          // this.lists1=this.listss1
          this.getApprovalProject()
        }
      },
      // 人员模式  需要判断是待审批还是已审批
      getPerson(){
        this.lists1=this.listss
        this.type=2
        this.srk="请输入相关人员"
      },
      // 折叠表格
      headerclick(column, event) {
        console.log(column, event)

      },
      filterHandler() {
        this.items = ""
        console.log("000")
      },
      // tab切换
      change(index) {
        console.log(index, "000")
        this.cur = index;
        if(index==1){
          this.getApprovalProject()
          // this.lists1=this.listss1
        }else if(index==0){
          this.getProject() 
          // this.lists1=this.lists
        }else if(index==2){
          this.Submission()
        }
      },
      //查询
      queryEvent() {
        manhourservice.querydata()
          .then(res => {
            // console.log(res,"查询")
            this.customer_Mess_Data = res.data
          })
      },
      // 添加的确定按钮
      add_tem_event() {
        // console.log(this.personForm,"personForm")
        this.dialogVisible = false
        // console.log(this.personForm.content,this.personForm.duration,this.personForm.projectName,"内容时间下拉")
        this.handleAddApp()
      },
      // 下拉
      getStatus() {
        var _this = this;
        _this.zjxyOptions = [{
          cnName: "上一条填报",
          id: 0
        }, ];
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      // 添加按钮的弹出
      addhoursEvent() {
        this.dialogVisible = true
        this.personForm = {}
      },
      // 上一个模板和确定按钮的执行
      handleAddApp() {
        // TODO form 是操作后返回的数据，用于发送到服务端
        // var form = {}
        // manhourservice.addhoursReport(this.personForm)
        //   .then(res => {
        //     console.log(res,"数据")
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
          month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        // console.log(currentdate,"当前时间")
        this.personForm.currentdate = currentdate;
        this.personForm.missiontime = this.Missiontime;

        this.customer_Mess_Data.push(this.personForm)
        // console.log(this.personForm,"111")
        //   })
        //   .then(() => {
        //     this.$message.success('添加成功')
        //   })
        // this.queryEvent();
      },
      // 提交
      Submission() {
        console.log(this.customer_Mess_Data, "提交")
      },
      // 编辑
      editEvent(index, row) {
        this.dialogVisible = true
        // console.log(row,"编辑")
        this.personForm.content = row.content
        this.personForm.duration = row.duration
        this.personForm.projectName = row.projectName
      },
      changetype() {
        this.type = !this.type
      }

    },

  };

</script>
<style scoped lang="scss">
  .hoursApproval {
    background-color: #f0eeee;

  }

  .el-header {
    background-color: white;
    color: #aaa;
    width: 99%;
    margin: 0 auto;
    /* margin-top: 12px; */
    line-height: 61px;
  }

  .el-main {
    background-color: white;
    width: 99%;
    margin: 0 auto;
    margin-top: 12px;
    padding: 0;
  }

  .item-button {
    float: right;
    margin-right: 26px;
  }


  .tab .active {
    height: 61px;
    color: #1492ff;
    border-bottom: 3px solid #1492ff
  }

  .input1 {
    width: 225px;
    display: inline-block;
    position: relative;
    padding-bottom: 10px;

    input {
      width: 249px;
      height: 37px;
      border-radius: 8px;
      border: 1px solid #e1e1e1;
      outline: none;
      font-size: 13px;
      padding-left: 15px;
    }

    .ss1 {
      position: absolute;
      height: 36.3px;
      top: -14px;
      left: 208px;
      width: 64px;

      text-align: center;
      display: inline-block;
      background: #55a7f0;
      color: white;
      // border-radius: 5%;
      border-top-right-radius: 8px;
      border-bottom-right-radius: 8px;
      cursor: pointer;
    }
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .datatab {
    font-size: 18px;
    margin-bottom: 20px;
  }

  #el-table_19_column_163 {
    height: 46px;
  }

  .active1 {
    font-size: 18px;
    margin-bottom: 20px;
    height: 46px;
  }

  .active2 {
    font-size: 15px;
    margin-bottom: 20px;
    height: 100%;
  }

  .button {
    margin-left: 1395px;
    width: 172px;
    height: 40px;
    line-height: 40px;
    font-size: 0;
    padding: 0;
    margin-top: 13px;
    box-sizing: border-box;
    color: black
  }

  .button li {
    list-style: none;
    display: inline-block;
    height: 38px;
    font-size: 15px;
    padding: 0 12px;
    border:1px solid #55a7f0;
    box-sizing: border-box;
  }

  .Project {
    // background: #1492ff;
    border-top-left-radius: 50px;
    border-bottom-left-radius: 50px;

  }

  .person {
    // background: red;
    border-top-right-radius: 50px;
    border-bottom-right-radius: 50px;
  }
  .button .click{
    color: white;
    background: #55a7f0
  }

</style>
