<template>
  <div class="hoursReport">
    <el-container>
      <el-header style="height: 61px">
        <el-row :gutter="20">
          <el-col :span="1.5">
            <div
              class="grid-content bg-purple"
              style="height: 61px;border-bottom:3px solid #1492ff"
            >
              <b>工时填报</b>
            </div>
          </el-col>
          <el-col :span="1" :offset="21">
            <el-button type="primary">工作日历</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="height:768px;">
        <el-form
          ref="formInline"
          :model="formInline"
          :inline="true"
          size="medium"
          class="form-inline"
          style="height:66px;border-bottom:1px dashed #aaa;padding-left:110px;padding-top:20px"
        >
          <!-- 人员范围 -->
          <el-form-item label="选择模板：" prop="capitalCreditID">
            <el-select  v-model="formInline.capitalCreditID"  clearabl  eplaceholder="请选择" @change="handleAddApp">
              <el-option
                v-for="item in zjxyOptions"
                :key="item.id"
                :label="item.cnName"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="item-button">
            <el-button type="success" @click="addhoursEvent">新增</el-button>
            <el-button type="primary" @click="Submission">提交</el-button>
          </el-form-item>
        </el-form>
        <div class="main-table-minHeight" style="padding:60px">
          <el-table  border :data="customer_Mess_Data" class="sydneyTable" :stripe="true"
            tooltip-effect="dark" style="width: 1400px" size="mini" header-cell-class-name="nihao">
            <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
            <el-table-column label="项目" prop="projectName" width="100" align="center" />
            <el-table-column prop="content" class-name="view_td" label="任务" align="center"></el-table-column>
            <el-table-column prop="name" label="审批人" width="150" align="center"></el-table-column>
            <el-table-column prop="duration" label="任务时长" width="120" align="center"></el-table-column>
            <el-table-column prop="duration3" label="任务时间" width="120" align="center"></el-table-column>
            <el-table-column prop="currentdate" label="提交时间" width="120" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
              <template slot-scope="scope">
                <el-button @click.native.prevent="editEvent(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
                <el-button @click.native.prevent="deleteEvent(scope.$index, scope.row)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-dialog
          title="编辑项目"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose">
          <el-form :model="personForm"  ref="personForm" :inline="true">
            <el-form-item label="请选择项目：" >
              <el-select filterable v-model="personForm.projectName" placeholder="请选择">
                <el-option v-for="item in projectlist" :key="item.id" :label="item.projectName" :value="item.projectName"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="请填写任务内容：" >
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
        <div id="dateContainer">
          <div class="date__showNowTime">
            今天是 <span v-text="ynow"></span>年<span v-text="mnow+1"></span>月<span v-text="dnow"></span>日
          </div>
          <div class="nowTime">
            <button class="preMon" @click="preMon">上个月</button>
            <div class="thisMon">
                <span v-text="ynow"></span>/
              <span v-text="mnow+1"></span>
            </div>
            <button class="nextMon" @click="nextMon">下个月</button>
          </div>
          <table border="1" id="table">
    
          </table>
    
        </div>
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
      customer_Mess_Data:[],
      personForm: {},
      projectlist:[],

      // 日历
      newDate: '',//当前的日期的信息
      ynow: '',//当前的年数
      mnow: '',//当前的月份
      dnow: '',//当前的天数
      firstDay: '',//第一天
      firstnow: '',//当前的星期
      m_days: [],//每个月的天数
      tr_str: '',//行数
      jsonHtmls:[]
    };
  },
  computed: {},
  created() {
    this.getStatus();
    this.projectdata();
    this.queryEvent();
    this.initData(null);
  },
  mounted() {
      //画出当前的月份的天数对应的表格
      this.getDaysInfo();
      //进行数据的获取，显示到对应的位置
  },
  methods: {
    //查询
    queryEvent(){
      manhourservice.querydata()
        .then(res => {
          console.log(res,"查询")
          this.customer_Mess_Data = res.data
        })
    },
    // 添加的确定按钮
    add_tem_event(){
        console.log(this.personForm,"personForm")
        this.dialogVisible=false
        console.log(this.personForm.content,this.personForm.duration,this.personForm.projectName,"内容时间下拉")
        this.handleAddApp()
    },
    // 下拉
    getStatus() {
      var _this = this;
      _this.zjxyOptions = [
        {
          cnName: "上一条填报",
          id: 0
        },
      ];
    },
    // 项目
    projectdata(){
      var _this = this;
      _this.projectlist = [
        {
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
    addhoursEvent(){
      this.dialogVisible = true
      this.personForm={}
    },
    
    // 上一个模板和确定按钮的执行
    handleAddApp(){
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
      console.log(currentdate,"当前时间")
      this.personForm.currentdate = currentdate;

          this.customer_Mess_Data.push(this.personForm)
          console.log(this.personForm,"111")
      //   })
      //   .then(() => {
      //     this.$message.success('添加成功')
      //   })
        // this.queryEvent();
    },
    // 提交
    Submission(){
      console.log(this.customer_Mess_Data,"提交")
    },
    editEvent(index, row) {
      this.dialogVisible = true
      console.log(row,"编辑")
    },


    // 日历
    initData: function(cur) {
      var date;
      if (cur) {
      date = new Date(cur);
      
      } else {
      date = new Date();
      }
      this.currentDay = date.getDate();
      this.currentYear = date.getFullYear();
      console.log(this.currentYear,"123")
      this.currentMonth = date.getMonth() + 1;
      this.currentWeek = date.getDay(); // 1...6,0
      if (this.currentWeek == 0) {
      this.currentWeek = 7;
      
      }
      // console.log(this.currentYear,"年")
      var str = this.formatDate(this.currentYear , this.currentMonth, this.currentDay);
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
  },
    getDaysInfo() {
      var _this = this;
      this.sureDate(_this);
    },
    is_leap(year) {//判断是不是闰年
      return (year%100==0?(year%400==0?1:0):(year%4==0?1:0));
    },
    //下面的是画表格的方法，这个方法会根据数据画出我们需要的表格
    drawTable(jsonHtml) {
      var _this = this;
      var str = `
      <tr class="xiqi">
        <td class="isRed">星期日</td>
        <td>星期一</td>
        <td>星期二</td>
        <td>星期三</td>
        <td>星期四</td>
        <td>星期五</td>
        <td class="isRed">星期六</td>
      </tr>`;
      var idx = '',date_str = '',isRed = '',hasMsg='';
      for(var i = 0; i< _this.tr_str; i++) {
        str+='<tr>';
        for(var k = 0; k < 7; k++) {
          // console.log(_this.firstnow,"0000")
            idx = i*7+k;
            isRed = (k===0||k===6)?"isRed":"";
            date_str=idx-_this.firstnow+1;

          (date_str<=0 || date_str>this.m_days[this.mnow]) ? date_str=" " : date_str=idx-_this.firstnow+1;
          date_str==_this.dnow?hasMsg='<td class="thisDay" date="'+date_str +'"><span  class="'+isRed +'">'+date_str+'</span></td>':hasMsg='<td date="'+date_str +'"><span  class="'+isRed +'">'+date_str+'</span></td>';
        
        // 符合这个月的数据挑出来
        for(var d=0;d<jsonHtml.length;d++){
          var sss=jsonHtml[d].date.substring(5,6)
          // console.log(sss,this.mnow+1,"0000")
          if(sss==this.mnow+1){
            _this.jsonHtmls.push(jsonHtml[d]);
          }
        }
        // 去重
        for(var e=_this.jsonHtmls.length-1;e>=0;e--){
          if(_this.jsonHtmls.indexOf(_this.jsonHtmls[e])!==_this.jsonHtmls.lastIndexOf(_this.jsonHtmls[e])){
            _this.jsonHtmls.splice(e,1);
          }
        }
        // console.log(_this.jsonHtmls,"111")
        for(var l = 0, len =_this.jsonHtmls.length; l < len; l++) {
            var ccc=_this.jsonHtmls[l].date.substring(7,9)
              if(date_str==ccc){
                var newStr = '<p>'+_this.jsonHtmls[l].msg +'</p>';
                date_str==_this.dnow?hasMsg='<td class="thisDay" date="'+date_str +'"><span  class="'+isRed +'">'+date_str+'</span>'+ newStr+'</td>':hasMsg='<td date="'+date_str +'"><span  class="'+isRed +'">'+date_str+'</span>'+ newStr+'</td>';
              }
            }
            str+=hasMsg;
          }
        str+='</tr>';
      }
      var table = document.getElementById('table');
      table.innerHTML = str;
    },
    //两个参数代表的含义分别是this对象以及判断当前的操作是不是在进行月份的修改
    sureDate(_this,other) {
      this.newDate = new Date();
      this.ynow = this.newDate.getFullYear();
      console.log(this.newDate,"下")
      if(!other) {
        console.log(this.newDate,"000")
        this.mnow=this.newDate.getMonth(); //月份
      }
      
      this.dnow=this.newDate.getDate(); //今日日期
        console.log(this.dnow,"下下")
      this.firstDay = new Date(this.ynow,this.mnow,1);
      this.firstnow=this.firstDay.getDay();
      this.m_days = [31,28+this.is_leap(this.ynow),31,30,31,30,31,31,30,31,30,31];
      this.tr_str = Math.ceil((_this.m_days[this.mnow] + this.firstnow)/7);
      this.showMsg();
    },
    preMon() {
      this.jsonHtmls=[]
      var _this = this;
      this.mnow--;
      if(this.mnow<1){
        this.mnow=12-1;
      }
      this.sureDate(_this,"up");
    },
    nextMon() {
      this.jsonHtmls=[]
      var _this = this;
      this.mnow++;
      if(this.mnow>12-1){
        this.mnow=1-1;
      }
      this.sureDate(_this,"next");
      
    },
    //通过接口返回的是我们当前的月份对应在日历中需要处理的事项
    showMsg() {
      var jsonHtml = [{
        date: "2019-3-3",
        msg: '吃饭'
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
        date:"2019-4-4",
        msg: '睡觉'
      },
      {
        date:"2019-5-5",
        msg: '打豆豆'
      },
      {
        date: "2019-6-6",
        msg: '豆豆不在家'
      },
      {
        date: "2019-7-7",
        msg: '~~~~~'
      },
      {
        date: "2019-8-8",
        msg: '怎么办~~~~'
      },
      {
        date: "2019-9-9",
        msg: '找豆豆'
      }];
      this.drawTable(jsonHtml)
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
.item-button{
  float: right;
  margin-right: 26px;
}
</style>
