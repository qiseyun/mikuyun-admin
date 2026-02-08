package com.mikuyun.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysConfig;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysconfig.AddSysConfigEvt;
import com.mikuyun.admin.evt.sysconfig.SysConfigListEvt;
import com.mikuyun.admin.evt.sysconfig.UpdateSysConfigEvt;
import com.mikuyun.admin.mapper.SysConfigMapper;
import com.mikuyun.admin.service.ISysConfigService;
import com.mikuyun.admin.vo.sysconfig.SysConfigListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2026-02-08 17:07
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public List<SysConfigListVo> getSysConfigList(SysConfigListEvt evt) {
        List<SysConfig> sysConfigList = this.lambdaQuery()
                .eq(SysConfig::getIsDelete, 0)
                .like(StrUtil.isNotBlank(evt.getConfigName()), SysConfig::getConfigName, evt.getConfigName())
                .eq(StrUtil.isNotBlank(evt.getConfigKey()), SysConfig::getConfigKey, evt.getConfigKey())
                .orderByDesc(SysConfig::getId)
                .last("limit " + evt.getOffset() + ", " + evt.getSize())
                .list();
        return sysConfigList.stream().map(item -> {
            SysConfigListVo vo = new SysConfigListVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).toList();
    }

    @Override
    public void addSysConfig(AddSysConfigEvt evt) {
        // 检查参数键是否已存在
        boolean exists = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, evt.getConfigKey())
                .eq(SysConfig::getIsDelete, 0)
                .exists();
        if (exists) {
            throw new RuntimeException("参数键已存在");
        }
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(evt, sysConfig);
        this.save(sysConfig);
    }

    @Override
    public void updateSysConfig(UpdateSysConfigEvt evt) {
        // 检查参数键是否存在且不与当前记录冲突
        boolean exists = this.lambdaQuery()
                .eq(SysConfig::getConfigKey, evt.getConfigKey())
                .ne(SysConfig::getId, evt.getId())
                .eq(SysConfig::getIsDelete, 0)
                .exists();
        if (exists) {
            throw new RuntimeException("参数键已存在");
        }
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(evt, sysConfig);
        this.updateById(sysConfig);
    }

    @Override
    public void delSysConfig(IdEvt evt) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setId(evt.getId());
        sysConfig.setIsDelete(1);
        this.updateById(sysConfig);
    }
}
