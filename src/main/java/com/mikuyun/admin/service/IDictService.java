package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.dict.EditDictDto;
import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.vo.dict.DictVo;

import java.util.List;
import java.util.Map;

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
    List<DictVo> getDictListByTypeId(Integer dictTypeId);

    /**
     * 根据字典类型查询字典枚举
     *
     * @param dictTypeCodes 字典类型code
     * @return 对应的字典列表
     */
    Map<String, List<DictVo>> getDictListByTypeCodes(String dictTypeCodes);

    /**
     * 新增字典
     *
     * @param dto 新增参数
     */
    void add(EditDictDto dto);

    /**
     * 新增字典
     *
     * @param dto 新增参数
     */
    void update(EditDictDto dto);

    /**
     * 删除/恢复字典
     *
     * @param dto 参数
     */
    void del(IdDto dto);

}
