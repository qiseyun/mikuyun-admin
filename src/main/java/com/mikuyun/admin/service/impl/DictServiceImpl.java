package com.mikuyun.admin.service.impl;

import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.mapper.DictMapper;
import com.mikuyun.admin.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.vo.dict.DictVo;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<DictVo> getDictListByType(Integer dictTypeId) {
        return List.of();
    }

}
