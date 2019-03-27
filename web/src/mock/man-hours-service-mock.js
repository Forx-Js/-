import mockUtils from './mock-utils'
import store from '@/store'

const db = store.state.db

export default {
  addhoursReport(form) {
    console.log(form.body, "0000")
    return mockUtils.success(form.body)
  },
  querydata() {
    return mockUtils.successPage(db.tb_sys_form_man_hours, db.tb_sys_form_man_hours.length)
  }
}
