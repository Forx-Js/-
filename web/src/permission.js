import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken } from '@/utils/auth' // getToken from cookie
import loginService from '@/service/login-service'
import permissionUtils from '@/utils/permission-utils'

NProgress.configure({ showSpinner: false })// NProgress Configuration

// permission judge function
function hasPermission(to) {
  return permissionUtils.canAccessRouter(to)
}

const whiteList = ['/login', '/auth-redirect']// no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (getToken()) { // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else if (to.path === '/401') {
      next()
      NProgress.done()
    } else {
      if (!store.getters.permissionInitialized) {
        // ==new
        loginService.getUserInfo()
          .then(() => {
            next({ ...to, replace: true })
          }).catch(err => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
        // ==old

        // if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
        //   store.dispatch('GetUserInfo').then(res => { // 拉取user_info
        //     const roles = res.data.roles || []
        //     store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
        //       router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
        //       next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        //     })
        //   }).catch((err) => {
        //     store.dispatch('FedLogOut').then(() => {
        //       Message.error(err)
        //       next({ path: '/' })
        //     })
        // })
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (hasPermission(to)) {
          next()
        } else {
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
        // 可删 ↑
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
      NProgress.done()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
