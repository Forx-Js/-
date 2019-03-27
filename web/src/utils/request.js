import axios from 'axios'
import { getToken, removeToken } from '@/utils/auth'
import {
  Loading,
  Message,
  MessageBox
} from 'element-ui';

const SUCCESS_CODE = "0"

const axios_instance = axios.create({
  // api 的 base_url，定义在 config/dev|prod|site.env.js
  baseURL: process.env.BASE_API,
  timeout: 5000
})

axios_instance.interceptors.request.use(
  config => {
    config.headers['token'] = getToken()
    config.headers['Content-Type']="application/x-www-form-urlencoded"
    createLoading()
    console.debug("============>")
    console.debug("begin: " + config.url)
    console.debug(config)
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)
axios.defaults.headers.post['Content-Type'] = 'application/json'

axios_instance.interceptors.response.use(
  response => {
    closeLoading();
    const res = response.data
    console.debug(response)
    console.debug("<<<<<<<<<<")
    if (res.result !== SUCCESS_CODE) {
      Message({
        message: res.message || '请求处理失败',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    closeLoading();
    console.log('err: ' + error)
    var status = error.response && error.response.status
    if (status === 403) {
      removeToken()
      MessageBox.alert('您的登录已过期，即将返回登录页面', '提示', {
        showConfirmButton: false,
        center: true,
        type: "error",
        callback: action => { }
      });
      setTimeout(function () {
        MessageBox.close();
        window.vroute.replace({
          name: "login",
          params: {
            login: false
          }
        });
      }, 3000)
    } else {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

let loadingInstance = null
var loadingInstanceIndex = {
  num: 0
}
function createLoading() {
  loadingInstance = Loading.service({
    fullscreen: true,
    background: "transparent",
    text: "加载中..."
  });
  loadingInstanceIndex.num++;
  loadingInstanceIndex.time = Date.now();
}

function closeLoading() {
  loadingInstanceIndex.num--;
  if (loadingInstanceIndex.num <= 0) {
    loadingInstanceIndex.num = 0;
    const timeOut = Math.max(500 - (Date.now() - loadingInstanceIndex.time), 0)
    setTimeout(() => {
      loadingInstance.close();
    }, timeOut)
  }
}

export default axios_instance
