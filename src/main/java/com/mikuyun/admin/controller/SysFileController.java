package com.mikuyun.admin.controller;


import com.mikuyun.admin.common.R;
import com.mikuyun.admin.properties.RustfsProperties;
import com.mikuyun.admin.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mikuyun
 * @since 2023年3月25日/0025 0点17分
 */
@Tag(name = "文件上传")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class SysFileController {

    private final FileUploadService fileUploadService;

    private final RustfsProperties rustfsProperties;

    @Operation(summary = "上传文件(qiniu)")
    @PostMapping("/upload/qiniu")
    public R<String> uploadQiniu(@RequestParam MultipartFile file) {
        return R.ok(fileUploadService.uploadFileQiniu(file));
    }

    @Operation(summary = "上传文件(RustFS单文件)")
    @PostMapping("/upload/rustfs")
    public R<String> uploadRustfs(@RequestParam MultipartFile file) {
        return R.ok(fileUploadService.uploadFileRustfs(file));
    }

    @Operation(summary = "上传文件(RustFS批量, 最多10个)")
    @PostMapping("/upload/rustfs/batch")
    public R<List<String>> uploadRustfsBatch(
            @Parameter(description = "文件列表, 最多10个") @RequestParam("files") List<MultipartFile> files) {
        int batchLimit = rustfsProperties.getBatchLimit();
        if (files.size() > batchLimit) {
            return R.failed("批量上传最多支持" + batchLimit + "个文件");
        }
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(fileUploadService.uploadFileRustfs(file));
        }
        return R.ok(urls);
    }

}
