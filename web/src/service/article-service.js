function fetchList(query) {
  var data = {
    total: 1,
    items: [
      {
        id: "1",
        timestamp: "2019-01-01 18:00:01"
      }
    ]
  }
  var response = {
    data: data
  }
  return Promise.resolve(response)
}

export default {
  fetchList
}
