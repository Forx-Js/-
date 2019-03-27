package ht.appfx.controller;

import ht.appfx.common.interceptor.AuthIgnore;
import ht.appfx.common.interceptor.LoginUser;
import ht.appfx.common.interceptor.UserVo;
import ht.framework.exception.ExceptionHandler;
import ht.framework.result.ApiResult;
import ht.framework.util.UploadFileInfo;
import ht.framework.util.UploadFileUtil;
import ht.service.auth.dao.UserDao;
import ht.service.auth.po.User;
import ht.service.common.po.UploadFilePo;
import ht.service.common.service.ParameterProvider;
import ht.service.common.service.UploadFileService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
 * Created by zeng.ling on 2017/11/20.
 */
@RestController
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private ParameterProvider parameterProvider;
    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private UserDao userDao;

    /**
     * 通用的文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/common/upload", method = RequestMethod.POST)
    @ResponseBody
    @AuthIgnore
    public ApiResult<UploadFilePo> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                                              @LoginUser UserVo userVo, HttpServletRequest request) {
        String name = "";
        try {
            String userId = null;
            if (userVo != null) {
                userId = userVo.getUserId();
                User user = userDao.find(userId);
                name = user.getName();
            }
            UploadFileInfo fileInfo = UploadFileUtil.uploadFile2(file, parameterProvider.uploadRootDirectory());
            long size = file.getSize();
            String s = FormetFileSize(size);
            UploadFilePo po = uploadFileService.save(fileInfo, userId,s);
            po.setFileSize(s);
            po.setOperUserName(name);
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

            return new ApiResult(po);
        } catch (Exception ex) {
            LOGGER.debug("上传文件失败", ex);
            return ApiResult.fail(ExceptionHandler.getErrorMessage(ex));
        }
    }
    /**
     * @Author dlj
     * @Description long类型转换KB,MB,GB
     * @Date 16:35 2019-3-7
     * @Param [file]
     * @return java.lang.String
     **/

    public String FormetFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / 1024) + "K";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
