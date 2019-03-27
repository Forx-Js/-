import Layout from '@/views/layout/Layout'
// import system_role from '../views/systemSetting/personSetting/system_role'
// import project_role from '../views/systemSetting/personSetting/project_role'
// import $$ from '../../allPerms'

const personRouter = {
  path: '/person',
  component: Layout,
  redirect: '/person',
  name: 'perSon',
  meta: {
    title: '人员设置',
    icon: 'table'
  },
  children: [{
    path: 'permissions',
    component: () => import('@/views/systemSetting/personSetting/permissions'),
    // redirect: '@/views/systemSetting/personSetting/system_role',
    name: 'permissions',
    meta: {
      title: '权限设置'
    }
    // children: [
    //   {
    //     path: 'system_role',
    //     // component:system_role,
    //     component: () => import('@/views/systemSetting/personSetting/system_role'),
    //     name: 'system_role',
    //     meta: {
    //       // funcName: allPerms.SYSTEM_ROLE,
    //       // title: '系统角色'
    //     }
    //   },
    //   {
    //     path: 'project_role',
    //     // component:project_role,
    //     component: () => import('@/views/systemSetting/personSetting/project_role'),
    //     name: 'project_role',
    //     meta: {
    //       // funcName: allPerms.SYSTEM_ROLE,
    //       // title: '项目角色'
    //       // noCache: true
    //     }
    //   }]
  },
  {
    path: 'personmanage',
    component: () => import('@/views/systemSetting/personSetting/personmanage'),
    name: 'personmanage',
    meta: {
      title: '人员管理'
    }
  }]
}
export default personRouter
