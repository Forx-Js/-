import request from '@/utils/request'

export default {
  queryPage() {
    return request.post('/sys/app/page', {})
      .then(res => res)
  },
  createApp(form) {
    return request.post('/sys/app/create', form)
      .then(res => res.data)
  }
}
