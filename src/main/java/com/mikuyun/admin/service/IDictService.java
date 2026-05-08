package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.vo.dict.DictVo;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
public interface IDictService extends IService<Dict> {

    /**
     * 根据字典类型查询字典枚举
     *
     * @param dictTypeId 字典类型id
     * @return 对应的字典列表
     */
    List<DictVo> getDictListByType(Integer dictTypeId);

}
