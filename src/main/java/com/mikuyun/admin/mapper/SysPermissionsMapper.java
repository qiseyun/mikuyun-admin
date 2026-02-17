package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.SysPermissions;
import com.mikuyun.admin.vo.syspermissions.SysPermissionListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {

    /**
     * 根据角色列表查询角色所属的权限列表
     *
     * @param roleIds 角色id列表
     * @return 菜单按钮权限列表
     */
    Set<String> sysRolePermissions(@Param("roleIds") List<Integer> roleIds);

    /**
     * 主菜单列表
     *
     * @param parentId 是否是父节点(1是; 0不是)
     * @return 菜单信息列表
     */
    List<SysPermissionListVo> queryPermissionList(@Param("parentId") Integer parentId);

    /**
     * 管理员菜单树
     *
     * @param sysUserId 管理员id
     * @return 菜单树
     */
    List<SysPermissionListVo> getPermissionList(@Param("sysUserId") Integer sysUserId);

    /**
     * 超级管理员菜单树
     *
     * @return 菜单树
     */
    List<Integer> getRolePermissionIds(@Param("roleId") Integer roleId);

}
