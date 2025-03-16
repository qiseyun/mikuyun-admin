package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.RegionDetails;
import com.mikuyun.admin.mapper.RegionDetailsMapper;
import com.mikuyun.admin.service.IRegionDetailsService;
import com.mikuyun.admin.util.TreeUtils;
import com.mikuyun.admin.vo.RegionTreeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2025/03/02 22:03
 */
@Service
public class RegionDetailsServiceImpl extends ServiceImpl<RegionDetailsMapper, RegionDetails> implements IRegionDetailsService {

    @Override
    public List<RegionTreeVO> getTree() {
        List<RegionTreeVO> regionList = this.baseMapper.getRegionList();
        RegionTreeVO root = TreeUtils.getRoot(RegionTreeVO.class);
        List<RegionTreeVO> tree = TreeUtils.buildTree(regionList, root);
        return tree.getFirst().getChildren();
    }

}
