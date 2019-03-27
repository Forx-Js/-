import request from '@/utils/request'

export default {
  queryPage(appId) {
    return request.post('/admin/form/page', { appId })
      .then(res => res)
  },

  queryFields(formId) {
    return request.get('/admin/form/fields/list/' + encodeURIComponent(formId))
      .then(res => res.data)
  },
  queryRoleFormFields(roleId, formId) {
    return request.get('/admin/form/fields/list-role/' + encodeURIComponent(formId) + '/' + encodeURIComponent(roleId))
      .then(res => res.data)
  },
  createForm(processId, nodeId, json) {
    return request.post('/form/config/create', {
      processId: processId || '123',
      nodeId: nodeId || processId || '123',
      json
    }).then(res => res.data)
  },

  queryForm(processID, nodeID) {
    return request.get('/form/config/get', {
      params: {
        processID,
        nodeID
      }
    }).then(res => res.data)
  },

  saveFormData(runtimeID, formConfigID, json) {
    return request.post('/form/data/save', {
      runtimeID,
      formConfigID,
      dataJson: json
    }).then(res => res.data)
  },

  queryFormData(runtimeID, formConfigID) {
    return request.get('/form/data/get', {
      params: {
        runtimeID,
        formConfigID
      }
    }).then(res => res.data)
  }
}
