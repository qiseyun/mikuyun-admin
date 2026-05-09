package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.dto.dict.EditDictTypeDto;
import com.mikuyun.admin.dto.dict.DictTypePageDto;

import java.util.List;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
public interface IDictTypeService extends IService<DictType> {

    /**
     * 列表查询
     *
     * @return List<DictType>
     */
    List<DictType> pageList(DictTypePageDto dto);

    /**
     * 新增
     *
     * @param dto 参数
     */
    void add(EditDictTypeDto dto);

    /**
     * 编辑
     *
     * @param dto 参数
     */
    void update(EditDictTypeDto dto);

    /**
     * 删除/恢复字典类型
     *
     * @param dto 参数
     */
    void del(IdDto dto);

}
