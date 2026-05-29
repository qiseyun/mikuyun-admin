package com.mikuyun.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.entity.SysConfig;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysconfig.AddSysConfigDto;
import com.mikuyun.admin.dto.sysconfig.SysConfigListDto;
import com.mikuyun.admin.dto.sysconfig.UpdateSysConfigDto;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.SysConfigMapper;
import com.mikuyun.admin.service.ISysConfigService;
import com.mikuyun.admin.vo.sysconfig.SysConfigListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2026-02-08 17:07
 */
@Slf4j
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public List<SysConfigListVo> getSysConfigList(SysConfigListDto dto) {
        List<SysConfig> sysConfigList = this.lambdaQuery()
                .eq(SysConfig::getIsDelete, 0)
                .like(StrUtil.isNotBlank(dto.getConfigName()), SysConfig::getConfigName, dto.getConfigName())
                .eq(StrUtil.isNotBlank(dto.getConfigKey()), SysConfig::getConfigKey, dto.getConfigKey())
                .orderByDesc(SysConfig::getId)
                .last("limit " + dto.getOffset() + ", " + dto.getSize())
                .list();
        return sysConfigList.stream().map(item -> {
            SysConfigListVo vo = new SysConfigListVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void addSysConfig(AddSysConfigDto dto) {
        // 检查参数键是否已存在
        boolean exists = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, dto.getConfigKey())
                .eq(SysConfig::getIsDelete, 0)
                .exists();
        if (exists) {
            throw new RuntimeException("参数键已存在");
        }
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(dto, sysConfig);
        this.save(sysConfig);
    }

    @Override
    public void updateSysConfig(UpdateSysConfigDto dto) {
        // 检查参数键是否存在且不与当前记录冲突
        boolean exists = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, dto.getConfigKey())
                .ne(SysConfig::getId, dto.getId())
                .eq(SysConfig::getIsDelete, 0)
                .exists();
        if (exists) {
            throw new ServiceException("参数键已存在");
        }
        SysConfig sysConfig = this.getById(dto.getId());
        if (sysConfig.getIsLock().equals(1)) {
            throw new ServiceException(ResultCode.DATA_IS_LOCK);
        }
        String beforeData = JSON.toJSONString(sysConfig);
        BeanUtils.copyProperties(dto, sysConfig);
        sysConfig.setUpdateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        sysConfig.setGmtModified(LocalDateTime.now());
        this.updateById(sysConfig);
        String afterData = JSON.toJSONString(sysConfig);
        log.info("\n系统配置更新: \n beforeData: {} \n afterData:{}", beforeData, afterData);
    }

    @Override
    public void delSysConfig(IdDto dto) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setId(dto.getId());
        sysConfig.setIsDelete(1);
        this.updateById(sysConfig);
    }
}
