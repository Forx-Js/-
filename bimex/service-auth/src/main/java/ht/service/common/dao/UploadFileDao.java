package ht.service.common.dao;

import ht.service.common.po.UploadFilePo;

/**
 * Created by zeng.ling on 2017/11/18.
 */
public interface UploadFileDao {

    UploadFilePo find(String fileKey);

    void create(UploadFilePo po);

    void updateToValid(String fileKey);
}
