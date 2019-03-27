export default {
  fail(msg) {
    return {
      result: '1',
      message: msg,
      data: null
    }
  },
  success(data) {
    return {
      result: '0',
      message: null,
      data: data || null
    }
  },
  successPage(data, total) {
    return {
      result: '0',
      message: null,
      total: total,
      data: data || null
    }
  }
}
