import { asyncRouterMap, constantRouterMap } from '@/router'
import permissionUtils from '@/utils/permission-utils'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

function hasPermission2(permissions, route) {
  return permissionUtils.canAccessRouter(route)
}

function filterAsyncRouterFromPermissions(routes, permissions) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission2(permissions, tmp)) {
      res.push(tmp)
      if (tmp.children) {
        tmp.children = filterAsyncRouterFromPermissions(tmp.children, permissions)
      }
    }
  })
  return res
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      console.log('permission routers')
      console.log(state)
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const { roles } = data
        let accessedRouters
        if (roles.includes('admin')) {
          accessedRouters = asyncRouterMap
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        }
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    },
    GenerateRoutesFromPermission({ commit }, permissions) {
      return new Promise(resolve => {
        const myPerms = permissions
        const accessedRouters = filterAsyncRouterFromPermissions(asyncRouterMap, myPerms)
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }

  }
}

export default permission
