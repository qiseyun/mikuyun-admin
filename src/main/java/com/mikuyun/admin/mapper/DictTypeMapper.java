package com.mikuyun.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.evt.DictType.DictTypePageEvt;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

/**
 * <p>
 * 字典类型 Mapper 接口
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
public interface DictTypeMapper extends BaseMapper<DictType> {

    /**
     * 列表查询
     *
     * @return List<DictType>
     */
    IPage<DictType> queryPageList(@Param("evt") DictTypePageEvt evt, @Param("page") Page<T> page);

}

