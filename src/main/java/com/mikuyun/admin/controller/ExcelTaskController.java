package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.service.IExcelTaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiangQL
 * @since 2025/04/11 23:27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/excelTask")
public class ExcelTaskController {

    private final IExcelTaskService excelTaskService;

    @SaIgnore
    @Operation(summary = "发送excel导出通知")
    @PostMapping(value = "/notice")
    public R<Void> notice(@RequestBody IdEvt evt) {
        excelTaskService.notice(evt);
        return R.ok();
    }

    @SaIgnore
    @Operation(summary = "获取七牛云私有文件下载链接")
    @PostMapping(value = "/getDownloadUrl")
    public R<String> getDownloadUrl(@RequestBody IdEvt evt) {
        return R.ok(excelTaskService.getDownloadUrl(evt));
    }

}
