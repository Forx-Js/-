<template>
    <div id="permissions">
        <el-container>
            <!-- 以下是头部内容 -->
            <el-header class="header" style="height: 52px;width:99.3%;margin-left:11px">
                <router-link :to="{name:'system_role'}" class="xt">系统角色权限</router-link>
                <router-link :to="{name:'project_role'}" class="xm">项目角色权限</router-link>
            </el-header>
            <el-container>
                <router-view @aa="getData"></router-view>
                <!-- 以下是右边内容 -->
                <div class="main" style="width:1083px;height: 800px;">
                        <el-main id="personmanage" style=" min-width:0px;height: 818px; padding:0px">
                            <el-breadcrumb class="Breadcrumb1" style="border-buttom: 1px dashed #aaa;display:inline-block" separator="/">
                                系统模块树
                            </el-breadcrumb>
                            <el-button class="removePadding7" @click="add">
                                    <span>添加</span>
                            </el-button>
                            <el-button class="removePadding8" @click="tj">
                                    <span>提交</span>
                            </el-button>
                            <template>
                                <div class="tree">
                                        <module-tree
                                        :columns="columns"
                                        :tree-structure="true"
                                        :data-source="dataSource"
                                        @connect="addCustomerEvent"
                                        @del="deleteEvent"
                                        @handlechange="editEvent"
                                        @edit="querymodule"
                                        @dd="dd"
                                        ></module-tree>
                                </div>
                            </template>
                        </el-main>
                </div>
            </el-container>
        </el-container>
        <!-- 编辑和添加的弹框 -->
        <el-dialog
        :before-close="handleClose"
        :title="dialogTitle"
        :visible.sync="handlerCustomDialogVisible"
        class="add-edit-view-dialog1">
                <p class="modotypeshow">模块类型说明</p>
                <p class="UnknowModule">UnknowModule</p>
                <P class="UnknowModule">如果是分类,使用该值。</P>
                <div v-if="dialogTitle!='添加客户信息'" class="modotype">
                    <el-form :model="list_Data" ref="dataSource" :inline="true" :disabled="disabled" class="formOfDialog1">
                        <el-form-item label="模块/权限名" class="modename" label-width="100px" prop="moduleName" v-if="type1 == 1" >
                            <el-input v-model="dataSource_moduleName"></el-input>
                        </el-form-item>
                        <el-form-item label="URL" class="modename" label-width="100px" prop="url" v-if="type1 == 1" >
                            <el-input v-model="dataSource_moduleurl"></el-input>
                        </el-form-item>
                        <el-form-item prop="moduleType" class="modename" label="模块类型" label-width="100px">
                            <el-select filterable :disabled="disabled"  v-model="dataSource.moduleType" placeholder="请选择" >
                                <el-option
                                v-for="item in dataSource_moduleType"
                                :key="item.moduleType"
                                :label="item.name"
                                :value="item.moduleType"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="权限标识" class="modename" label-width="100px" prop="moduleNameEn" v-if="type1 == 1" >
                            <el-input v-model="dataSource_moduleNameEn"></el-input>
                        </el-form-item>
                        <el-form-item label="排序号" class="modename" label-width="100px" prop="sortNum" v-if="type1 == 1" >
                            <el-input v-model="dataSource_modulesortNum"></el-input>
                        </el-form-item>
                        <el-form-item label="funcName" class="modename" label-width="100px" prop="funcName" v-if="type1 == 1" >
                            <el-input v-model="dataSource_funcName"></el-input>
                        </el-form-item>
                        <el-form-item label="图标" label-width="100px" prop="fileID">
                            <label style="display:inline-block">
                                <input type="file" id="SelecyFile_Liu"  style="display:none" @change="uploadZCFile"/>
                                <el-button size="mini" class="ico" @click="selectFile">上传</el-button>
                            </label>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="cancelEvent">取 消</el-button>
                        <el-button v-if="type1 == 1" type="primary" @click="addNewCustomerEvent('dataSource')">确 定</el-button>
                        <el-button v-else-if="type1 == 2" type="primary" @click="removeNewCustomerEvent('dataSource')">确 定</el-button>
                    </div>
                </div>
        </el-dialog>
    </div>
</template>
<script>
import ModuleTree  from '../treeTable/vue/ModuleTree'
import Sortable from 'sortablejs'
export default {
    data () {
            return {
                //查询条件
                formInline:{
                    customerName:"",
                    capitalCreditID:"",
                    customerAddr:"",
                    // list:['系统','项目'],
                    list:[
                        {
                            item:'系统',
                            data:'system_role'
                    },{
                            item:'项目',
                            data:'project_role'
                    }],
                    jsdata:[],
                    cur:0,
                },
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

                //项目角色信息   ni
                list_Data:{},
                list_Data_roleName:"",
                keepid:'user',
                keepindex:0,
                //项目人员信息
                person_Data:{},
                list_Data_roleName:"",
                module_Data:{},
                // 模块树
                columns:[
                    {
                        text: '系统模块',
                        dataIndex: 'moduleName',
                    },
                ],
                dataSource:[],
                dataSource_moduleName:"",
                dataSource_moduleurl:"",
                dataSource_moduleNameEn:"",
                dataSource_modulesortNum:"",
                dataSource_funcName:"",
                row_id:"",
                dataSource_icon:"",
                dataSource_moduleType:[
                    {
                        moduleType:1,
                        name:"菜单",
                    },
                    {
                        moduleType:2,
                        name:"功能",
                    }
                ],
                dataArr:"",

        }
    },
    components: {
        ModuleTree
    },
    created () {
        // this.querymodule();
        // this.getZJXYSel();//资金信用select
        // this.getDialogSelect();//客户类别select、经营方式select
    },
    methods: {
        dd(data){
            // console.log(data,"datadatadata")
            for(var j=data.length-1;j>=0;j--){
                if(data.indexOf(data[j])!==data.lastIndexOf(data[j])){
                    data.splice(j,1);
                }
            }
            // console.log(data,"1111111111111111")
            var me = this;
            me.dataArr = "";
            for (var i = 0; i < data.length; i++) {
                me.dataArr += data[i].moduleID + ","+ data[i].limit+"|";
            }
            // console.log(me.dataArr,"Arr")
            
        },
        tj(){
            let _this = this;
            _this.axios
            .post(_this.testIP + "/role/update-module",{
                    roleID:_this.keepid,
                    data:_this.dataArr,
            })
            .then(function(res) {
                if(res.data.result == "0") {
                    // console.log(res.data.data,"提交");
                }else{
                    _this.$message({duration:1000,
                        message: "提交失败",
                        type: 'warning'
                    });
                }
            })
            .catch(function(error) {
                ;
            })
        },
        add(){
            this.dataSource_moduleName="";
            this.dataSource_moduleurl="";
            this.dataSource_moduleNameEn="";
            this.dataSource_modulesortNum="";
            this.dataSource_funcName="",
            this.row_id="";
            this.dataSource_icon="",
            this.row_id="0";
            this.disabled = false;
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "添加表单";
            this.add_edit = "add";
            this.type1=1;
            var _this = this;
            try {
                $("#SelecyFile_Liu")[0].value = null;
            } catch (error) {}
            setTimeout(function(){
                try {_this.$refs['dataSource'].clearValidate();} catch (e) {};
            });
        },
        openDetails(row, column, cell, event){
            if(cell.cellIndex==1){
                this.viewEvent("",row)
            }
        },
        getData(data){
                this.keepid=data
                this.querymodule()
        },
        change(index){
            this.formInline.cur = index;
        },
        //查询模块  ni
        querymodule(){
            var _this = this;
            // console.log(this.keepid,"cx")
            _this.axios
            .get(_this.testIP + "/module/list",{
                params:{
                    roleID:this.keepid,
                }
            })
            .then(function(res) {
                if(res.data.result == "0") {
                    _this.customer_Mess_Data = res.data.data;
                    _this.dataSource=res.data.data;
                    console.log(res.data.data,"模块");
                    _this.customer_Mess_Page_Total = res.data.totalCount;
                    _this.customer_Mess_Page_Index= res.data.pageIndex;
                    // for循环 用来表明复选框
                    for(var i=0;i<_this.customer_Mess_Data.length;i++){
                        if(_this.customer_Mess_Data[i].status==0){
                            _this.customer_Mess_Data[i].status=false;
                        }else{
                            _this.customer_Mess_Data[i].status=true;
                        }
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
        //主界面--行--查看
        viewEvent(index,row){
            var _this = this;
            try {_this.$refs['customerForm'].clearValidate();} catch (e) {};
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
        //删除
        deleteEvent(rowid){
            // console.log(rowid,"111111111")
            this.row_id=rowid;
            this.$confirm('确定删除此模块吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var _this = this;
                // console.log(_this.row_id,"2222")
                _this.$http.post(_this.testIP + "/module/delete", {
                    moduleID:_this.row_id
                })
                .then(function(res) {
                    if(res.data.result == "0") {
                        _this.$message({duration:1000,
                            message: "删除成功",
                            type: "success",
                        });
                        _this.querymodule();
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
        //修改dialog
        editEvent(rowid){
            this.dataSource_moduleName="";
            this.dataSource_moduleurl="";
            this.dataSource_moduleNameEn="";
            this.dataSource_modulesortNum="";
            this.dataSource_funcName="",
            this.row_id="";
            this.dataSource_icon="",
            this.row_id=rowid;
            this.disabled = false;
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "修改模块";
            this.add_edit = "change";
            this.type1=1;
            var _this = this;
            setTimeout(function(){
                try {_this.$refs['dataSource'].clearValidate();} catch (e) {};
            });
        },
        //添加dialog
        addCustomerEvent(rowid){
            this.dataSource_moduleName="";
            this.dataSource_moduleurl="";
            this.dataSource_moduleNameEn="";
            this.dataSource_modulesortNum="";
            this.dataSource_funcName="",
            this.row_id="";
            this.dataSource_icon="",
            this.row_id=rowid;
            this.disabled = false;
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "添加表单";
            this.add_edit = "add";
            this.type1=1;
            var _this = this;
            try {
                $("#SelecyFile_Liu")[0].value = null;
            } catch (error) {}
            setTimeout(function(){
                try {_this.$refs['dataSource'].clearValidate();} catch (e) {};
            });
        },
        //上传文件
        uploadZCFile(e) {
                var vm = this;
                var thisFile = e.target.files;
                var formData = new FormData();
                if (e.target.files.lenght == 0) {
                    return false;
                } else {
                    formData.append("file", thisFile["0"]);
                    $.ajax({
                    url: this.testIP + "/common/upload",
                    type: "POST",
                    headers: {
                        // "Content-Type": "application/x-www-form-urlencoded",
                        token: localStorage.getItem("token")
                    },
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(res) {
                        if (res.result == "0") {
                        setTimeout(_ => {
                            console.log(res,"55555")
                            var str=res.data.realName
                            // var str="hello";
                            var arr=str.substring(0,str.length-4);
                            console.log(arr,"222")
                            // vm.dataSource_icon=res.data.filePath;
                            vm.dataSource_icon=arr;
                            vm.$refs.dataSource.validate();
                        });
                        vm.$message({duration:1000,
                            message: "上传成功",
                            type: "success"
                        });
                        } else {
                        vm.$message({duration:1000,
                            message: "上传失败",
                            type: "warning"
                        });
                        try {
                            $("#SelecyFile_Liu")[0].value = null;
                        } catch (error) {}
                        }
                    },
                    error: function() {
                        vm.$message({duration:1000,
                        message: "上传失败",
                        type: "warning"
                        });
                        try {
                        $("#SelecyFile_Liu")[0].value = null;
                        } catch (error) {}
                    }
                    });
                }
        },
        //选择文件
        selectFile() {
            $("#SelecyFile_Liu").click();
        },
        //添加的确定按钮   ni
        addNewCustomerEvent(formName){
            var _this = this;
            console.log(_this.row_id,"pid")
            console.log(_this.dataSource_moduleurl,"url")
            console.log(_this.dataSource.moduleType,"moduleType")
            console.log(_this.dataSource_moduleName,"mname")
            console.log(_this.dataSource_moduleNameEn,"en")
            console.log(_this.dataSource_modulesortNum,"num")
            console.log(_this.dataSource_icon,"icon")
            console.log(_this.dataSource_funcName,"funcName")
            _this.$refs[formName].validate((valid) => {
                if (valid) {
                    if(this.add_edit == "add"){
                        ;
                        //添加角色的确定
                        _this.axios.post(_this.testIP + "/module/add",{
                            pID:_this.row_id,
                            url:_this.dataSource_moduleurl,
                            moduleType:_this.dataSource.moduleType,
                            moduleNameEn:_this.dataSource_moduleNameEn,
                            moduleName:_this.dataSource_moduleName,
                            icon:_this.dataSource_icon,
                            sortNum:_this.dataSource_modulesortNum,
                            funcName:_this.dataSource_funcName,
                        })
                        .then(function(res){
                            if(res.data.result == "0") {
                                _this.$message({
                                    duration:1000,
                                    message: "添加模块成功",
                                    type: 'success'
                                });
                                _this.handlerCustomDialogVisible = false;
                                _this.querymodule();

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
                    }else{
                            delete _this.dataSource.operTime;
                            ;
                            //编辑角色的确定
                            // console.log("修改")
                            // console.log(_this.row_id,"pid")
                            // console.log(_this.dataSource_moduleurl,"url")
                            // console.log(_this.dataSource.moduleType,"moduleType")
                            // console.log(_this.dataSource_moduleName,"mname")
                            // console.log(_this.dataSource_moduleNameEn,"en")
                            _this.axios.post(_this.testIP + '/module/update',{
                                moduleID:_this.row_id,
                                url:_this.dataSource_moduleurl,
                                moduleType:_this.dataSource.moduleType,
                                moduleNameEn:_this.dataSource_moduleNameEn,
                                moduleName:_this.dataSource_moduleName,
                                icon:_this.dataSource_icon,
                                sortNum:_this.dataSource_modulesortNum,
                                funcName:_this.dataSource_funcName,
                            })
                            .then(function(res) {
                                if(res.data.result == "0") {
                                    _this.$message({duration:1000,
                                        message: "修改模板成功",
                                        type: 'success'
                                    });
                                    _this.handlerCustomDialogVisible = false;
                                    _this.querymodule();
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
                    // console.log( _this.customerForm.capitalCreditName,"44")
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
.xt{
    color: white;
    width: 162px;
    height: 37px;
    display: inline-block;
    text-align: center;
    line-height: 37px;

}
.xt:hover{
        color: white
    }
.xm{
    color: white;
    width: 162px;
    height: 37px;
    display: inline-block;
    text-align: center;
    line-height: 37px;

}
.xm:hover{
        color: white
    }
.input{
    width: 225px;
    display: inline-block;
    position: relative;
    input{
        width: 225px;
        height: 28px;
        // border: none;
        border-radius: 3px;
        border: 1px solid #e1e1e1;
        // border-color:none;
        outline: none;
        font-size: 13px;
        padding-left:10px;
    }
    .ss{
        position: absolute;
        height: 28px;
        top: 2px;
        left: 161px;
        width: 64px;
        text-align: center;
        display: inline-block;
        background: #55a7f0;
        color: white;
        // border: 1px solid #55a7f0;
        border-radius: 5%;
    }
}
.header{
    background: #b6c4c8;
    padding: 0;
    padding-top: 17px;
    .router-link-active{
    color: #7bc0ff;
    background: white;
    }
}
.header .active{
    color: #7bc0ff;
    background: white;
}
.js{
    width: 520px;
    height: 405px;
    margin: 0 auto;
    background: white;
}
.jsdata{
    // border-top: 1px dashed #aaa;
    width: 490px;
    margin: 0 auto;
}
.jsdata li{
    list-style: none;
}
.fp{
    width: 520px;
    margin: 0 auto;
    margin-top: 10px;
    background: white;
}
.aside{
    background: #f2f2f2;

}
.main{
    width: 400px;
}
#personmanage{
    min-width: 0px;
    .tree{
        // border-top: #aaa 1px dashed;
        padding-top:5px;
        width:1028px;
        margin:0 auto;
    }
}
.form-inline {
    padding-top: 16px;
    padding-left: 24px;
    height:78px;
    .removePadding {
        padding: 5px 10px;
        margin-left: 7px;
        }
    img {
        width: 1em;
        vertical-align: bottom;
        }
    .removePadding1 {
        padding: 5px 10px;
        margin-left: 63px;
        }
    .removePadding {
        padding: 5px 10px;
        margin-right: 237px;
        }

    }
// .removePadding7{
//     // display: inline-block;
//     // margin-left: 50px;        
// }
// .removePadding8{
//     margin-right: -50px;        
// }
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
    width:878px;
    margin-top: 0px;
    // margin:0 auto;
    color: #0f81d8;
    height: 45px;
    line-height: 43px;
    font-size:17px;
    text-align: center;
    // border-bottom: #aaa 1px dashed;
    padding-bottom: 3px;
}
// .el-input--mini .el-input__inner {
//     width: 100px;
// }

.add-edit-view-dialog1 {
    .el-dialog {
        min-width: 1200px;
        // border: #0f81d8 1px solid;
        .modotype{
            border: #b3c7ea 1px solid;
            background: #eff6fc;
            width:700px;
            margin: 0 auto;
            margin-bottom:10px;
            padding: 5px;
            .formOfDialog1 {
                // border: #0f81d8 1px solid;
                width: 800px;
                margin: auto;
            }
            .dialog-footer{
                margin-left:500px;
            }
        }


    .el-dialog__body {
        padding: 30px 0;
        // border: #0f81d8 1px solid;
        .el-input--medium .el-input__inner,
        .el-radio-group,
        .el-date-editor {
            width: 210px;
        }
        .modename .el-input__inner{
            width: 530px;
            // border: red 1px solid;
        }
        .modotypeshow{
            // border: red 1px solid;
            width: 130px;
            margin: 0 auto;
            // font-family:SimHei;
            font-weight: bold;
        }
        .UnknowModule{
            // border: red 1px solid;
            width: 650px;
            margin: 0 auto;
            font-size:14px;
            margin-bottom:10px;
            font-weight: bold;
        }
        .ico{
            background:#b3c7e8;
            color: white;
        }

    }
    }
}
</style>