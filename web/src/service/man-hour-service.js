import request from '@/utils/request'
import qs from 'qs'
// import { from } from 'rxjs';

export default {
  querydata() {
    return request.post('/man/hours/list', {})
      .then(res => res)
  },
  addhoursReport(form) {
    console.log(form)
    return request.post('/man/add/hours', form)
      .then(res => res.data)
  },

  //已填报工时模块
  getProjectsData(){
    return request.get('/project/list')
      .then(res => res)
  },
  getSubmittedHoursData(status){
    return request.get('/worktime/list', {
      params: {
        status:status
      }
    })
      .then(res => res)
      .catch(function(reason) {
        console.log('catch:', reason);
      })
  },
  getApprovalRecordsData(params){
    return request.post('/man/approvalRecords/list', params)
      .then(res => res)
  },
  addRecallHour(recallForm){
    console.log(recallForm);
    return request.post('/worktime/update-status', qs.stringify(recallForm))
      .then(res => res.data)
      .catch(function(reason) {
        console.log('catch:', reason);
      })
  }





}
