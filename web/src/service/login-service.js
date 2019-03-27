import request from '@/utils/request'
import { setToken, removeToken } from '@/utils/auth'
import store from '@/store'
import router from '@/router'

export function loginByUsername(loginForm) {
  console.log(loginForm);
  return request.post('/auth/login?username='+loginForm.username+'&password='+loginForm.password)
    .then(res => {
      var token = res.data.token
      console.log('token: ' + token)
      setToken(token)
      processUserInfo(res)
    })
    .catch(err => {
      console.error(err)
      Promise.reject('登录失败')
    })
}

function logout() {
  return request.post('/auth/logout')
    .then(() => removeToken())
}

function getUserInfo() {
  return request.get('/auth/user/info', null)
    .then(res => { processUserInfo(res) })
}

function processUserInfo(res) {
  store.dispatch('setUserInfo', res.data)
  store.dispatch('GenerateRoutesFromPermission', res.data.permissions || [])
  router.addRoutes(store.getters.addRouters)
}

export default {
  loginByUsername,
  getUserInfo,
  logout
}
