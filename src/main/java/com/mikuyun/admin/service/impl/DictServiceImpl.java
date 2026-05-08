package com.mikuyun.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.mapper.DictMapper;
import com.mikuyun.admin.service.IDictService;
import com.mikuyun.admin.vo.dict.DictVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public List<DictVo> getDictListByTypeId(Integer dictTypeId) {
        return this.baseMapper.queryDictListByTypeId(dictTypeId);
    }

    @Override
    public Map<String, List<DictVo>> getDictListByTypeCodes(String dictTypeCodes) {
        List<String> codeList = Arrays.asList(dictTypeCodes.split(","));
        List<DictVo> dictVos = this.baseMapper.queryDictListByTypeCodes(codeList);
        if (CollectionUtil.isEmpty(dictVos)) {
            return new HashMap<>();
        }
        return dictVos.stream().collect(Collectors.groupingBy(DictVo::getDictTypeCode));
    }
}
