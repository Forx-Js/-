package ht.service.common.service;

import ht.framework.exception.ValidateException;
import ht.framework.util.Validator;
import ht.service.common.po.UploadFilePo;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by zhong on 2018/1/11.
 */
@Service
public class DownloadFileService {

    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private ParameterProvider parameterProvider;

    public DownloadFileInfo extractFileInfo(String uri) {
        String fileName = FilenameUtils.getName(uri);
        DownloadFileInfo fileInfo = new DownloadFileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setExtension(FilenameUtils.getExtension(fileName));
        boolean isThumbnail = StringUtils.endsWithIgnoreCase(fileName, "-thumb." + fileInfo.getExtension());
        fileInfo.setThumbnail(isThumbnail);
        String[] tokens = StringUtils.split(FilenameUtils.getBaseName(fileName), "-");
        fileInfo.setFileKey(tokens[0]);
        return fileInfo;
    }

    public InputStream getFile(DownloadFileInfo fileInfo, String sessionUserId) throws FileNotFoundException {
        validate(fileInfo);
        UploadFilePo po = uploadFileService.findFile(fileInfo.getFileKey());
        validateUploadFile(po, sessionUserId);
        String rootPath = parameterProvider.uploadRootDirectory();//d:/zhong/upload
        String fileName;
        if (fileInfo.isThumbnail()) {
            fileName = FilenameUtils.concat(rootPath, po.getThumbPath());
        } else {
            fileName = FilenameUtils.concat(rootPath, po.getFilePath());
        }
        File file = new File(fileName);
        if (!file.exists()) {
            throw new ValidateException("文件不存在");
        }
        return new FileInputStream(file);
    }

    private void validateUploadFile(UploadFilePo po, String sessionUserId) {
        Validator.notNull(po, "文件不存在");
    }

    private void validate(DownloadFileInfo fileInfo) {
        Validator.notBlank(fileInfo.getFileKey(), "文件名格式错误");
    }

}
