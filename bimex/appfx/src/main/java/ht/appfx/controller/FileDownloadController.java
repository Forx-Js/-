package ht.appfx.controller;

import ht.appfx.common.interceptor.AuthIgnore;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.exception.ValidateException;
import ht.framework.util.UploadFileUtil;
import ht.service.common.service.DownloadFileInfo;
import ht.service.common.service.DownloadFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zeng.ling on 2017/11/20.
 */
@Controller
public class FileDownloadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadController.class);

    @Resource
    private DownloadFileService downloadFileService;


    @RequestMapping(value = "/upload/{dir}/{name}", method = RequestMethod.GET)
    @AuthIgnore
    public void downloadFile(@PathVariable(name = "dir", required = false) String dir,
            @PathVariable(name = "name") String name, @LoginUser UserVo userVo,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = request.getRequestURI();
            DownloadFileInfo fileInfo = downloadFileService.extractFileInfo(filePath);
            String userId = null;
            if (userVo != null) {
                userId = userVo.getUserId();
            }
            InputStream stream = downloadFileService.getFile(fileInfo, userId);
            /*// 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());*/
            response.setStatus(200);
            /*if (UploadFileUtil.isImage(fileInfo.getFileName())) {
                response.setContentType("image/" + fileInfo.getExtension().toLowerCase());
            }else if("PDF".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/pdf");*/
            if("DOC".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/msword");
            }else if("DOCX".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            }else if("XLS".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/vnd.ms-excel");
            }else if("XLSX".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            }else if("PPT".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("application/vnd.ms-powerpoint");
            }else if("TXT".equals(FilenameUtils.getExtension(fileInfo.getFileName()).toUpperCase())){
                response.setContentType("text/plain");
            }else {
                response.setContentType("image/jpeg");
            }
            try {
                IOUtils.copy(stream, response.getOutputStream());
            } finally {
                IOUtils.closeQuietly(stream);
            }
        } catch (ValidateException ex) {
            response.setStatus(400);
            try {
                response.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                LOGGER.error("输出数据异常", e);
            }
        } catch (Exception e) {
            response.setStatus(400);
            LOGGER.error("输出数据异常", e);
        }
    }

}
