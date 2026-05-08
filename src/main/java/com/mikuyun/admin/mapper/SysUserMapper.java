package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.dto.sysuser.SysUserListDto;
import com.mikuyun.admin.vo.sysuser.SysUserListVo;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户列表
     *
     * @param dto 查询参数
     * @return List<SysUserListVo>
     */
    List<SysUserListVo> getSysUsers(SysUserListDto dto);

}
