package com.mikuyun.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.evt.DictType.DictTypeEvt;
import com.mikuyun.admin.evt.DictType.DictTypePageEvt;
import com.mikuyun.admin.service.IDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典类型 前端控制器
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
@AllArgsConstructor
@RestController
@RequestMapping("/dictType")
public class DictTypeController {

    private final IDictTypeService dictTypeService;

    @GetMapping("/list")
    @Operation(summary = "列表查询")
    public R<IPage<DictType>> getList(DictTypePageEvt evt) {
        return R.ok(dictTypeService.pageList(evt));
    }

    @GetMapping("/add")
    @Operation(summary = "新增")
    public R<Void> add(@Valid @RequestBody DictTypeEvt evt) {
        dictTypeService.add(evt);
        return R.ok();
    }

    @GetMapping("/update")
    @Operation(summary = "编辑")
    public R<Void> update(@Valid @RequestBody DictTypeEvt evt) {
        dictTypeService.update(evt);
        return R.ok();
    }

}
