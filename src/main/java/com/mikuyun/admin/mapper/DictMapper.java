package com.mikuyun.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.vo.dict.DictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据字典类型查询字典枚举
     *
     * @param dictTypeId 字典类型id
     * @return 对应的字典列表
     */
    List<DictVo> queryDictListByTypeId(@Param("dictTypeId") Integer dictTypeId);

    /**
     * 根据字典类型查询字典枚举
     *
     * @param dictTypeCodes 字典类型code列表
     * @return 对应的字典列表
     */
    List<DictVo> queryDictListByTypeCodes(@Param("dictTypeCodes") List<String> dictTypeCodes);

}

