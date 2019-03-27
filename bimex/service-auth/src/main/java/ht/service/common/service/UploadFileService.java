package ht.service.common.service;

import ht.framework.util.TimeSource;
import ht.framework.util.UploadFileInfo;
import ht.service.common.dao.UploadFileDao;
import ht.service.common.po.UploadFilePo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhong on 2018/1/10.
 */
@Service
public class UploadFileService {
    @Resource
    private TimeSource timeSource;
    @Resource
    private UploadFileDao uploadFileDao;

    public UploadFilePo save(UploadFileInfo fileInfo, String userId, String s) {
        String key = FilenameUtils.getBaseName(fileInfo.getFileName());
        UploadFilePo po = new UploadFilePo();
        po.setFilePath(fileInfo.getFilePath());
        po.setFileType(fileInfo.getFileType());
        po.setFileKey(key);
        po.setFileName(fileInfo.getFileName());
        po.setCreateTime(timeSource.getCurrentTime());
        po.setUserId(userId);
        po.setIfValid(0);
        po.setThumbPath(fileInfo.getThumbPath());
        po.setRealName(fileInfo.getOriginalFileName());
        po.setFileSize(s);
        uploadFileDao.create(po);
        return po;
    }

    public UploadFilePo findFile(String fileKey) {
        return uploadFileDao.find(fileKey);
    }
}
