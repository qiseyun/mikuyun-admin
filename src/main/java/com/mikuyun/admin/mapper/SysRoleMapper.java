package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询登录的系统用户角色信息列表
     *
     * @param sysUserId 系统用户id
     * @return 角色列表
     */
    List<SysRole> querySysRoleInfo(@Param("sysUserId") Object sysUserId);

    /**
     * 查询登录的系统用户角色列表
     *
     * @param sysUserId 系统用户id
     * @return 角色列表
     */
    List<String> querySysRole(@Param("sysUserId") Object sysUserId);

    /**
     * 查询角色列表
     *
     * @return 角色信息列表
     */
    List<QuerySysRoleListVo> queryRoleList();

}
