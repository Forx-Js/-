package com.ht.bim.command.service;

import com.ht.bim.command.dao.WorkTimeDao;
import com.ht.bim.command.dto.ProjectUserDto;
import com.ht.bim.command.dto.WorkTimeDto;
import com.ht.bim.constant.WorkTimeStatus;
import com.ht.bim.query.dao.ProjectQueryDao;
import com.ht.bim.query.vo.SysUserQueryVo;
import ht.framework.util.TimeSource;
import ht.service.common.service.SerialNoService;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dlj on 2019-3-19.
 */
@Service
public class WorkTimeService {
    @Resource
    private WorkTimeDao workTimeDao;
    @Resource
    private TimeSource timeSource;
    @Resource
    private SerialNoService serialNoService;
    @Resource
    private ProjectQueryDao projectQueryDao;

    public void addWorkTime(WorkTimeDto dto, String userId) {
        dto.setiD(serialNoService.newId());
        dto.setOperTime(timeSource.getCurrentTime());
        dto.setOperUserID(userId);
        dto.setStatus(WorkTimeStatus.NO_SUBMIT);
        //根据项目ID找到此项目的项目经理ID
        SysUserQueryVo sysUserQueryVo = projectQueryDao.queryPrjManage(dto.getProjectID());
        dto.setApprover(sysUserQueryVo.getUserID());
        dto.validateAdd();
        //判断是否为加班
        ArrayList<Object> list = new ArrayList<>();
        //把获取到的提交日期格式化
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        String format = dft2.format(dto.getCommitTime());
        JSONObject request = request(format);
        Iterator iterator = request.values().iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            list.add(next);
        }
        if(list.get(1).equals(0)){
            if(dto.getWorkTime()<=8){
                dto.setOverTime(0);
            }else {
            dto.setOverTime(dto.getWorkTime()-8);
            }
        }else {
            dto.setOverTime(dto.getWorkTime());
        }
        workTimeDao.addWorkTime(dto);
    }

    public void updateWorkTime(WorkTimeDto dto, String userId) {
        dto.setOperUserID(userId);
        dto.setOperTime(timeSource.getCurrentTime());
        //根据项目ID找到此项目的项目经理ID
        SysUserQueryVo sysUserQueryVo = projectQueryDao.queryPrjManage(dto.getProjectID());
        dto.setApprover(sysUserQueryVo.getUserID());
        dto.validateAdd();
        workTimeDao.updateWorkTime(dto);
    }

    /**
     * @Author dlj
     * @Description // 判断日期是否是国家法定节假日（0：工作日 1：法定 3：周六日）
     * @Date 13:30 2019-3-19
     * @Param [httpArg]
     * @return net.sf.json.JSONObject
     **/
        public JSONObject request(String httpArg) {
            BufferedReader reader = null;
            String result = null;
            JSONObject jsonObjectResult = null;
            StringBuffer sbf = new StringBuffer();
            String httpUrl = "http://api.goseek.cn/Tools/holiday?date=" + httpArg;

            try {
                URL url = new URL(httpUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = sbf.toString();
                jsonObjectResult = JSONObject.fromObject(result);//转为JSONObject对象
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectResult;
        }

    public void deleteWorkTime(String iD) {
            workTimeDao.deleteWorkTime(iD);
    }
    /**
     * @Author dlj
     * @Description //TODO 所有的修改状态都走这个方法
     * @Date 14:23 2019-3-22
     * @Param [keyWords, status]
     * @return void
     **/

    public void updateStatus(String keyWords, Integer status) {
        List<WorkTimeDto> workTimeDtos = buildStatus(keyWords);
        for (WorkTimeDto workTimeDto : workTimeDtos) {
            workTimeDto.setStatus(status);
            //状态不管等于几都用rejectReason这个接收原因
            if(workTimeDto.getStatus() == 4){
                workTimeDao.updateStatusAndRR(workTimeDto);
            }else if(workTimeDto.getStatus() == 5){
                workTimeDao.updateStatusandWR(workTimeDto);
            }else {
                workTimeDao.updateStatus(workTimeDto);
            }
        }

    }
    /**
     * 数据格式：id,reason|id,reason|id,reason|...|
     *
     * @param
     * @param keyWord
     * @return
     */
    private List<WorkTimeDto> buildStatus(String keyWord) {
        List<WorkTimeDto> list = new ArrayList<>();
        String[] items = keyWord.split("\\|");
        for (String s : items) {
            String[] tokens = s.split(",");
            WorkTimeDto dto = new WorkTimeDto();
            dto.setiD(tokens[0]);
            //如果不是驳回跟撤销的时候让原因保存null
            if(tokens.length == 1){
                dto.setRejectReason(null);
            }else {
                dto.setRejectReason(tokens[1]);
            }
            list.add(dto);
        }
        return list;
    }
}
