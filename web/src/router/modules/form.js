import Layout from '@/views/layout/Layout'
import $$ from '../../allPerms'

const formRouter = {
  path: '/form-builer',
  component: Layout,
  meta: {
    title: '表单编辑器',
    icon: 'tab'
  },
  children: [{
    path: 'index',
    component: () => import('@/views/formBuilder/index'),
    name: 'formBuilder',
    meta: {
      title: '表单编辑器',
      icon: 'tab',
      funcName: $$.ADMIN_FORM
    }
  },
  {
    path: 'formShow',
    component: () => import('@/views/formBuilder/formShow'),
    name: 'formShow',
    meta: {
      title: '表单显示-演示',
      icon: 'tab'
    }
  }
  ]
}
export default formRouter
