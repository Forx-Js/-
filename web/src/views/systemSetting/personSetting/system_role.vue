<template>
    <div id="system_role">
        <el-container>
        <div class="aside" style="width:541px;">
            <div class="js">
            <div class="jsmc">角色名称</div>
            <el-form
                :inline="true"
                style="border-top: 1px dashed #aaa"
                :model="formInline"
                ref="formInline"
                size="mini"
                class="form-inline"
            >
                <el-form-item>
                <el-button
                    class="removePadding"
                    @click="editEvent"
                    
                >
                    <!-- <img src="../../../../static/image/icon_shuaxin.png" alt> -->
                    <span>修改</span>
                </el-button>
                <el-button
                    class="removePadding"
                    @click="deleteEvent"
                    
                >
                    <!-- <img src="../../../../static/image/icon_del.png" alt> -->
                    <span>删除</span>
                </el-button>
                <el-button
                    class="removePadding2"
                    @click="addCustomerEvent"
                    
                >
                    <!-- <img src="../../../../static/image/icon_add.png" alt> -->
                    <span>角色</span>
                </el-button>
                </el-form-item>
            </el-form>
            <el-main style=" height:400px;padding-top:0">
                <ul class="jsdata" style="border-top: 1px dashed #aaa;padding-top:10px">
                <li
                    v-for="(item,index) in list_Data"
                    :key="index"
                    @click="_add(item.roleID,index,item.roleName)"
                    :class="[index === cur1?'active':'']"
                >
                    <i>
                    <!-- <img src="../../layout/components/images/perpson.png" alt="图标"> -->
                    </i>
                    {{item.roleName}}
                </li>
                </ul>
            </el-main>
            </div>
            <div class="fp">
            <div class="jsmc">已分配人员</div>
            <el-form
                :inline="true"
                style="border-top: 1px dashed #aaa"
                :model="formInline"
                ref="formInline"
                size="mini"
                class="form-inline"
            >
                <el-form-item>
                <div class="input">
                    <input type="text" v-model="list_Data_roleName1" placeholder="输入用户名和登录名">
                    <p class="ss" @click="queryEvent3()">搜索</p>
                </div>
                <el-button
                    class="removePadding"
                    @click="deleteDicEvent"
                    
                >
                    <!-- <img src="../../../../static/image/icon_del.png" alt> -->
                    <span>删除</span>
                </el-button>
                <el-button
                    class="removePadding1"
                    @click="addCustomerEvent1"
                    
                >
                    <!-- <img src="../../../../static/image/icon_add.png" alt> -->
                    <span>分配人员</span>
                </el-button>
                </el-form-item>
            </el-form>
            <el-main style=" height:300px;padding-top:0">
                <ul class="jsdata dispose" style="border-top: 1px dashed #aaa;padding-top:10px">
                <li
                    v-for="(item,index) in person_Data"
                    :key="index"
                    @click="_add2(item.iD,index,item.roleName)"
                    :class="[index === cur2?'active':'']"
                >
                    <i>
                    <!-- <img src="../../layout/components/images/perpson.png" alt="图标"> -->
                    </i>
                    {{item.name}}
                </li>
                </ul>
                <div v-if="type1 == 4">
                <button @click="queryEvent2()" class="again">查无此人,请重置</button>
                </div>
            </el-main>
            </div>
        </div>
        </el-container>
        <!-- 下面是添加角色时弹出内容 -->
        <el-dialog
        :before-close="handleClose"
        :title="dialogTitle"
        :visible.sync="handlerCustomDialogVisible"
        class="add-edit-view-dialog"
        >
        <el-form
            :model="list_Data"
            ref="list_Data"
            :inline="true"
            :disabled="disabled"
            class="formOfDialog"
        >
            <el-form-item label="角色名称" label-width="100px" prop="roleName" v-if="type1 == 1">
            <el-input v-model="list_Data.roleName"></el-input>
            </el-form-item>
            <div class="main-table-minHeight" v-if="type1 == 3">
            <el-form
                :inline="true"
                :model="formInline"
                ref="formInline"
                size="mini"
                class="form-inline"
            >
                <el-form-item class="item-button">
                <div class="input1">
                    <input
                    type="text"
                    v-model="formInline.customerName"
                    placeholder="输入登记名称、员工工号或者员工名称以搜索"
                    >
                    <p class="ss1" @click="queryEvent4()">搜索</p>
                </div>
                <el-button
                    @click="resetEvent('formInline')"
                    class="orangeBg"
                    
                >重置</el-button>
                </el-form-item>
            </el-form>
            <el-table
                @cell-click="openDetails"
                border
                :data="customer_Mess_Data"
                class="sydneyTable"
                :stripe="true"
                tooltip-effect="dark"
                style="width: 1400px"
                size="mini"
                header-cell-class-name="nihao"
                @select-all="handleSelectionChange"
                @select="handleSelectionChange"
                @selection-change="handleSelectionChange"
            >
                <el-table-column
                prop="status"
                label="选择"
                width="120"
                align="center"
                :selectable="selectable"
                >
                <template slot-scope="scope">
                    <el-checkbox
                    v-model="scope.row.change"
                    v-bind="{'checked': scope.row.change? true : false}"
                    @change="editChange(scope)"
                    ></el-checkbox>
                </template>
                </el-table-column>
                <el-table-column label="员工工号" prop="staffID" width="100" align="center"/>
                <el-table-column prop="userName" class-name="view_td" label="登录名称"></el-table-column>
                <el-table-column prop="name" label="员工名称" width="150"></el-table-column>
                <el-table-column prop="address" label="所在区域"></el-table-column>
                <el-table-column
                prop="status"
                label="是否在职"
                width="120"
                align="center"
                :selectable="selectable"
                >
                <template slot-scope="scope">
                    <el-checkbox
                    v-model="scope.row.status"
                    v-bind="{'checked': scope.row.status? true : false}"
                    >
                    <!-- @change="v => {update(scope.row, v, 'status')}" -->
                    </el-checkbox>
                </template>
                </el-table-column>
            </el-table>
            <el-pagination
                style="text-align:center;width:1366px;"
                :current-page="customer_Mess_Page_Index"
                @current-change="bidMessPageChange"
                :page-size="customer_Mess_Page_Size"
                layout="total, prev, pager, next"
                :total="customer_Mess_Page_Total"
            ></el-pagination>
            </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelEvent">取 消</el-button>
            <el-button v-if="type1 == 1" type="primary" @click="addNewCustomerEvent('list_Data')">确 定</el-button>
            <el-button
            v-else-if="type1 == 2"
            type="primary"
            @click="removeNewCustomerEvent('list_Data')"
            >确 定</el-button>
            <el-button v-if="type1 == 3" type="primary" @click="addNewCustomerEvent1('list_Data')">确 定</el-button>
        </div>
        </el-dialog>
    </div>
</template>
<script>
// import userService from "@/service/user-service";
export default {
    data() {
        return {
        //查询条件
        formInline: {
            customerName: "",
            capitalCreditID: "",
            customerAddr: "",
            // list:[],
            jsdata: []
        },
        cur1: 0,
        cur2: 0,
        type1: 1,
        peopledata: [],
        peopleID: "",
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
        dialogTitle: "添加客户信息", //增改查客户信息的dialog的标题
        disabled: false, //增改查客户信息的dialog内的form表单及关联数据的可操作与否
        customerForm: {}, //增改查客户信息的dialog内的form表单

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

        //系统角色信息   ni
        list_Data: {},
        keepid: "admin",
        keepindex: 0,

        //人员信息
        person_Data: {},
        list_Data_roleName: "管理员",
        list_Data_roleName1: "",

        changedata: [],
        alldata: [],
        arr4: []
        };
    },
    created() {
        // this.queryEvent();
    },
    methods: {
        openDetails(row, column, cell, event) {
        if (cell.cellIndex == 1) {
            this.viewEvent("", row);
        }
        },
        // 记录id  ni
        _add(roleID, index, roleName) {
        this.keepid = roleID;
        this.keepindex = index;
        this.list_Data_roleName = roleName;
        this.cur1 = index;
        this.cur2 = 0;
        this.queryEvent2(); //点击某个角色 查询对应的人员
        this.$emit("aa", this.keepid);
        },
        _add2(iD, index, roleName) {
        this.keepid1 = iD;
        this.keepindex = index;
        this.cur2 = index;
        },
        change(index) {
        this.formInline.cur = index;
        },
        //查询角色   ni
        queryEvent() {
        // var _this = this;
        // _this.axios
        //     .get(_this.testIP + "/role/list", {
        //     params: {
        //         type: 1
        //     }
        //     })
        //     .then(function(res) {
        //     if (res.data.result == "0") {
        //         _this.list_Data = res.data.data;
        //         _this.list_Data_roleName = res.data.data[0].roleName;
        //         _this.keepid = res.data.data[0].roleID;
        //         // 初始化时或者刷新时   第一个角色对应的人员
        //         _this.queryEvent2();
        //     } else {
        //         _this.$message({
        //         duration: 1000,
        //         message: "数据获取失败00",
        //         type: "warning"
        //         });
        //     }
        //     })
        //     .catch(function(error) {});
        },
        //查询人员  ni
        queryEvent2() {
        // var _this = this;

        // _this.axios
        //     .get(_this.testIP + "/role/users", {
        //     params: {
        //         roleID: _this.keepid
        //     }
        //     })
        //     .then(function(res) {
        //     if (res.data.result == "0") {
        //         _this.person_Data = res.data.data;
        //         _this.type1 = 1;
        //         _this.list_Data_roleName1 = "";
        //     } else {
        //         _this.$message({
        //         duration: 1000,
        //         message: "数据获取失败",
        //         type: "warning"
        //         });
        //     }
        //     })
        //     .catch(function(error) {});
        },
        //搜索人员  ni
        queryEvent3() {
        // var _this = this;
        // _this.axios
        //     .get(_this.testIP + "/role/users", {
        //     params: {
        //         roleID: this.keepid,
        //         keyWord: _this.list_Data_roleName1
        //     }
        //     })
        //     .then(function(res) {
        //     if (res.data.result == "0") {
        //         _this.person_Data = res.data.data;
        //         if (_this.person_Data == "") {
        //         console.log("没人");
        //         _this.type1 = 4;
        //         }
        //     } else {
        //         _this.$message({
        //         duration: 1000,
        //         message: "数据获取失败",
        //         type: "warning"
        //         });
        //     }
        //     })
        //     .catch(function(error) {});
        },
        queryEvent4() {
        // var _this = this;
        // userService
        //     .getUserList({
        //     pageNo: _this.customer_Mess_Page_Index,
        //     pageSize: _this.customer_Mess_Page_Size,
        //     keyWord: _this.formInline.customerName,
        //     status: _this.formInline.capitalCreditID
        //     })
        //     .then(res => {
        //     _this.customer_Mess_Data = res.data;
        //     var arr1 = JSON.stringify(_this.person_Data);
        //     var arr2 = JSON.parse(arr1);
        //     _this.arr4 = [];
        //     for (var r = 0; r < res.data.length; r++) {
        //         for (var e = 0; e < arr2.length; e++) {
        //         if (res.data[r].userID == arr2[e].userID) {
        //             _this.arr4.push(res.data[r]);
        //         } else {
        //             // console.log("没有相等的")
        //         }
        //         }
        //         if (res.data[r].show == undefined) {
        //         _this.$set(res.data[r], "show", false);
        //         }
        //     }
        //     _this.customer_Mess_Page_Total = res.totalCount;
        //     _this.customer_Mess_Page_Index = res.pageIndex;
        //     // for循环 用来表明复选框
        //     for (var i = 0; i < _this.customer_Mess_Data.length; i++) {
        //         if (_this.customer_Mess_Data[i].status == 0) {
        //         _this.customer_Mess_Data[i].status = false;
        //         } else {
        //         _this.customer_Mess_Data[i].status = true;
        //         }

        //         if (_this.customer_Mess_Data[i].change == undefined) {
        //         _this.$set(_this.customer_Mess_Data[i], "change", false);
        //         }
        //     }

        //     var arr3 = [];
        //     for (var j = 0; j < _this.customer_Mess_Data.length; j++) {
        //         for (var l = 0; l < _this.changedata.length; l++) {
        //         if (
        //             _this.customer_Mess_Data[j].staffID ==
        //             _this.changedata[l].staffID
        //         ) {
        //             arr3.push(_this.customer_Mess_Data[j]);
        //         } else {
        //             _this.customer_Mess_Data[j].change = false;
        //         }
        //         }
        //     }
        //     arr3 = arr3.concat(_this.arr4);
        //     for (var o = 0; o < arr3.length; o++) {
        //         arr3[o].change = true;
        //     }
        //     // console.log(arr3,"arr3")
        //     })
        //     .catch(error => {
        //     _this.$message({
        //         duration: 1000,
        //         message: error || "数据获取失败",
        //         type: "warning"
        //     });
        //     });
        },
        // 复选框
        editChange(scope) {
        // console.log(scope.row.change)
        // if (scope.row.change == true) {
        //     scope.row.change = true;
        //     this.changedata.push(scope.row);
        //     for (var i = this.changedata.length - 1; i >= 0; i--) {
        //     if (
        //         this.changedata.indexOf(this.changedata[i]) !==
        //         this.changedata.lastIndexOf(this.changedata[i])
        //     ) {
        //         this.changedata.splice(i, 1);
        //     }
        //     }
        // } else {
        //     var id = "";
        //     for (var i = 0; i < this.person_Data.length; i++) {
        //     if (this.person_Data[i].userID == scope.row.userID) {
        //         id = this.person_Data[i].iD;
        //     }
        //     }
        //     this.keepid2 = id;
        //     this.deleteDicEvent1();
        //     let index1 = scope.row.userID;
        //     this.removeByValue(this.changedata, "userID", index1);
        // }
        },
        // 复选框不勾选时需要调用的函数
        removeByValue(arr, attr, value) {
        //数组，属性，属性值
        var index = 0;
        for (var i in arr) {
            if (arr[i][attr] == value) {
            index = i;
            break;
            }
        }
        arr.splice(index, 1);
        },
        //重置查询条件、重新查询数据
        resetEvent(formName) {
        this.customer_Mess_Page_Index = 1;
        this.$refs[formName].resetFields();
        this.formInline = {};
        this.queryEvent4();
        },
        //切换页码
        bidMessPageChange(val) {
        this.customer_Mess_Page_Index = val;
        this.queryEvent4();
        },
        //主界面--行--编辑   ni
        // 查看修改
        editEvent(index, row) {
        this.disabled = false;
        this.handlerCustomDialogVisible = true;
        this.dialogTitle = "修改系统角色";
        this.add_edit = "change";
        this.type1 = 1;
        var _this = this;
        setTimeout(function() {
            try {
            _this.$refs["list_Data"].clearValidate();
            } catch (e) {}
        });
        },
        //主界面--行--删除
        // 删除角色信息    ni
        deleteEvent(index, row) {
        this.$confirm("确定删除此角色?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        })
            .then(() => {
            var _this = this;

            _this.$http
                .post(_this.testIP + "/role/delete", {
                type: "1",
                roleID: this.keepid
                })
                .then(function(res) {
                if (res.data.result == "0") {
                    _this.$message({
                    duration: 1000,
                    message: "删除成功",
                    type: "success"
                    });
                    _this.queryEvent();
                    _this.cur1 = 0;
                } else {
                    _this.$message({
                    duration: 1000,
                    message: "删除失败",
                    type: "warning"
                    });
                }
                })
                .catch(function(error) {});
            })
            .catch(() => {});
        },
        // 删除人员
        deleteDicEvent(index, row) {
        this.$confirm("确定删除此人员?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        })
            .then(() => {
            var _this = this;
            _this.$http
                .post(_this.testIP + "/role/delete-user", {
                iD: this.keepid1
                })
                .then(function(res) {
                if (res.data.result == "0") {
                    _this.$message({
                    duration: 1000,
                    message: "删除成功",
                    type: "success"
                    });
                    _this.queryEvent2();
                } else {
                    _this.$message({
                    duration: 1000,
                    message: "删除失败",
                    type: "warning"
                    });
                }
                })
                .catch(function(error) {});
            })
            .catch(() => {});
        },
        // 在添加角色里去掉已经添加过的人员的勾选框(删除人员)
        deleteDicEvent1(index, row) {
        var _this = this;
        _this.$http
            .post(_this.testIP + "/role/delete-user", {
            iD: this.keepid2
            })
            .then(function(res) {
            if (res.data.result == "0") {
                _this.queryEvent2();
            }
            })
            .catch(function(error) {});
        },
        // 打开添加角色信息   ni
        addCustomerEvent() {
        this.disabled = false;
        this.handlerCustomDialogVisible = true;
        this.dialogTitle = "新增系统角色";
        this.add_edit = "add";
        this.type1 = 1;
        var _this = this;
        setTimeout(function() {
            try {
            _this.$refs["list_Data"].clearValidate();
            } catch (e) {}
        });
        },
        // 打开添加分配人员   ni
        addCustomerEvent1() {
        this.disabled = false;
        this.handlerCustomDialogVisible = true;
        this.changedata = [];
        this.dialogTitle = "添加人员";
        this.peopleName = "";
        this.customer_Mess_Page_Index = 1;
        this.formInline = {};
        this.queryEvent4();
        // this.person_Data();
        this.type1 = 3;
        var _this = this;
        setTimeout(function() {
            try {
            _this.$refs["list_Data"].clearValidate();
            } catch (e) {}
        });
        },
        //添加角色的确定按钮   ni
        addNewCustomerEvent(formName) {
        var _this = this;
        _this.$refs[formName].validate(valid => {
            if (valid) {
            if (this.add_edit == "add") {
                _this.axios
                .post(_this.testIP + "/role/add", {
                    type: "1",
                    roleName: _this.list_Data.roleName
                })
                .then(function(res) {
                    if (res.data.result == "0") {
                    _this.$message({
                        duration: 1000,
                        message: "添加角色成功",
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
                .catch(function(error) {});
            } else {
                delete _this.list_Data.operTime;
                //编辑角色的确定
                _this.axios
                .post(_this.testIP + "/role/update", {
                    type: "1",
                    roleID: _this.keepid,
                    roleName: _this.list_Data.roleName
                })
                .then(function(res) {
                    if (res.data.result == "0") {
                    _this.$message({
                        duration: 1000,
                        message: "修改角色信息成功",
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
                .catch(function(error) {});
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
        // 分配人员的确定按钮
        addNewCustomerEvent1(formName) {
        var _this = this;
        _this.peopleName = "";
        for (var i = 0; i < _this.person_Data.length; i++) {
            for (var l = 0; l < _this.changedata.length; l++) {
            if (_this.person_Data[i].userID == _this.changedata[l].userID) {
                _this.changedata.splice(l, 1);
            }
            }
        }
        for (var o = 0; o < _this.changedata.length; o++) {
            _this.peopleName += _this.changedata[o].userID + ",";
        }

        _this.$refs[formName].validate(valid => {
            if (valid) {
            _this.axios
                .post(_this.testIP + "/role/add-user", {
                roleID: _this.keepid,
                keyWord: _this.peopleName
                })
                .then(function(res) {
                if (res.data.result == "0") {
                    _this.$message({
                    duration: 1000,
                    message: "添加人员成功",
                    type: "success"
                    });
                    _this.handlerCustomDialogVisible = false;
                    _this.queryEvent2();
                } else {
                    _this.$message({
                    duration: 1000,
                    message: res.data.message,
                    type: "warning"
                    });
                }
                })
                .catch(function(error) {});
            }
        });
        },
        removeNewCustomerEvent(formName) {
        var _this = this;
        _this.$refs[formName].validate(valid => {
            if (valid) {
            if (this.add_edit == "remove") {
                //删除角色的确定
                _this.axios
                .post(_this.testIP + "/role/delete", {
                    roleID: this.keepid
                })
                .then(function(res) {
                    if (res.data.result == "0") {
                    _this.$message({
                        duration: 1000,
                        message: "删除角色成功",
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
                .catch(function(error) {});
            } else {
                delete this.customerForm.operTime;
                //编辑客户
                _this.axios
                .post(
                    _this.testIP + "/customer/update-cusMsg",
                    _this.customerForm
                )
                .then(function(res) {
                    if (res.data.result == "0") {
                    _this.$message({
                        duration: 1000,
                        message: "编辑角色信息成功",
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
                .catch(function(error) {});
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
        handleClose(done) {
        if (this.dialogTitle != "查看客户信息") {
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
        }
    }
};
</script>
<style lang="scss" scoped>
.input {
    width: 225px;
    display: inline-block;
    position: relative;
    input {
        width: 225px;
        height: 28px;
        border-radius: 3px;
        border: 1px solid #e1e1e1;
        outline: none;
        font-size: 13px;
        padding-left: 10px;
    }
    .ss {
        position: absolute;
        height: 28px;
        top: 2px;
        left: 161px;
        width: 64px;
        text-align: center;
        display: inline-block;
        background: #55a7f0;
        color: white;
        border-radius: 5%;
        cursor: pointer;
    }
}
.input1 {
    width: 225px;
    display: inline-block;
    position: relative;
    padding-bottom: 10px;
    input {
        width: 358px;
        height: 28px;
        border-radius: 3px;
        border: 1px solid #e1e1e1;
        outline: none;
        font-size: 13px;
        padding-left: 15px;
    }
    .ss1 {
        position: absolute;
        height: 28px;
        top: 1px;
        left: 294px;
        width: 64px;
        text-align: center;
        display: inline-block;
        background: #55a7f0;
        color: white;
        border-radius: 5%;
        cursor: pointer;
    }
}
.orangeBg {
    margin-left: 153px;
}
.el-button--mini,
.el-button--mini.is-round {
    padding: 5px 15px;
}
.header {
    background: #b6c4c8;
    padding: 0;
    padding-top: 17px;
    ul {
        display: flex;
        li {
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
.jsdata img {
    margin-right: 5px;
}
.jsdata li {
    font-size: 17px;
    height: 28px;
    line-height: 25px;
    cursor: pointer;
    color: black;
}
.jsdata .active {
    background-color: #f0f7ff;
}
.js {
    width: 520px;
    height: 405px;
    margin: 0 auto;
    background: white;
}
.jsdata {
    border-top: 1px dashed #aaa;
    margin: 0 auto;
}
.jsdata li {
    list-style: none;
}
.fp {
    width: 520px;
    margin: 0 auto;
    margin-top: 10px;
    background: white;
}
.aside {
    background: #f2f2f2;
    height: 100%;
}
.main {
    width: 400px;
}
#personmanage {
    min-width: 0px;
}
.form-inline {
    padding-top: 16px;
    padding-left: 24px;
    height: 60px;
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
    .removePadding2 {
        padding: 5px 10px;
        margin-left: 237px;
    }
}
.sydneyTable {
    margin-left: 0px;
    margin-top: 0px;
}
.jsmc {
    height: 43px;
    text-align: center;
    line-height: 43px;
    color: #0f81d8;
    font-size: 17px;
}
.csh {
    margin-top: 14px;
    margin-left: 900px;
}
.Breadcrumb1 {
    width: 81px;
    margin-top: 0px;
    margin: 0 auto;
    color: #0f81d8;
    height: 19px;
    font-size: 16px;
}
.el-input--mini .el-input__inner {
    width: 100px;
}
.again {
    padding: 5px;
    background: #d69e07;
    border: 1px solid white;
    color: white;
    margin-left: 10px;
}
</style>