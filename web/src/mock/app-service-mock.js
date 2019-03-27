import mockUtils from './mock-utils'
import store from '@/store'

const db = store.state.db

export default {
  queryPage() {
    return mockUtils.successPage(db.tb_sys_app, db.tb_sys_app.length)
  },
  createApp(req) {
    return mockUtils.success()
  }
}
