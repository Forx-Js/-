<template>
    <div id="project_role">
            <el-container>
                <div class="aside" style="width:541px;height: 845px;">
                    <div class="js1">
                        <div class="jsmc">角色名称</div>
                        <el-form :inline="true" style="border-top: 1px dashed #aaa" :model="formInline" ref="formInline" size="mini" class="form-inline">
                            <el-form-item>
                                <div class="input1">
                                    <input type="text" placeholder="输入用户名和登录名">
                                    <p class="ss1" @click="queryEvent3()">搜索</p>
                                </div>  
                                <el-button class="removePadding5" @click="editEvent" style="margin-left:0px">
                                    <!-- <img src="../../../../static/image/icon_shuaxin.png" alt=""> -->
                                    <span>修改</span>
                                </el-button>
                                    <el-button class="removePadding4" @click="deleteEvent" >
                                    <!-- <img src="../../../../static/image/icon_del.png" alt=""> -->
                                    <span>删除</span>
                                </el-button>
                                <el-button class="removePadding3" @click="addRoleEvent" style="margin-left:18x">
                                    <!-- <img src="../../../../static/image/icon_add.png" alt=""> -->
                                    <span>角色</span>
                                </el-button>
                            </el-form-item>
                        </el-form>
                        <ul class="jsdata" style="border-top: 1px dashed #aaa;">
                            <li
                                v-for="(item,index) in  list_Data" 
                                :key="index"
                                @click="_add(item.roleID,index,item.roleName)"
                                :class="[index === cur1?'active':'']"
                            ><i><img src=""  alt="图标"></i>{{item.roleName}}
                            </li>
                            <!-- ../../layout/components/images/perpson.png -->
                        </ul>
                    </div>
                </div>
            </el-container>
            <el-dialog :before-close="handleClose" :title="dialogTitle" :visible.sync="handlerCustomDialogVisible" class="add-edit-view-dialog">
                <el-form :model="list_Data" ref="list_Data" :inline="true" :disabled="disabled" class="formOfDialog">
                    <el-form-item label="角色名称" label-width="100px" prop="roleName" v-if="type1 == 1">
                        <el-input v-model="list_Data.roleName"></el-input>
                    </el-form-item>
                    <el-form-item label="确定删除？" label-width="100px"  prop="roleName" v-else-if="type1 == 2">
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="cancelEvent">取 消</el-button>
                    <el-button v-if="type1 == 1" type="primary" @click="addNewRoleEvent('list_Data')">确 定</el-button>
                    <el-button v-else-if="type1 == 2" type="primary" @click="removeNewCustomerEvent('list_Data')">确 定</el-button>
                </div>
            </el-dialog> 
    </div>
</template>
<script>
export default {
    data () {
        return {
            //查询条件
            formInline:{
                customerName:"",
                capitalCreditID:"",
                customerAddr:"",
                // list:[],
                jsdata:[],
                cur:0,
            },
            cur1:2,
            type1:1,
            zjxyOptions:[],//资金信用select
            khlbOptions:[],//客户类别select
            jyfsOptions:[],//经营方式select
            // 分页
            customer_Mess_Data:[],
            customer_Mess_Page_Total:0,//客户信息table数据总数
            customer_Mess_Page_Size:10, //客户信息table每页显示个数
            customer_Mess_Page_Index: 1, //客户信息table当前页数
            customer_Mess_Page_status: 0,

            handlerCustomDialogVisible:false,//增改查客户信息的dialog的显隐
            dialogTitle:"添加客户信息",//增改查客户信息的dialog的标题
            disabled:false,//增改查客户信息的dialog内的form表单及关联数据的可操作与否
            customerForm:{},//增改查客户信息的dialog内的form表单

            customerIDRec:"",//记录当前行的唯一id

            //关联的联系人信息
            linkMan_Data:[],
            linkMan_Page_Total:0,
            linkMan_Page_Size: 10,
            linkMan_Page_Index: 1,

            //关联的项目信息
            project_Data:[],
            project_Page_Total:0,
            project_Page_Size: 10,
            project_Page_Index: 1,

            //项目角色信息  ni
            list_Data:{},
            list_Data_roleName:[],
            list_Data_roleName2:[],
            keepid:'',
            keepname:'',
            keepindex:0,
            //项目人员信息
            person_Data:{},

        }
    },
    created () {
        // this.queryEvent();5
        // this.getZJXYSel();//资金信用select
        // this.getDialogSelect();//客户类别select、经营方式select
    },
    methods: {    
        openDetails(row, column, cell, event){
            // console.log("000")
            if(cell.cellIndex==1){
                this.viewEvent("",row)
            }
        },
        // 添加id
        _add(roleID,index,roleName){
            this.keepid=roleID
            this.keepindex=index
            this.keepname=roleName
            this.cur1=index
            console.log(this.keepid,"111") 
            console.log(this.keepindex,"222") 
            console.log(this.keepname,"222") 
        },
        change(index){
            this.formInline.cur = index;
        },
        //查询
        queryEvent(){
            var _this = this;
            _this.axios.get(_this.testIP + "/role/list",{
                params:{
                    type:2,
                }
            })
            .then(function(res) {
                console.log(res)
                if(res.data.result == "0") {
                    _this.list_Data = res.data.data;
                    console.log( res.data.data)
                    // for循环 
                for(var i=0;i<_this.list_Data.length;i++){
                        _this.list_Data_roleName.push(_this.list_Data[i].roleName)
                    }
                    for(var i=0;i<_this.list_Data_roleName.length;i++){
                        var flag=true;
                        for(var j=0;j< _this.list_Data_roleName2.length;j++){
                            if( _this.list_Data_roleName2[j]==_this.list_Data_roleName[i]){
                                flag=false;
                                break;
                            }
                        }
                        if(flag){
                            _this.list_Data_roleName2.push(_this.list_Data_roleName[i]);
                        }
                        // console.log(_this.list_Data_roleName2)
                    }
                }else{
					_this.$message({duration:1000,
                        message: "数据获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        queryEvent3(){
            var _this = this;
            // console.log(this.keepid,"参数")
            _this.axios
            .get(_this.testIP + "/role/users",{
                params:{
                    roleID:this.keepid,
                    keyWord:_this.list_Data_roleName1
                }
            })
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.person_Data = res.data.data;
                }else{
                    _this.$message({duration:1000,
                        message: "数据获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        addNewRoleEvent(formName){
            var _this = this;
            _this.$refs[formName].validate((valid) => {
                if (valid) {
                    if(this.add_edit == "add"){
                        ;
                        _this.axios.post(_this.testIP + "/role/add",{
                            type:"2",
                            roleName:_this.list_Data.roleName
                        }) 
                        .then(function(res) {
                            if(res.data.result == "0") {  
                                // console.log(res.data.data)        
                                _this.$message({
                                    duration:1000,
                                    message: "添加角色成功",
                                    type: 'success'
                                });
                                _this.handlerCustomDialogVisible = false;
                                _this.queryEvent();
                            }else{
                                _this.$message({duration:1000,
                                    message: res.data.message,
                                    type: 'warning'
                                });
                            }
                        })
                        .catch(function(error) {
                            ;
                        })
                    }
                        else
                        {
                            delete _this.list_Data.operTime;
                            ;
                            _this.axios.post(_this.testIP + '/role/update',{
                                type:"2",
                                roleID:_this.keepid,
                                roleName:_this.list_Data.roleName
                            }) 
                            .then(function(res) {
                                if(res.data.result == "0") {
                                    _this.$message({duration:1000,
                                        message: "修改角色信息成功",
                                        type: 'success'
                                    });
                                    _this.handlerCustomDialogVisible = false;
                                    _this.queryEvent();
                                }else{
                                    _this.$message({duration:1000,
                                        message: res.data.message,
                                        type: 'warning'
                                    });
                                }
                            })
                            .catch(function(error) {
                                ;
                            })
                        }   
                    } else {
                        _this.$message({duration:1000,
                            message: "校验未通过，请检查！",
                            type: 'warning'
                        });
                        return false;
                    }
                });
        },
        //重置查询条件、重新查询数据
        resetEvent(formName){
            this.customer_Mess_Page_Index = 1;
            this.$refs[formName].resetFields();
            this.queryEvent();
        },
        //切换页码
        bidMessPageChange(val){
            this.customer_Mess_Page_Index = val;
            this.queryEvent();
        },
        //主界面--行--编辑
        editEvent(index,row){
            this.disabled = false;
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "修改系统角色";
            this.add_edit = "change";
            this.type1=1;
            var _this = this;
            setTimeout(function(){
                try {_this.$refs['list_Data'].clearValidate();} catch (e) {};
            });
        },
        //主界面--行--查看
        viewEvent(index,row){
            var _this = this;
            try {_this.$refs['list_Data'].clearValidate();} catch (e) {};
            _this.customerIDRec = row.customerID;
            _this.disabled = true;
            _this.axios.get(_this.testIP + '/customer/query-cusXqMsg?customerID='+_this.customerIDRec)
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.customerForm = res.data.data;
                    _this.linkMan_Page_Index = 1;//关联联系人--初始化页码为1
                    _this.queryReaPerson(_this.customerIDRec);//关联联系人
                    _this.project_Page_Index = 1;//关联项目信息--初始化页码为1
                    _this.queryReaProject(_this.customerIDRec);//关联项目信息
                    _this.dialogTitle = "查看客户信息";
                    _this.handlerCustomDialogVisible = true;
                }else{
					_this.$message({duration:1000,
                        message: "数据获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        //主界面--行--删除
        deleteEvent(index,row){
            this.$confirm('确定删除此客户?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var _this = this;
                _this.$http.post(_this.testIP + '/role/delete',{
                        type:"2",
                        roleID:this.keepid
                }) 
                .then(function(res) {
                    if(res.data.result == "0") {
                        console.log(res)
                        _this.$message({duration:1000,
                            message: "删除成功",
                            type: "success",
                        });
                        _this.queryEvent();
                    }else{
                        _this.$message({duration:1000,
                            message: "删除失败",
                            type: "warning",
                        });
                    }
                })
                .catch(function(error) {
						;
                });
            }).catch(() => {

            });
        },
        //打开--添加客户信息--dialog
        // 打开添加角色信息
        addRoleEvent(){
            console.log("333")
            this.disabled = false;
            // this.customerForm = {};
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "新增项目角色";
            this.add_edit = "add";
            this.type1=1;
            var _this = this;
            setTimeout(function(){
                try {_this.$refs['list_Data'].clearValidate();} catch (e) {};
            });
        },
        //资金信用select
        getZJXYSel(){
            var _this = this;
            _this.zjxyOptions = [
                {
                    cnName:" 离职人员",
                    id:0,
                },
                {
                    cnName: "在职人员",
                    id:1,
                },
                {
                    cnName:" 实习生",
                    id:3,
                }
            ]
            //   console.log( _this.zjxyOptions ,"777")
        },
        //客户类别select、经营方式select
        getDialogSelect(){
            var _this = this;
            //客户类别
            _this.axios.get(_this.testIP + '/common/dict/CustomerType')
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.khlbOptions = res.data.data;
                }else{
					_this.$message({duration:1000,
                        message: "客户类别获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
            //经营方式
            _this.axios.get(_this.testIP + '/common/dict/OperationMode')
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.jyfsOptions = res.data.data;
                }else{
					_this.$message({duration:1000,
                        message: "经营方式获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        //客户类别改变
        customerTypeChange(item){
            var _this = this;
            _this.khlbOptions.filter(function(itemx,index){
                if(item == itemx.id){
                    _this.customerForm.customerTypeName = itemx.cnName;
                }
            })
        },
        //经营方式改变
        businessLicenseChange(item){
            var _this = this;
            
            _this.jyfsOptions.filter(function(itemx,index){
                if(item == itemx.id){
                    _this.customerForm.operationModeName = itemx.cnName;
                }
            })
        },
        //资金信用改变
        zjxyChange(item){
            var _this = this;
            _this.zjxyOptions.filter(function(itemx,index){
                if(item == itemx.id){
                    _this.customerForm.capitalCreditName = itemx.cnName;
                    console.log( _this.customerForm.capitalCreditName,"44")
                }
            })
        },
        //客户关联的联系人信息
        queryReaPerson(customerID){
            var _this = this;
            _this.axios.get(_this.testIP + '/customer/query-contact?customerID='+customerID+"&pageNo="+_this.linkMan_Page_Index+"&pageSize="+_this.linkMan_Page_Size)
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.linkMan_Data = res.data.data;
                    _this.linkMan_Page_Total = res.data.totalCount;
                }else{
					_this.$message({duration:1000,
                        message: "联系人信息获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        //客户关联的联系人信息的页码变化
        linkManPageChange(val){
            this.linkMan_Page_Index = val;
            this.queryReaPerson(this.customerIDRec);
        },
        //编辑/查看客户--联系人信息--删除
        deleteEventxx(index,row){
            this.$confirm('确定删除此联系人?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var _this = this;
                _this.$http.post(_this.testIP + '/customer/del-contact?linkID='+row.linkID)
                .then(function(res) {
                    if(res.data.result == "0") {
                        _this.$message({duration:1000,
                            message: "删除联系人成功",
                            type: 'success'
                        });
                        _this.queryReaPerson(_this.customerIDRec);
                    }else{
                        _this.$message({duration:1000,
                            message: res.data.message,
                            type: 'warning'
                        });
                    }
                })
                .catch(function(error) {
                    _this.$message({duration:1000,
                        message: error,
                        type: 'warning'
                    });
                });
            }).catch(() => {

            });
        },
        //客户关联的项目信息
        queryReaProject(customerID){
            var _this = this;
            _this.axios.get(_this.testIP + '/customer/query-bidPrj?customerID='+customerID+"&pageNo="+_this.project_Page_Index+"&pageSize="+_this.project_Page_Size)
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.project_Data = res.data.data;
                    _this.project_Page_Total = res.data.totalCount;
                }else{
					_this.$message({duration:1000,
                        message: "项目信息获取失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        //客户关联的项目信息的页码变化
        projectPageChange(val){
            this.project_Page_Index = val;
            this.queryReaProject(this.customerIDRec);
        },
        handleClose(done) {
            if(this.dialogTitle != "查看客户信息"){
                this.$confirm("确认关闭？")
                .then(_ => {
                    this.project_Page_Index = 1, //当前页数
                    this.linkMan_Page_Index = 1, //当前页数
                    done();
                })
                .catch(_ => {});
            }else{
                done();
            }
        },
        isStatus(row, column) {
            var status = row[column.property];
            if(status == undefined) {
                return "";
            }else if(status == '0'){
                return "未投标"
            }else if(status == '1'){
                return "处理中"
            }else if(status == '2'){
                return "已投标"
            }
        },
        dateFormat:function(row, column) {
            var date = row[column.property];
            if (date == undefined) {
                return "";
            }
            return moment(date).format("YYYY-MM-DD");
        },
        cancelEvent(){
            this.project_Page_Index = 1, //当前页数--关联的联系人信息
            this.linkMan_Page_Index = 1, //当前页数-关联的项目信息
            this.handlerCustomDialogVisible = false;
        }
    }
}
</script>
<style lang="scss" scoped>
.input1{
    width: 225px;
    display: inline-block;
    position: relative;
    input{
        width: 215px;
        height: 28px;
        // border: none;
        border-radius: 3px;
        border: 1px solid #e1e1e1;
        // border-color:none;
        outline: none;
        font-size: 13px;
        padding-left:10px;
    }
    .ss1{
        position: absolute;
        height: 28px;
        top: 2px;
        left: 150px;
        width: 64px;
        text-align: center;
        display: inline-block;
        background: #55a7f0;
        color: white;
        border-radius: 5%;
        cursor: pointer;
    }
}
.header{
    background: #b6c4c8;
    padding: 0;
    padding-top: 17px;
    ul{
        display: flex;
        li{
            list-style: none;
            color: white;
            width: 162px;
            height: 37px;
            display: inline-block;
            text-align: center;
            line-height: 37px;
        }
    }
}
.header .active{
    color: #7bc0ff;
    background: white;

}
.js1{
    // border: 1px solid red;
    width: 520px;
    height: 840px;
    margin: 0 auto;
    background: white;

}
.jsdata{
    // border-top: 1px dashed #aaa;
    width: 490px;
    margin: 0 auto;
}
.jsdata img{
    margin-right: 5px;
}
.jsdata li{
        list-style: none;
        font-size:17px;
        height: 28px;
        line-height: 25px;
        cursor: pointer;
        color: black
}
.fp{
    width: 520px;
    height: 400px;
    margin: 0 auto;
    margin-top: 10px;
    background: white;
}
.jsdata .active{
    background-color:#f0f7ff;
}
.aside{
    // border: 1px solid blue;
    background: #f2f2f2;
}
.main{
    // border: 1px solid blue;
    width: 400px;
}
#personmanage{
    min-width: 0px;
    // width: 900px;
}
.form-inline {
    padding-top: 16px;
    padding-left: 24px;
    height:60px;
    // border-top: 1px dashed #aaa;
    // border:1px solid red;
    .removePadding{
        padding: 5px 10px;
        margin-left: 7px;
        }
    img {
        width: 1em;
        vertical-align: bottom;
        }
    .removePadding5 {
        padding: 5px 1px;
        margin-left: 0px;
        }
    .removePadding4 {
        padding: 5px 1px;
        margin-left: 5px;
        }
    .removePadding3{
        padding: 5px 10px;
        margin-left: 56px;
        }

    }
.sydneyTable{
    margin-left: 0px;
    margin-top: 0px;
}
.jsmc{
    height: 43px;
    text-align: center;
    line-height:43px;
    color: #0f81d8;
    font-size: 15px;
}
.csh {
    margin-top: 14px;
    margin-left: 900px;
}
.Breadcrumb1 {
    width:81px;
    margin-top: 0px;
    margin:0 auto;
    color: #0f81d8;
    height: 19px;
    font-size:16px;
}
.el-input--mini .el-input__inner {
    width: 100px;
}
</style>