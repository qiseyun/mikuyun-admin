package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.SysMenu;
import com.mikuyun.admin.vo.sysmenu.GetMenuTreeVo;
import com.mikuyun.admin.vo.sysmenu.QueryMenuListVo;
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
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色列表查询角色所属的菜单或按钮权限列表
     *
     * @param roleIds 角色id列表
     * @return 菜单按钮权限列表
     */
    Set<String> sysRoleMenuPermissions(@Param("roleIds") List<Integer> roleIds);

    /**
     * 主菜单列表
     *
     * @param parentId 是否是父节点(1是; 0不是)
     * @return 菜单信息列表
     */
    List<QueryMenuListVo> queryMenuList(@Param("parentId") Integer parentId);

    /**
     * 管理员菜单树
     *
     * @param sysUserId 管理员id
     * @return 菜单树
     */
    List<GetMenuTreeVo> getMenuTree(@Param("sysUserId") Integer sysUserId);

}
