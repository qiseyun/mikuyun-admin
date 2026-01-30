package com.mikuyun.admin.service;


import com.mikuyun.admin.common.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月22日/0022 15点52分
 */
public interface FileUploadService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link R}
     */
    String uploadFileMinio(MultipartFile file, String type);

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link R}
     */
    String uploadFileQiniu(MultipartFile file, String type);

}
