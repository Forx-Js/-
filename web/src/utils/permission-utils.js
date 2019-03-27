import store from '@/store'
import { constantRouterMap } from '@/router'

// SWITCH ENABLE / DISABLE PERMISSION CHECK
const ignorePermissionCheck = true

function hasPermission(funcName) {
  if (ignorePermissionCheck) {
    return true
  }
  if (!funcName) {
    return false
  }
  const publics = constantRouterMap.map(item => item.path)
  if (publics.includes(funcName)) {
    return true
  }
  var permissions = store.getters.permissions || []
  return permissions.includes(funcName)
}

function canAccess(funcName) {
  return hasPermission(funcName)
}

function canNotAccess(funcName) {
  return !hasPermission(funcName)
}

function canAccessRouter(router) {
  var funcName = router.name || ''
  if (router.meta && router.meta.funcName) {
    funcName = router.meta.funcName
  }
  return hasPermission(funcName)
}
export default {
  hasPermission,
  canAccess,
  canNotAccess,
  canAccessRouter
}
