import Layout from '@/views/layout/Layout'
import $$ from '../../allPerms'

const adminRouter = {
  path: '/admin/app',
  component: Layout,
  redirect: '/admin/app',
  name: 'appAdmin',
  meta: {
    title: '应用管理',
    icon: 'table'
  },
  children: [{
    path: '/index',
    component: () => import('@/views/admin/app/index'),
    name: 'appAdmin',
    meta: {
      title: '应用管理',
      funcName: $$.ADMIN_APP
    }
  }]
}
export default adminRouter
