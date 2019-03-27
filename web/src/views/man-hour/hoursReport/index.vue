<template>
  <div class="hoursReport">
    <el-container>
      <el-header style="height: 61px">
        <el-row :gutter="20">
          <el-col :span="1.5">
            <div class="grid-content bg-purple" style="height: 61px;border-bottom:3px solid #1492ff">
              <b>工时填报</b>
            </div>
          </el-col>
          <el-col :span="1" :offset="21">
            <el-button type="primary" @click="changetype">工作日历</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="height:768px;">
        <el-form ref="formInline" :model="formInline" :inline="true" size="medium" class="form-inline" style="height:66px;border-bottom:1px dashed #aaa;padding-left:110px;padding-top:20px">
          <!-- 人员范围 -->
          <el-form-item label="选择模板：" prop="capitalCreditID">
            <el-select v-model="formInline.capitalCreditID" clearabl eplaceholder="请选择" @change="handleAddApp">
              <el-option v-for="item in zjxyOptions" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="item-button">
            <el-button type="success" @click="addhoursEvent">新增</el-button>
            <el-button type="primary" @click="Submission">提交</el-button>
          </el-form-item>
        </el-form>
        <div class="main-table-minHeight" style="padding:60px">
          <el-table border :data="customer_Mess_Data" class="sydneyTable" :stripe="true" tooltip-effect="dark" style="width: 1400px"
            size="mini" header-cell-class-name="nihao"
            >
            <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
            <el-table-column label="项目" prop="projectName" width="100" align="center" />
            <el-table-column prop="content" class-name="view_td" label="任务" align="center"></el-table-column>
            <el-table-column prop="name" label="审批人" width="150" align="center"></el-table-column>
            <el-table-column prop="duration" label="任务时长" width="120" align="center"></el-table-column>
            <el-table-column prop="missiontime" label="任务时间" width="120" align="center"></el-table-column>
            <el-table-column prop="currentdate" label="提交时间" width="120" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
              <template slot-scope="scope">
                <el-button @click.native.prevent="editEvent(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
                <el-button @click.native.prevent="deleteEvent(scope.$index, scope.row)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
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
        <!-- 日历 -->
        <div id="calendar" v-if="type">
          <!-- 年份 月份 -->
          <div class="month">
            <ul>
              <li class="arrow" @click="pickPre(currentYear,currentMonth)">❮</li>
              <li class="year-month" @click="pickYear(currentYear,currentMonth)">
                <span class="choose-year">{{ currentYear }}</span>
                <span class="choose-month">{{ currentMonth }}月</span>
              </li>
              <li class="arrow" @click="pickNext(currentYear,currentMonth)">❯</li>
            </ul>
          </div>
          <!-- 星期 -->
          <ul class="weekdays">
            <li>一</li>
            <li>二</li>
            <li>三</li>
            <li>四</li>
            <li>五</li>
            <li>六</li>
            <li>日</li>
          </ul>
          <!-- 日期 -->
          <ul class="days">
            <li v-for="day in days" :key="day.index" @click="pick(day)">
              <!--本月-->
              <!-- 1 -->
              <!--不是这个月的 class为"other-month"-->
              <span v-if="day.getMonth()+1 != currentMonth" class="other-month">{{ day.getDate() }}</span>

              <!-- 2 -->
              <!--是这个月的  普通的为v-else 今天的class="active"-->
              <span v-else>
                <!--今天-->
                <span v-if="day.getFullYear() == new Date().getFullYear() && day.getMonth() == new Date().getMonth() && day.getDate() == new Date().getDate()"
                  class="active">{{ day.getDate() }}</span>
                <span v-else>{{ day.getDate() }}</span>
                <span v-for="item in cc" :key="item.index">
                  <span v-if="item.date.getFullYear() == day.getFullYear() && item.date.getMonth() == day.getMonth() && item.date.getDate() == day.getDate()&&item.msg==11"
                    class="active1">{{item.date.getDate()}}</span>
                  <span v-if="item.date.getFullYear() == day.getFullYear() && item.date.getMonth() == day.getMonth() && item.date.getDate() == day.getDate()&&item.msg==22"
                    class="active2">{{item.date.getDate()}}</span>
                  <span v-if="item.date.getFullYear() == day.getFullYear() && item.date.getMonth() == day.getMonth() && item.date.getDate() == day.getDate()&&item.msg==33"
                    class="active3">{{item.date.getDate()}}</span>
                  <span v-if="item.date.getFullYear() == day.getFullYear() && item.date.getMonth() == day.getMonth() && item.date.getDate() == day.getDate()&&item.msg==44"
                    class="active4">{{item.date.getDate()}}</span>
                  <span v-if="item.date.getFullYear() == day.getFullYear() && item.date.getMonth() == day.getMonth() && item.date.getDate() == day.getDate()&&item.msg==55"
                    class="active5">{{item.date.getDate()}}</span>
                </span>
              </span>
            </li>
          </ul>
          <ul class="Notes">
            <li><span class="passed"></span>已通过</li>
            <li><span class="noFillin"></span>未填报</li>
            <li><span class="noApproval"></span>待审批</li>
            <li><span class="reject"></span>被驳回</li>
            <li><span class="revoke"></span>已撤销</li>
          </ul>
        </div>
        <div class="total"><p>合计：</p>总时长(<span>  {{ total }}  </span>)小时</div>
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
        type: false,

        // 日历
        currentDay: 1,
        currentMonth: 1,
        currentYear: 1970,
        currentWeek: 1,
        days: [],
        cc: [],
        Missiontime: "",
        jsonHtml: [{
            date: "2019-3-3",
            msg: '11'
          },
          {
            date: "2019-3-4",
            msg: '22'
          },
          {
            date: "2019-3-5",
            msg: '33'
          },
          {
            date: "2019-3-6",
            msg: '44'
          },
          {
            date: "2019-3-7",
            msg: '55'
          },
          {
            date: "2019-4-6",
            msg: '11'
          },
          {
            date: "2019-4-7",
            msg: '22'
          },
          {
            date: "2019-4-8",
            msg: '33'
          },
          {
            date: "2019-4-9",
            msg: '44'
          }
        ]
      };
    },
    // computed: {},
    created() {
      this.getStatus();
      this.projectdata();
      this.queryEvent();

      // 初始的月份
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      var d = new Date(this.formatDate(year,month+1, 1));
      d.setDate(0);
      this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
      // this.initData(null);
    },
    methods: {
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
      // 项目
      projectdata() {
        var _this = this;
        _this.projectlist = [{
            projectName: "项目一",
            id: 0
          },
          {
            projectName: "项目二",
            id: 1
          },
          {
            projectName: "项目三",
            id: 3
          }
        ];
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
      // 日历
      initData(cur) {
        // console.log(cur,"0000")
        var date;
        if (cur) {
          date = new Date(cur);
        } else {
          date = new Date();
        }
        this.currentDay = date.getDate();
        this.currentYear = date.getFullYear();
        // console.log(this.currentYear,"123")
        this.currentMonth = date.getMonth() + 1;
        this.currentWeek = date.getDay(); // 1...6,0
        if (this.currentWeek == 0) {
          this.currentWeek = 7;

        }
        // console.log(this.currentYear,"年")
        var str = this.formatDate(this.currentYear, this.currentMonth, this.currentDay);
        // console.log("today:" + str + "," + this.currentWeek);
        this.days.length = 0;
        // 今天是周日，放在第一行第7个位置，前面6个
        for (var i = this.currentWeek - 1; i >= 0; i--) {
          var d = new Date(str);
          d.setDate(d.getDate() - i);
          // console.log("y:" + d.getDate());
          this.days.push(d);
        }
        for (var i = 1; i <= 35 - this.currentWeek; i++) {
          var d = new Date(str);
          d.setDate(d.getDate() + i);
          this.days.push(d);
        }
        // console.log(this.days,"riqi")
        // console.log(this.jsonHtml,"shuju")
        for (var l = 0; l < this.jsonHtml.length; l++) {
          this.jsonHtml[l].date = new Date(this.jsonHtml[l].date)
          this.cc.push(this.jsonHtml[l])
        }
        for (var e = this.cc.length - 1; e >= 0; e--) {
          if (this.cc.indexOf(this.cc[e]) !== this.cc.lastIndexOf(this.cc[e])) {
            this.cc.splice(e, 1);
          }
        }
        // console.log(this.cc,"shuju3333")
      },
      pick(date) {
        // alert(this.formatDate( date.getFullYear() , date.getMonth() + 1, date.getDate()));
        // console.log(this.formatDate( date.getFullYear() , date.getMonth() + 1, date.getDate()))
        this.Missiontime = this.formatDate(date.getFullYear(), date.getMonth() + 1, date.getDate())
      },
      pickPre(year, month) {
        this.cc = []
        // console.log(year, month,"年月")
        // setDate(0); 上月最后一天
        // setDate(-1); 上月倒数第二天
        // setDate(dx) 参数dx为 上月最后一天的前后dx天
        var d = new Date(this.formatDate(year, month, 1));

        d.setDate(0);
        this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
      },
      pickNext: function (year, month) {
        this.cc = []
        var d = new Date(this.formatDate(year, month, 1));
        d.setDate(35);
        this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
      },
      pickYear: function (year, month) {
        alert(year + "," + month);
      },
      // 返回 类似 2016-01-02 格式的字符串
      formatDate: function (year, month, day) {
        var y = year;
        var m = month;
        if (m < 10) m = "0" + m;
        var d = day;
        if (d < 10) d = "0" + d;
        return y + "-" + m + "-" + d
      },
      changetype() {
        this.type = !this.type
      }

    },
    computed:{
        total(){
            var hours=0
            for(var i=0;i<this.customer_Mess_Data.length;i++){
                hours+=+this.customer_Mess_Data[i].duration[0]
            }
            return hours
        }
    }

  };

</script>
<style scoped>
  .hoursReport {
    background-color: #f0eeee;
  }

  .el-header {
    background-color: white;
    /* border: 1px solid black; */
    color: #1492ff;
    width: 99%;
    margin: 0 auto;
    margin-top: 12px;
    /* text-align: center; */
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


  /*  */
  * {
    box-sizing: border-box;
  }

  ul {
    list-style-type: none;
  }

  body {
    font-family: Verdana, sans-serif;
    background: #E8F0F3;
    position: relative;
  }

  #calendar {
    width: 27%;
    margin: 0 auto;
    position: absolute;
    top: 75px;
    right: 34px;
    z-index: 100;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.1), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
  }

  .month {
    width: 100%;
    background: #00B8EC;
  }

  .month ul {
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: space-between;
  }

  .year-month {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
  }

  .year-month:hover {
    background: rgba(150, 2, 12, 0.1);
  }

  .choose-year {
    padding-left: 20px;
    padding-right: 20px;
  }

  .choose-month {
    text-align: center;
    font-size: 1.5rem;
  }

  .arrow {
    padding: 30px;
  }

  .arrow:hover {
    background: rgba(100, 2, 12, 0.1);
  }

  .month ul li {
    color: white;
    font-size: 20px;
    text-transform: uppercase;
    letter-spacing: 3px;
  }

  .weekdays {
    margin: 0;
    padding: 10px 0;
    background-color: #00B8EC;
    display: flex;
    flex-wrap: wrap;
    color: #FFFFFF;
    justify-content: space-around;
  }

  .weekdays li {
    display: inline-block;
    width: 13.6%;
    text-align: center;
  }

  .days {
    padding: 0;
    background: #FFFFFF;
    margin: 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }

  .days li {
    list-style-type: none;
    display: inline-block;
    width: 14.2%;
    text-align: center;
    padding-bottom: 15px;
    padding-top: 15px;
    font-size: 1rem;
    color: #000;
    position: relative;
  }

  .days li .active {
    padding: 6px 8px;
    /* border-radius: 50%; */
    background: #00B8EC;
    color: #fff;
  }

  .days li .active1 {
    position: absolute;
    top: 12px;
    left: 24px;
    padding: 6px 10px;
    border-radius: 50%;
    background: orange;
    color: #fff;

  }

  .days li .active2 {
    position: absolute;
    top: 12px;
    left: 24px;
    padding: 6px 10px;
    border-radius: 50%;
    background: red;
    color: #fff;
  }

  .days li .active3 {
    position: absolute;
    top: 12px;
    left: 24px;
    padding: 6px 10px;
    border-radius: 50%;
    background: #aaa;
    color: #fff;
  }

  .days li .active4 {
    position: absolute;
    top: 12px;
    left: 24px;
    padding: 6px 10px;
    border-radius: 50%;
    background: green;
    color: #fff;
  }

  .days li .active5 {
    position: absolute;
    top: 12px;
    left: 24px;
    padding: 6px 10px;
    border-radius: 50%;
    background: skyblue;
    color: #fff;
  }

  .days li .other-month {
    padding: 5px;
    color: gainsboro;
  }

  .days li:hover {
    background: #e1e1e1;
  }

  .Notes {
    display: flex;
    flex-wrap: wrap;
    /* justify-content: space-around; */
    /* border: 1px solid red; */
  }

  .Notes li {
    margin-right: 10px
  }

  .Notes .passed {
    padding: 6px 16px;
    background: green;
    border-radius: 50%;
  }

  .Notes .noFillin {
    padding: 6px 16px;
    background: red;
    border-radius: 50%;
  }

  .Notes .noApproval {
    padding: 6px 16px;
    background: skyblue;
    border-radius: 50%;
  }

  .Notes .reject {
    padding: 6px 16px;
    background: orange;
    border-radius: 50%;
  }

  .Notes .revoke {
    padding: 6px 16px;
    background: #aaa;
    border-radius: 50%;
  }

  .total {
    margin-left: 1300px;
  }
  .total p{
    display: inline-block;
    font-size: 20px
  }
  .total span{
    color: red;
    font-size: 20px
  }

</style>
