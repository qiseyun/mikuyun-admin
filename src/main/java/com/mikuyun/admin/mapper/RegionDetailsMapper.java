package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.RegionDetails;
import com.mikuyun.admin.vo.RegionTreeVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 地区表 Mapper 接口
 * </p>
 *
 * @author qiseyun
 * @since 2025/03/02 22:03
 */
public interface RegionDetailsMapper extends BaseMapper<RegionDetails> {

    /**
     * 获取所有地区列表
     *
     * @return List<RegionTreeVO>
     */
    @Select("SELECT `id`, `pid`, `name`, `zip` FROM region_details")
    List<RegionTreeVO> getRegionList();

}
