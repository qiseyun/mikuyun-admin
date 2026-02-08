package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysConfig;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysconfig.AddSysConfigEvt;
import com.mikuyun.admin.evt.sysconfig.SysConfigListEvt;
import com.mikuyun.admin.evt.sysconfig.UpdateSysConfigEvt;
import com.mikuyun.admin.vo.sysconfig.SysConfigListVo;

import java.util.List;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2026-02-08 17:07
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 分页查询系统配置列表
     *
     * @param evt 查询参数
     * @return 分页结果
     */
    List<SysConfigListVo> getSysConfigList(SysConfigListEvt evt);

    /**
     * 新增系统配置
     *
     * @param evt 新增参数
     */
    void addSysConfig(AddSysConfigEvt evt);

    /**
     * 更新系统配置
     *
     * @param evt 更新参数
     */
    void updateSysConfig(UpdateSysConfigEvt evt);

    /**
     * 删除系统配置
     *
     * @param evt 配置ID
     */
    void delSysConfig(IdEvt evt);
}
