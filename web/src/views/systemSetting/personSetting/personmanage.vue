<template>
    <div id="personmanage">
        <el-breadcrumb class="Breadcrumb" separator="/">
        <el-breadcrumb-item class="firstLevel">人员管理</el-breadcrumb-item>
        </el-breadcrumb>
        <br class="clear">
        <p class="messages-show">人员信息</p>
        <el-form :inline="true" :model="formInline" ref="formInline" size="mini" class="form-inline">
        <el-form-item>
            <el-button class="removePadding" @click="addPersonEvent" >
            <!-- <img src="../../../../static/image/icon_add.png" alt> -->
            <span>添加</span>
            </el-button>
        </el-form-item>
        <el-form-item class="customerAddr" label="搜索条件：" prop="customerAddr">
            <el-input style="width: 300px" v-model="formInline.customerName" placeholder="输入登记名称、员工工号或者员工名称以搜索"></el-input>
        </el-form-item>
        <!-- 人员范围 -->
        <el-form-item label="人员范围：" prop="capitalCreditID">
            <el-select filterable v-model="formInline.capitalCreditID" placeholder="请选择">
            <el-option v-for="item in zjxyOptions" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item class="item-button">
            <el-button type="primary" @click="queryEvent" >查询</el-button>
            <el-button @click="resetEvent('formInline')" class="orangeBg" >重置</el-button>
        </el-form-item>
        </el-form>
        <!-- 以下是头部 -->
        <div class="main-table-minHeight">
        <el-table @cell-click="openDetails" border :data="customer_Mess_Data" class="sydneyTable" :stripe="true"
            tooltip-effect="dark" style="width: 1400px" size="mini" header-cell-class-name="nihao" @selection-change="handleSelectionChange">
            <el-table-column label="员工工号" prop="staffID" width="100" align="center" />
            <el-table-column prop="userName" class-name="view_td" label="登录名称"></el-table-column>
            <el-table-column prop="name" label="员工名称" width="150"></el-table-column>
            <el-table-column prop="sex" label="员工性别" width="90" align="center"></el-table-column>
            <el-table-column prop="address" label="所在区域"></el-table-column>
            <!-- <el-table-column prop="operationModeName" label="电子邮箱" width="90" align="center"></el-table-column> -->
            <el-table-column prop="telNum" label="座机号码" width="90" align="center"></el-table-column>
            <el-table-column prop="phoneNum" label="手机号码" align="center"></el-table-column>
            <el-table-column prop="status" label="是否在职" width="120" align="center" :selectable="selectable">
            <template slot-scope="scope">
                <el-checkbox v-model="scope.row.status" v-bind="{'checked': scope.row.status? true : false}"></el-checkbox>
            </template>
            </el-table-column>
            <el-table-column prop="type" label="是否外部" width="120" align="center" :selectable="selectable">
            <template slot-scope="scope">
                <el-checkbox v-model="scope.row.type" v-bind="{'checked': scope.row.type? true : false}"></el-checkbox>
            </template>
            </el-table-column>
            <el-table-column prop="customerAddr" label="备注" align="center"></el-table-column>
            <el-table-column label="操作" width="120" align="center">
            <template slot-scope="scope">
                <el-button @click.native.prevent="editEvent(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
                <el-button @click.native.prevent="deleteEvent(scope.$index, scope.row)" type="text" size="small">删除</el-button>
            </template>
            </el-table-column>
        </el-table>
        </div>
        <!-- 下面是添加角色时弹出内容 -->
        <el-dialog :before-close="handleClose" :title="dialogTitle" :visible.sync="handlerCustomDialogVisible" class="add-edit-view-dialog">
        <el-form :model="personForm" :rules="rules" ref="personForm" :inline="true" :disabled="disabled" class="formOfDialog">
            <el-form-item label="登录名称" label-width="100px" prop="userName">
            <el-input v-model="personForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="员工名称" label-width="100px" prop="name">
            <el-input v-model="personForm.name"></el-input>
            </el-form-item>
            <el-form-item label="所在区域" label-width="100px" prop="address">
            <el-select filterable v-model="formInline.address" placeholder="请选择">
                <el-option v-for="item in zjxyOptionss" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="员工性别：" label-width="100px" class="type">
            <el-radio v-model="personForm.sex" label="1">男</el-radio>
            <el-radio v-model="personForm.sex" label="2">女</el-radio>
            </el-form-item>
            <el-form-item label="手机号码" label-width="100px" prop="roleName">
            <el-input v-model="personForm.phoneNum"></el-input>
            </el-form-item>
            <el-form-item label="座机号码" label-width="100px" prop="roleName">
            <el-input v-model="personForm.telNum"></el-input>
            </el-form-item>
            <el-form-item label="电子邮箱" label-width="100px" prop="email">
            <el-input v-model="personForm.email"></el-input>
            </el-form-item>
            <el-form-item label="员工工号" label-width="100px" prop="staffID">
            <el-input v-model="personForm.staffID"></el-input>
            </el-form-item>
            <el-form-item label="是否在职" label-width="100px">
            <el-select filterable v-model="personForm.status" placeholder="请选择">
                <el-option v-for="item in getStatus" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="是否外部" label-width="100px" prop="type">
            <el-select filterable v-model="formInline.type" placeholder="请选择">
                <el-option v-for="item in gettype" :key="item.id" :label="item.type" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="用户类型" label-width="100px" prop="types">
            <el-select filterable v-model="formInline.types" placeholder="请选择">
                <el-option v-for="item in zjxyOptionss" :key="item.id" :label="item.cnName" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="所属部门" label-width="100px">
            <el-input v-model="personForm.capitalCreditIDd" placeholder="请输入内容" type="textarea" style="width:535px"></el-input>
            </el-form-item>
            <el-form-item label="备注信息" label-width="100px" prop="note">
            <el-input v-model="personForm.note" placeholder="请输入内容" type="textarea" style="width:535px"></el-input>
            </el-form-item>
        </el-form>
        <div class="uploadremark">
            <span>备注信息</span>
            <div class="uploadform">
            <label style="display:inline-block">
                <input type="file" id="SelecyFile_Liu" style="display:none" @change="uploadZCFile">
                <el-button size="mini" class="ico" @click="selectFile">上传文件</el-button>
            </label>
            <label style="display:inline-block">
                <el-button size="mini" class="ico" @click="setFile">上传设置</el-button>
            </label>
            </div>
            <div class="main-table-minHeight1">
            <el-table @cell-click="openDetails" border :data="uploadData" class="sydneyTable" :stripe="true"
                tooltip-effect="dark" style="width: 1000px" size="mini" header-cell-class-name="nihao" @selection-change="handleSelectionChange">
                <el-table-column label="文件名称" prop="realName" width="150" align="center"></el-table-column>
                <el-table-column prop="fileSize" class-name="view_td" width="80" align="center" label="文件大小"></el-table-column>
                <!-- <el-table-column prop="status" class-name="view_td"  width="70" label="状态" align="center"></el-table-column> -->
                <el-table-column prop="operUserName" label="上传人" width="140" align="center"></el-table-column>
                <el-table-column prop="createTime" label="上传时间" width="150" align="center"></el-table-column>
                <el-table-column label="操作" width="120" align="center">
                <template slot-scope="scope">
                    <el-button @click.native.prevent="downloadZCEvent(scope.$index, scope.row)" type="text" size="small">下载</el-button>
                    <el-button @click.native.prevent="downloadZCEvent1(scope.$index, scope.row)" type="text" size="small">查看附件</el-button>
                </template>
                </el-table-column>
            </el-table>
            </div>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelEvent">取 消</el-button>
            <el-button type="primary" @click="addNewPersonEvent('personForm')">确 定</el-button>
        </div>
        </el-dialog>
        <!-- 添加的文件信息的弹出 -->
        <el-dialog :before-close="handleClose1" :title="dialogTitle1" :visible.sync="handlerCustomDialogVisible1" class="add-edit-view-dialog1">
        <el-form :model="personForm" :rules="rules" ref="personForm" :inline="true" :disabled="disabled" class="formOfDialog">
            <el-form-item label="限制大小" label-width="100px" prop="size" v-if="type1 == 2">
            <el-input v-model="size"></el-input>
            </el-form-item>
            <p v-if="type1 == 2">M</p>
            <div class="set-footer" v-if="type1 == 2">
            <el-button @click="cancelEvent1">取 消</el-button>
            <el-button @click="addNewPersonEvent1()">确 定</el-button>
            </div>
        </el-form>
        </el-dialog>
        <!-- 附件的展示 -->
        <img :src="objectUrl" alt class="photo" @click="clice" v-if="type==2">
        <!--以下是分页 -->
        <el-pagination style="text-align:center;width:1366px;" :current-page="customer_Mess_Page_Index" @current-change="bidMessPageChange"
        :page-size="customer_Mess_Page_Size" layout="total, prev, pager, next" :total="customer_Mess_Page_Total"></el-pagination>
    </div>
</template>
<script>
    // import permissionService from "@/service/permission-service";
    // import userService from "@/service/user-service";
    import axios from "axios";
    export default {
    data() {
        return {
        //查询条件
        formInline: {
            customerName: "",
            capitalCreditID: "",
            customerAddr: ""
            // keyword: ""
        },
        type1: 2,
        zjxyOptions: [], //资金信用select
        khlbOptions: [], //客户类别select
        jyfsOptions: [], //经营方式select

        // 分页
        customer_Mess_Data: [],
        customer_Mess_Page_Total: 0, //客户信息table数据总数
        customer_Mess_Page_Size: 10, //客户信息table每页显示个数
        customer_Mess_Page_Index: 1, //客户信息table当前页数
        customer_Mess_Page_status: 0,

        handlerCustomDialogVisible: false, //增改查客户信息的dialog的显隐
        handlerCustomDialogVisible1: false,
        handlerCustomDialogVisible2: false,
        dialogTitle: "添加客户信息", //增改查客户信息的dialog的标题
        dialogTitle1: "上传设置",
        disabled: false, //增改查客户信息的dialog内的form表单及关联数据的可操作与否
        // customerForm: {}, //增改查客户信息的dialog内的form表单
        personForm: {},
        customerIDRec: "", //记录当前行的唯一id

        //关联的联系人信息
        linkMan_Data: [],
        linkMan_Page_Total: 0,
        linkMan_Page_Size: 10,
        linkMan_Page_Index: 1,

        //关联的项目信息
        project_Data: [],
        project_Page_Total: 0,
        project_Page_Size: 10,
        project_Page_Index: 1,
        res: "",
        rules: {
            userName: [{
            required: true,
            message: "登录名称不能为空",
            trigger: "blur"
            }],
            name: [{
            required: true,
            message: "员工名称不能为空",
            trigger: "blur"
            }],
            staffID: [{
            required: true,
            message: "员工工号不能为空",
            trigger: "blur"
            }]
            // types:[
            //   { required: true, message: "请选择用户类型", trigger: "change" }
            // ],
            // note:[
            //   { required: true, message: "备注信息不能为空", trigger: "blur" }
            // ],
        },
        uploadData: [],
        type: 1,
        size: "50", //52428800B
        objectUrl: "",
        hearurl: "",
        thisFile: ""
        };
    },
    computed: {},
    created() {
        // this.queryEvent();
        // this.getZJXYSel();
        // this.getStatus();
        // this.gettype();
    },
    methods: {
        cannotAccess(p) {
            return !permissionService.hasPermission(p);
        },
        openDetails(row, column, cell, event) {
            if (cell.cellIndex == 1) {
                this.viewEvent("", row);
            }
        },
        //查询
        queryEvent() {
            var _this = this;
            console.log(_this.formInline.customerName);
            userService
                .getUserList({
                pageNo: _this.customer_Mess_Page_Index,
                pageSize: _this.customer_Mess_Page_Size,
                keyWord: _this.formInline.customerName,
                status: _this.formInline.capitalCreditID
                })
                .then(res => {
                _this.customer_Mess_Data = res.data;
                _this.personForm = res.data;
                //
                // console.log(_this.personForm,"data")
                _this.customer_Mess_Page_Total = res.totalCount;
                _this.customer_Mess_Page_Index = res.pageIndex;
                // for循环 用来表明复选框
                for (var i = 0; i < _this.customer_Mess_Data.length; i++) {
                    if (_this.customer_Mess_Data[i].status == 0) {
                    _this.customer_Mess_Data[i].status = false;
                    } else {
                    _this.customer_Mess_Data[i].status = true;
                    }
                }
                })
                .catch(error => {
                _this.$message({
                    duration: 1000,
                    message: error || "数据获取失败",
                    type: "warning"
                });
            });
        },
        //重置查询条件、重新查询数据
        resetEvent(formName) {
            this.$refs[formName].resetFields();
            this.formInline = {};
            this.queryEvent();
        },
        //切换页码
        bidMessPageChange(val) {
            this.customer_Mess_Page_Index = val;
            this.queryEvent();
        },
        //主界面--行--编辑
        editEvent(index, row) {
            this.uploadData = [];
            this.getDetails(row.userID);
        },
        getDetails(rowId) {
            // console.log("编辑了")
            // console.log(this.uploadData,"编辑了")
            var _this = this;
            _this.axios
                .get(_this.testIP + "/user/detail?userID=" + rowId)
                .then(function (res) {
                if (res.data.result == "0") {
                    try {
                    _this.$refs["personForm"].resetFields();
                    } catch (e) {}
                    _this.personForm = res.data.data;
                    if (res.data.data.headURL != "") {
                    _this.uploadData.push(res.data.data);
                    }
                    _this.dialogTitle = "编辑人员信息";
                    _this.handlerCustomDialogVisible = true;
                    _this.add_edit = "edit";
                    console.log(res.data.data, "编辑");
                } else {
                    _this.$message({
                    duration: 1000,
                    message: res.data.message,
                    type: "warning"
                    });
                }
                })
                .catch(function (error) {});
        },
        //主界面--行--删除
        deleteEvent(index, row) {
            this.$confirm("确定删除此人员?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
                })
                .then(() => {
                var _this = this;
                // console.log(row.userID,"删除")
                _this.$http
                    .post(_this.testIP + "/user/delete", {
                    userID: row.userID
                    })
                    .then(function (res) {
                    if (res.data.result == "0") {
                        _this.$message({
                        duration: 1000,
                        message: "删除成功",
                        type: "success"
                        });
                        _this.queryEvent();
                    } else {
                        _this.$message({
                        duration: 1000,
                        message: "删除失败",
                        type: "warning"
                        });
                    }
                    })
                    .catch(function (error) {});
                })
                .catch(() => {});
        },
        //打开--添加人员信息--dialog
        addPersonEvent() {
            this.disabled = false;
            this.personForm = {};
            this.handlerCustomDialogVisible = true;
            this.dialogTitle = "新增人员";
            this.add_edit = "add";
            this.uploadData = [];
            var _this = this;
            setTimeout(function () {
                try {
                _this.$refs["personForm"].clearValidate();
                } catch (e) {}
            });
        },
        //添加/编辑人员，保存按钮
        addNewPersonEvent(formName) {
            var _this = this;
            _this.personForm.headURL = _this.hearurl;
            _this.personForm.size = _this.thisFile;
            _this.$refs[formName].validate(valid => {
                if (valid) {
                if (this.add_edit == "add") {
                    //添加客户
                    // console.log(_this.personForm,"添加")
                    _this.axios
                    .post(_this.testIP + "/user/add", _this.personForm)
                    .then(function (res) {
                        if (res.data.result == "0") {
                        _this.$message({
                            duration: 1000,
                            message: "添加人员成功",
                            type: "success"
                        });
                        _this.handlerCustomDialogVisible = false;
                        _this.queryEvent();
                        } else {
                        _this.$message({
                            duration: 1000,
                            message: res.data.message,
                            type: "warning"
                        });
                        }
                    })
                    .catch(function (error) {});
                } else {
                    //编辑客户
                    // console.log(_this.personForm,"修改")
                    _this.axios
                    .post(_this.testIP + "/user/update", _this.personForm)
                    .then(function (res) {
                        if (res.data.result == "0") {
                        _this.$message({
                            duration: 1000,
                            message: "编辑人员信息成功",
                            type: "success"
                        });
                        _this.handlerCustomDialogVisible = false;
                        _this.queryEvent();
                        } else {
                        _this.$message({
                            duration: 1000,
                            message: res.data.message,
                            type: "warning"
                        });
                        }
                    })
                    .catch(function (error) {});
                }
                } else {
                _this.$message({
                    duration: 1000,
                    message: "校验未通过，请检查！",
                    type: "warning"
                });
                return false;
                }
            });
        },
        // 人员范围
        getZJXYSel() {
            var _this = this;
            _this.zjxyOptions = [{
                cnName: " 离职人员",
                id: 0
                },
                {
                cnName: "在职人员",
                id: 1
                },
                {
                cnName: " 实习生",
                id: 3
                }
            ];
        },
        // 是否在职
        getStatus() {
            var _this = this;
            _this.getStatus = [{
                cnName: " 离职人员",
                id: 0
                },
                {
                cnName: "在职人员",
                id: 1
                },
                {
                cnName: " 实习生",
                id: 3
                }
            ];
        },
        // 是否外部
        gettype() {
            var _this = this;
            _this.gettype = [{
                type: "否",
                id: 0
                },
                {
                type: "是",
                id: 1
                }
            ];
        },
        // 字典
        customerTypeChange(item) {
            var _this = this;
            _this.khlbOptions.filter(function (itemx, index) {
                if (item == itemx.id) {
                _this.customerForm.customerTypeName = itemx.cnName;
                }
            });
        },
        handleClose(done) {
            if (this.dialogTitle != "查看人员信息") {
                this.$confirm("确认关闭？")
                .then(_ => {
                    (this.project_Page_Index = 1), //当前页数
                    (this.linkMan_Page_Index = 1), //当前页数
                    done();
                })
                .catch(_ => {});
            } else {
                done();
            }
        },
        cancelEvent() {
            (this.project_Page_Index = 1), //当前页数--关联的联系人信息
            (this.linkMan_Page_Index = 1), //当前页数-关联的项目信息
            (this.handlerCustomDialogVisible = false);
        },
        selectFile() {
            $("#SelecyFile_Liu").click();
        },
        //上传文件
        uploadZCFile(e) {
            var vm = this;
            var thisFile = e.target.files;
            var formData = new FormData();
            var newsize = vm.size * 1024 * 1024;
            if (e.target.files.lenght == 0) {
                return false;
            } else {
                // console.log(newsize,"大小")
                // console.log(thisFile["0"].size,"file")//Bytes
                if (thisFile["0"].size <= newsize) {
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
                    success: function (res) {
                    if (res.result == "0") {
                        setTimeout(_ => {
                        vm.uploadData.push(res.data);
                        vm.hearurl = res.data.fileKey;
                        vm.thisFile = thisFile["0"].size + "B";
                        console.log(res.data, "res.data");
                        });
                        vm.$message({
                        duration: 1000,
                        message: "上传成功",
                        type: "success"
                        });
                    } else {
                        vm.$message({
                        duration: 1000,
                        message: "上传失败",
                        type: "warning"
                        });
                        try {
                        $("#SelecyFile_Liu")[0].value = null;
                        } catch (error) {}
                    }
                    },
                    error: function () {
                    vm.$message({
                        duration: 1000,
                        message: "上传失败",
                        type: "warning"
                    });
                    try {
                        $("#SelecyFile_Liu")[0].value = null;
                    } catch (error) {}
                    }
                });
                } else {
                vm.$message({
                    message: "文件太大",
                    type: "warning"
                });
                }
            }
        },
        //下载文件
        downloadZCEvent(index, row) {
            this.$root.downLoadFile2(row);
        },
        // 查看附件
        downloadZCEvent1(index, row) {
            var _this = this;
            _this.axios
                .get(_this.testIP + "/upload/" + row.filePath, {
                responseType: "blob"
                })
                .catch(data => {
                const url = URL.createObjectURL(data);
                _this.objectUrl = url;
                });
            this.type = 2;
        },
        setFile() {
            this.handlerCustomDialogVisible1 = true;
        },
        addNewPersonEvent1() {
            this.handlerCustomDialogVisible1 = false;
        },
        cancelEvent1() {
            this.handlerCustomDialogVisible1 = false;
        },
        clice() {
            this.type = 1;
            this.objectUrl = "";
        }
    }
};
</script>
<style lang="scss" scoped>
  #personmanage {
    width: 98%;
    min-width: 1192px;
    margin-left: 10px;

    .form-inline {
      .removePadding {
        padding: 5px 15px;

        img {
          width: 1em;
          vertical-align: bottom;
        }
      }

      .customerAddr {
        width: 382px;
      }
    }
  }

  .sydneyTable {
    margin-left: 0px;
    margin-top: 0px;
  }

  .el-input--mini .el-input__inner {
    width: 300px;
  }

  .zz {
    height: 19px;
    width: 102px;
  }

  .el-checkbox__inner {
    margin-left: 45px;
  }

  .type {
    margin-left: 14px;
    width: 250px;
  }

  .uploadremark {
    width: 707px;
    margin-left: 30px;

    span {
      font-size: 14px;
    }

    .uploadform {
      display: inline-block;
      width: 547px;
      margin-left: 6px;
    }

    .main-table-minHeight1 {
      margin-left: 65px;
    }
  }

  .set-footer {
    margin-left: 254px;
  }

  .add-edit-view-dialog1 {
    margin-left: 400px;
    width: 50%;
  }

  .add-edit-view-dialog2 {
    img {
      margin-left: 200px;
    }
  }

  .formOfDialog {
    p {
      display: inline-table;
      margin-left: -12px;
      margin-top: 5px;
    }
  }

  .photo {
    z-index: 3000;
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: auto;
    margin: 100px auto;
  }

  // 以上是新写的
  #customer-mess {
    width: 98%;
    min-width: 1192px;
    height: 100%;
    margin-left: 10px;

    .right-top-addIcon {
      // border: 1px solid red;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      width: 170px;
      float: right;
      margin-top: 10px;
      margin-right: 20px;

      button.add-customer-mess {
        border: 0;
        padding: 10px;
      }
    }

    .clear {
      clear: both;
    }

    .form-inline {
      .el-form-item {
        margin: 10px 20px;
      }

      .item-button {
        float: right;
        margin-right: 20px;
      }
    }

    .add-edit-view-dialog {
      .el-dialog {
        min-width: 1100px;
        width: 30%;

        .formOfDialog {
          width: 1000px;
          margin: auto;
        }

        .el-dialog__body {
          padding: 30px 0;

          .el-input--medium .el-input__inner {
            width: 360px;
          }

          .lang-input {
            // width: 835px;
            width: 360px;
          }
        }
      }
    }
  }

</style>