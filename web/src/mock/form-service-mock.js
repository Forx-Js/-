import mockUtils from './mock-utils'
import store from '@/store'
import { findInArray } from '@/utils'

const db = store.state.db

export default {
  queryPage(req) {
    console.debug('--------- queryPage')
    console.debug(req)
    var appId = req.body && req.body.appId
    if (!appId) {
      return mockUtils.successPage(db.tb_sys_form, db.tb_sys_form.length)
    }
    var app = findInArray(db.tb_sys_app, app => app.appId === appId)
    var forms = db.tb_sys_form.filter(item => item.formId === app.formId) || []
    return mockUtils.successPage(forms, forms.length)
  },

  queryFields(req) {
    console.debug('--------- queryFields')
    console.debug(req)
    return mockUtils.success(db.tb_sys_form_field)
  },

  queryRoleFormFields(req) {
    console.debug('--------- queryRoleFormFields')
    console.debug(req)
    return mockUtils.success()
  }
}
