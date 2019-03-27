import request from '@/utils/request'

export default {
  queryAllRoles() {
    return request.get('/sys/role/list', {})
      .then(res => res.data)
  }
}
