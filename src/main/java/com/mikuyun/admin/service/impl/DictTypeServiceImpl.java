package com.mikuyun.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.dict.DictTypePageDto;
import com.mikuyun.admin.dto.dict.EditDictTypeDto;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.DictType;
import com.mikuyun.admin.exception.BizException;
import com.mikuyun.admin.mapper.DictTypeMapper;
import com.mikuyun.admin.service.IDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mikuyun.admin.common.Constant.STATUS_DEL_INT;
import static com.mikuyun.admin.common.Constant.STATUS_NORMAL_INT;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
@Slf4j
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {

    @Override
    public List<DictType> pageList(DictTypePageDto dto) {
        dto.generatePageCountFalse();
        return this.baseMapper.queryPageList(dto);
    }

    @Override
    public void add(EditDictTypeDto dto) {
        DictType dictType = BeanUtil.copyProperties(dto, DictType.class, "id");
        try {
            this.save(dictType);
        } catch (DuplicateKeyException e) {
            throw new BizException("当前字典类型码已存在");
        }
    }

    @Override
    public void update(EditDictTypeDto dto) {
        if (ObjectUtil.isEmpty(dto.getId())) {
            throw new BizException("所修改条目的id不能为空");
        }
        DictType dictType = BeanUtil.copyProperties(dto, DictType.class);
        this.updateById(dictType);
    }

    @Override
    public void del(IdDto dto) {
        DictType dictType = this.getById(dto.getId());
        String userIdStr = StpUtil.getLoginId().toString();
        this.lambdaUpdate()
                .set(BaseEntity::getIsDelete, dictType.getIsDelete().equals(STATUS_NORMAL_INT) ? STATUS_DEL_INT : STATUS_NORMAL_INT)
                .set(BaseEntity::getUpdateBy, Integer.parseInt(userIdStr))
                .eq(DictType::getId, dto.getId())
                .update();
        log.info("删除字典类型: dictId: {}, userId: {}", dictType.getId(), userIdStr);
    }

}
