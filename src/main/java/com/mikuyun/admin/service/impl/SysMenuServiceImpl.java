package com.mikuyun.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.CacheConstants;
import com.mikuyun.admin.common.CommonConstants;
import com.mikuyun.admin.entity.SysMenu;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddMenuOrButtonEvt;
import com.mikuyun.admin.mapper.SysMenuMapper;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.util.SatokenUserUtils;
import com.mikuyun.admin.vo.sysmenu.GetMenuTreeVo;
import com.mikuyun.admin.vo.sysmenu.QueryMenuListVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<String> sysRoleMenuPermissions(Integer sysUserId) {
        // 查询登录用户的角色列表
        List<Integer> collect = sysRoleService.querySysRoleInfo(sysUserId)
                .stream()
                .map(SysRole::getRoleId)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(baseMapper.sysRoleMenuPermissions(collect));
    }

    @Override
    public List<QueryMenuListVo> queryMenuList(IdEvt evt) {
        // 菜单按钮列表
        return baseMapper.queryMenuList(evt.getId());
    }

    @Override
    public void addMenuOrButton(AddMenuOrButtonEvt evt) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(evt, sysMenu);
        sysMenu.setCreateBy(SatokenUserUtils.getUserInfo().getId());
        this.save(sysMenu);
    }

    @Override
    public List<GetMenuTreeVo> getMenuTree() {
        Integer sysUserId = SatokenUserUtils.getUserInfo().getId();
        // 先从redis取
        String key = CacheConstants.MENU_TREE + sysUserId;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            return JSON.parseArray(stringRedisTemplate.opsForValue().get(key), GetMenuTreeVo.class);
        }
        List<GetMenuTreeVo> allMenuTree = baseMapper.getMenuTree(sysUserId);
        List<GetMenuTreeVo> treeVo = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(allMenuTree)) {
            List<GetMenuTreeVo> menuTree = new ArrayList<>();
            for (GetMenuTreeVo menu : allMenuTree) {
                if (menu.getParentId() == 0) {
                    // 递归列表
                    recursionFn(allMenuTree, menu);
                    menuTree.add(menu);
                }
            }
            // 根节点
            GetMenuTreeVo menu = new GetMenuTreeVo();
            menu.setMenuId(CommonConstants.MENU_TREE_ROOT_ID);
            menu.setName(CommonConstants.MENU_TREE_ROOT_NAME);
            menu.setChildren(menuTree);
            treeVo.add(menu);
            // 存redis
            String jsonString = JSON.toJSONString(treeVo);
            stringRedisTemplate.opsForValue().set(key, jsonString);
            System.out.println(jsonString);
        }
        return treeVo;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<GetMenuTreeVo> menuList, GetMenuTreeVo menu) {
        // 得到子节点列表
        List<GetMenuTreeVo> childList = getChildList(menuList, menu);
        menu.setChildren(childList);
        for (GetMenuTreeVo child : childList) {
            if (hasChild(menuList, child)) {
                //判断是否有子节点
                for (GetMenuTreeVo n : childList) {
                    recursionFn(menuList, n);
                }
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<GetMenuTreeVo> menuList, GetMenuTreeVo menu) {
        return !getChildList(menuList, menu).isEmpty();
    }

    /**
     * 得到子节点列表
     */
    private List<GetMenuTreeVo> getChildList(List<GetMenuTreeVo> menuList, GetMenuTreeVo menu) {
        List<GetMenuTreeVo> childlist = new ArrayList<>();
        for (GetMenuTreeVo menuChild : menuList) {
            if (menuChild.getParentId().equals(menu.getMenuId())) {
                childlist.add(menuChild);
            }
        }
        return childlist;
    }

}
