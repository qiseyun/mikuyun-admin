package com.mikuyun.admin.controller;


import com.mikuyun.admin.common.R;
import com.mikuyun.admin.service.IRegionDetailsService;
import com.mikuyun.admin.vo.RegionTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2025/03/02 22:03
 */
@Tag(name = "地区相关接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/region")
public class RegionDetailsController {

    private final IRegionDetailsService regionDetailsService;

    /**
     * 查询所有城市列表（树结构）
     *
     * @return 树结构城市列表
     */
    @GetMapping("/tree")
    @Operation(summary = "树结构城市列表")
    public R<List<RegionTreeVO>> getAllCityTree() {
        return R.ok(regionDetailsService.getTree());
    }

}
