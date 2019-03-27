import mockUtils from './mock-utils'
import store from '@/store'

const db = store.state.db

export default {
  queryAllRoles() {
    return mockUtils.success(db.tb_role)
  }
}
