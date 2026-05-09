package com.mikuyun.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.dict.EditDictDto;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.Dict;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.DictMapper;
import com.mikuyun.admin.service.IDictService;
import com.mikuyun.admin.vo.dict.DictVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mikuyun.admin.common.Constant.STATUS_DEL_INT;
import static com.mikuyun.admin.common.Constant.STATUS_NORMAL_INT;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2025-04-18 23:14
 */
@Slf4j
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

    @Override
    public void add(EditDictDto dto) {
        Dict dict = BeanUtil.copyProperties(dto, Dict.class, "id");
        dict.setCreateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        this.save(dict);
        String addJsonData = JSON.toJSONString(dict);
        log.info("新增字典: {}", addJsonData);
    }

    @Override
    public void update(EditDictDto dto) {
        if (ObjectUtil.isEmpty(dto.getId())) {
            throw new ServiceException(ResultCode.PARAM_UNDEFINED);
        }
        Dict beforeData = this.getById(dto.getId());
        if (ObjectUtil.isEmpty(beforeData)) {
            throw new ServiceException(ResultCode.DATA_NOT_EXIST);
        }
        if (!beforeData.getDictTypeId().equals(dto.getDictTypeId())) {
            throw new ServiceException(ResultCode.UPDATE_FAILED);
        }
        String beforeJsonData = JSON.toJSONString(beforeData);
        BeanUtil.copyProperties(dto, beforeData);
        beforeData.setCreateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        this.updateById(beforeData);
        String afterJsonData = JSON.toJSONString(beforeData);
        log.info("编辑字典: before: {}, after: {}", beforeJsonData, afterJsonData);
    }

    @Override
    public void del(IdDto dto) {
        Dict dict = this.getById(dto.getId());
        String userIdStr = StpUtil.getLoginId().toString();
        this.lambdaUpdate()
                .set(BaseEntity::getIsDelete, dict.getIsDelete().equals(STATUS_NORMAL_INT) ? STATUS_DEL_INT : STATUS_NORMAL_INT)
                .set(BaseEntity::getUpdateBy, Integer.parseInt(userIdStr))
                .eq(Dict::getId, dto.getId())
                .update();
        log.info("删除字典: dictId: {}, userId: {}", dict.getId(), userIdStr);
    }

}
