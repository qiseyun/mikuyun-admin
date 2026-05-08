package com.mikuyun.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.dto.dict.DictTypePageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典类型 Mapper 接口
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
public interface DictTypeMapper extends BaseMapper<DictType> {

    /**
     * 列表查询
     *
     * @return List<DictType>
     */
    List<DictType> queryPageList(@Param("dto") DictTypePageDto dto);

}

