package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.RegionDetails;
import com.mikuyun.admin.vo.RegionTreeVO;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2025/03/02 22:03
 */
public interface IRegionDetailsService extends IService<RegionDetails> {

    /**
     * 获取地区树
     *
     * @return List<RegionTreeVO>
     */
    List<RegionTreeVO> getTree();

}
