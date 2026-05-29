package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.dict.DictTypePageDto;
import com.mikuyun.admin.dto.dict.EditDictTypeDto;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.service.IDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
@Tag(name = "字典类型管理")
@AllArgsConstructor
@RestController
@RequestMapping("/dictType")
public class DictTypeController {

    private final IDictTypeService dictTypeService;

    @SaCheckPermission(value = "system:dict_type:page_view")
    @GetMapping("/list")
    @Operation(summary = "列表查询")
    public R<List<DictType>> getList(DictTypePageDto dto) {
        return R.ok(dictTypeService.pageList(dto));
    }

    @SaCheckRole(value = "super_admin")
    @PostMapping("/add")
    @Operation(summary = "新增")
    public R<Void> add(@Valid @RequestBody EditDictTypeDto dto) {
        dictTypeService.add(dto);
        return R.ok();
    }

    @SaCheckRole(value = "super_admin")
    @PostMapping("/update")
    @Operation(summary = "编辑")
    public R<Void> update(@Valid @RequestBody EditDictTypeDto dto) {
        dictTypeService.update(dto);
        return R.ok();
    }

    @SaCheckRole(value = "super_admin")
    @Operation(description = "删除/恢复字典类型")
    @PostMapping(value = "/del")
    public R<Void> update(@RequestBody IdDto dto) {
        dictTypeService.del(dto);
        return R.ok();
    }

}
