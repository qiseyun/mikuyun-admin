package com.mikuyun.admin.controller;

import com.mikuyun.admin.common.R;
import com.mikuyun.admin.service.IDictService;
import com.mikuyun.admin.vo.dict.DictVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
@Tag(name = "字典管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/dict")
public class DictController {

    private final IDictService dictService;

    @Operation(description = "根据字典类型获取该字典所有枚举")
    @GetMapping(value = "/getDictList")
    public R<List<DictVo>> getDictListByType(@RequestParam(value = "dictTypeId") Integer dictTypeId) {
        return R.ok(dictService.getDictListByType(dictTypeId));
    }

}
