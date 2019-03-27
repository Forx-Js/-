const db = {
  state: {
    tb_user: [],
    tb_role: [],
    tb_sys_app: [],
    tb_sys_form: [],
    tb_sys_form_field: [],
    tb_sys_form_man_hours: []
  },
  mutations: {
    INIT_DB: state => {
      initDB(state)
    },
    TOGGLE_SIDEBAR: state => { }
  },
  actions: {
    initDb({ commit }) {
      commit('INIT_DB')
    },
    toggleSideBar({
      commit
    }) {
      commit('TOGGLE_SIDEBAR')
    }
  }
}

const initDB = state => {
  state.tb_user = [

  ]

  // 以下都是测试数据
  state.tb_role = [
    {
      roleId: 'R001',
      roleName: '系统管理员'
    },
    {
      roleId: 'R002',
      roleName: '普通用户'
    }
  ]

  state.tb_sys_app = [{
    appId: '1001',
    appName: '请假申请',
    createTime: '2019-01-01',
    createUserName: '小明',
    formId: 'F001'
  }]

  state.tb_sys_form = [{
    formId: 'F001',
    formName: '请假表单'
  }]

  state.tb_sys_form_field = [{
    formId: 'F001',
    fieldId: 'F-F001-01',
    fieldName: '标题',
    sortNum: 1
  }]

  state.tb_sys_form_man_hours = [
    {
      projectName: '项目1',
      content: '测试',
      duration: '3小时',
      currentdate: "2019-03-19"
    },
    {
      projectName: '项目2',
      content: '测试2',
      duration: '2小时',
      currentdate: "2019-03-19"
    }
  ]
}

export default db
