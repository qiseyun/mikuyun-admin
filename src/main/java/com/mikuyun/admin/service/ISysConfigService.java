package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysConfig;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysconfig.AddSysConfigDto;
import com.mikuyun.admin.dto.sysconfig.SysConfigListDto;
import com.mikuyun.admin.dto.sysconfig.UpdateSysConfigDto;
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
     * @param dto 查询参数
     * @return 分页结果
     */
    List<SysConfigListVo> getSysConfigList(SysConfigListDto dto);

    /**
     * 新增系统配置
     *
     * @param dto 新增参数
     */
    void addSysConfig(AddSysConfigDto dto);

    /**
     * 更新系统配置
     *
     * @param dto 更新参数
     */
    void updateSysConfig(UpdateSysConfigDto dto);

    /**
     * 删除系统配置
     *
     * @param dto 配置ID
     */
    void delSysConfig(IdDto dto);
}
