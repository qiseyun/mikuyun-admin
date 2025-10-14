package com.mikuyun.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.evt.DictType.DictTypeEvt;
import com.mikuyun.admin.evt.DictType.DictTypePageEvt;
import com.mikuyun.admin.exception.BizException;
import com.mikuyun.admin.mapper.DictTypeMapper;
import com.mikuyun.admin.service.IDictTypeService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {

    @Override
    public IPage<DictType> pageList(DictTypePageEvt evt) {
        return this.baseMapper.queryPageList(evt, evt.generatePageCountTrue());
    }

    @Override
    public void add(DictTypeEvt evt) {
        DictType dictType = BeanUtil.copyProperties(evt, DictType.class, "id");
        try {
            this.save(dictType);
        } catch (DuplicateKeyException e) {
            throw new BizException("当前字典类型码已存在");
        }
    }

    @Override
    public void update(DictTypeEvt evt) {
        if (ObjectUtil.isEmpty(evt.getId())) {
            throw new BizException("所修改条目的id不能为空");
        }
        DictType dictType = BeanUtil.copyProperties(evt, DictType.class);
        this.updateById(dictType);
    }

}
