package com.mikuyun.admin.controller;


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

    @Operation(summary = "发送excel导出通知")
    @PostMapping(value = "/notice")
    public R<Void> notice(@RequestBody IdEvt evt) {
        excelTaskService.notice(evt);
        return R.ok();
    }

}
