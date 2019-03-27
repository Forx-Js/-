import { getToken } from '@/utils/auth'
import store from '@/store'
const db = store.state.db

const userMap = {
  admin: {
    roles: ['admin'],
    token: 'admin',
    introduction: '我是超级管理员',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  editor: {
    roles: ['editor'],
    token: 'editor',
    introduction: '我是编辑',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}
userMap['zeng.ling'] = {
  roles: ['admin'],
  token: 'zeng.ling',
  introduction: '我是超级管理员',
  avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
  name: 'Zeng ling'
}
export default {
  loginByUsername: config => {
    const { username } = JSON.parse(config.body)
    const user = userMap[username]
    if (user) {
      return {
        result: '0',
        message: null,
        data: user
      }
    } else {
      return {
        result: '1',
        message: '登录失败',
        data: null
      }
    }
  },
  getUserInfo: config => {
    console.debug('---- mock getUserInfo')
    console.debug(config)
    var myToken = getToken()
    console.debug('token = ' + myToken)
    var user = null
    db.tb_user.forEach(item => {
      if (item.token === myToken) {
        user = item
      }
    })
    if (user) {
      return {
        result: '0',
        message: null,
        data: user
      }
    } else {
      return {
        result: '1',
        message: '获取用户信息失败',
        data: null
      }
    }
  },
  logout: () => {
    return {
      result: '0',
      message: null,
      data: null
    }
  }
}
