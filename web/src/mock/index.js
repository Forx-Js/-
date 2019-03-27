import Mock from 'mockjs'
import loginAPI from './login-service-mock'
import articleAPI from './article'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'
import appServiceMock from './app-service-mock'
import formServiceMock from './form-service-mock'
import ManHoursServiceMock from './man-hours-service-mock'
import userServiceMock from './user-admin-service-mock'

// 修复在使用 MockJS 情况下，设置 withCredentials = true，且未被拦截的跨域请求丢失 Cookies 的问题
// https://github.com/nuysoft/Mock/issues/300
Mock.XHR.prototype.proxy_send = Mock.XHR.prototype.send
Mock.XHR.prototype.send = function () {
  if (this.custom.xhr) {
    this.custom.xhr.withCredentials = this.withCredentials || false
  }
  this.proxy_send(...arguments)
}

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关
//Mock.mock(/\/auth\/login/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/auth\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/auth\/user\/info/, 'get', loginAPI.getUserInfo)

// 文章相关
Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)
Mock.mock(/\/article\/create/, 'post', articleAPI.createArticle)
Mock.mock(/\/article\/update/, 'post', articleAPI.updateArticle)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

Mock.mock(/\/sys\/app\/page/, 'post', appServiceMock.queryPage)
Mock.mock(/\/sys\/app\/create/, 'post', appServiceMock.createApp)
Mock.mock(/\/admin\/form\/page/, 'post', formServiceMock.queryPage)
Mock.mock(/\/admin\/form\/fields\/list-role/, 'get', formServiceMock.queryRoleFormFields)
Mock.mock(/\/admin\/form\/fields\/list/, 'get', formServiceMock.queryFields)

// 工时填报
Mock.mock(/\/man\/add\/hours/, 'post', ManHoursServiceMock.addhoursReport)
Mock.mock(/\/man\/hours\/list/, 'post', ManHoursServiceMock.querydata)

Mock.mock(/\/sys\/role\/list/, 'get', userServiceMock.queryAllRoles)


//工时系统相关
/*已提交工时*/
//Mock.mock(/\/project\/list/, 'get', ManHoursServiceMock.getProjectsData)
//Mock.mock(/\/worktime\/list/, 'get', ManHoursServiceMock.getSubmittedHoursData)
Mock.mock(/\/man\/approvalRecords\/list/, 'post', ManHoursServiceMock.getApprovalRecordsData)
//Mock.mock(/\/worktime\/update-status/, 'post', ManHoursServiceMock.addRecallHour)

export default Mock
