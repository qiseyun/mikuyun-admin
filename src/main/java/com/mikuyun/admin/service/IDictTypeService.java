package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.evt.DictType.DictTypeEvt;
import com.mikuyun.admin.evt.DictType.DictTypePageEvt;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
public interface IDictTypeService extends IService<DictType> {

    /**
     * 列表查询
     *
     * @return List<DictType>
     */
    IPage<DictType> pageList(DictTypePageEvt evt);

    /**
     * 新增
     *
     * @param evt 参数
     */
    void add(DictTypeEvt evt);

    /**
     * 编辑
     *
     * @param evt 参数
     */
    void update(DictTypeEvt evt);

}
