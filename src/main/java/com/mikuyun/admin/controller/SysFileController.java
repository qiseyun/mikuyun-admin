package com.mikuyun.admin.controller;


import com.mikuyun.admin.common.R;
import com.mikuyun.admin.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Tag(name = "文件上传")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class SysFileController {

    private final FileUploadService fileUploadService;

    @Operation(summary = "上传文件(minio)")
    @PostMapping("/upload/minio")
    public R<String> uploadMinio(@RequestParam MultipartFile file, @RequestParam(value = "type") String type) {
        return R.ok(fileUploadService.uploadFileMinio(file, type));
    }

    @Operation(summary = "上传文件(qiniu)")
    @PostMapping("/upload/qiniu")
    public R<String> uploadQiniu(@RequestParam MultipartFile file, @RequestParam(value = "type") String type) {
        return R.ok(fileUploadService.uploadFileQiniu(file, type));
    }

}
