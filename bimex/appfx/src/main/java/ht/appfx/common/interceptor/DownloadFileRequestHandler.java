package ht.appfx.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhong on 2018/1/10.
 */
public class DownloadFileRequestHandler extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadFileRequestHandler.class);

//    @Resource
//    private DownloadFileService downloadFileService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        try {
//            String uri = request.getRequestURI();
//            LOGGER.debug("url: {}", uri);
//            DownloadFileInfo fileInfo = downloadFileService.extractFileInfo(uri);
//            InputStream stream = downloadFileService.getFile(fileInfo, getSessionUserId());
//            response.setStatus(200);
//            if (UploadFileUtil.isImage(fileInfo.getFileName())) {
//                response.setContentType("image/" + fileInfo.getExtension().toLowerCase());
//            } else {
//                response.setContentType("application/octet-stream");
//            }
//            try {
//                IOUtils.copy(stream, response.getOutputStream());
//            } finally {
//                IOUtils.closeQuietly(stream);
//            }
//        } catch (Exception ex) {
//            LOGGER.debug("下载文件失败", ex);
//            response.setStatus(404);
//        }
        return false;
    }

//    private String getSessionUserId() {
//        SessionUser user = SessionUserContext.getUser();
//        return user.getUserId();
//    }
}
