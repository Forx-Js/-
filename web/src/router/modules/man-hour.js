import Layout from '@/views/layout/Layout'
// import $$ from '../../allPerms'

const manHourRouter = {
  path: '/man-hour',
  component: Layout,
  redirect: '/man-hour',
  name: 'manHour',
  meta: {
    title: '工时系统',
    icon: 'table'
  },
  children: [{
    path: 'hoursReport',
    component: () => import('@/views/man-hour/hoursReport/index'),
    name: 'hoursReport',
    meta: {
      title: '工时填报'
    }
  },
  {
    path: 'hoursApproval',
    component: () => import('@/views/man-hour/hoursApproval/index'),
    name: 'hoursApproval',
    meta: {
      title: '工时审批'
    }
  },
  {
    path: 'hoursSubmitted',
    component: () => import('@/views/man-hour/hoursSubmitted/index'),
    name: 'hoursSubmitted',
    meta: {
      title: '已提交工时'
    }
  }]
}
export default manHourRouter
