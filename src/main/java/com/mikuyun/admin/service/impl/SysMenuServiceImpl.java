package com.mikuyun.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.SysMenu;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.enums.UserTypeEnum;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddMenuOrButtonEvt;
import com.mikuyun.admin.mapper.SysMenuMapper;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.util.SatokenUserUtils;
import com.mikuyun.admin.util.TreeUtils;
import com.mikuyun.admin.vo.UserInfo;
import com.mikuyun.admin.vo.sysmenu.SysMenuListVo;
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
    public List<String> sysRoleMenuPermissions(Object sysUserId) {
        // 查询登录用户的角色列表
        List<Integer> collect = sysRoleService.querySysRoleInfo(sysUserId)
                .stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(baseMapper.sysRoleMenuPermissions(collect));
    }

    @Override
    public List<SysMenuListVo> queryMenuList(IdEvt evt) {
        // 页面组件列表
        List<SysMenuListVo> sysMenuList = baseMapper.queryMenuList(evt.getId());
        // 构建树结构
        SysMenuListVo root = TreeUtils.getRoot(SysMenuListVo.class);
        sysMenuList = TreeUtils.buildTree(sysMenuList, root);
        return sysMenuList.getFirst().getChildren();
    }

    @Override
    public void addMenuOrButton(AddMenuOrButtonEvt evt) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(evt, sysMenu);
        sysMenu.setCreateBy(SatokenUserUtils.getUserInfo().getId());
        this.save(sysMenu);
    }

    @Override
    public List<SysMenuListVo> getMenuTree() {
        UserInfo sysUser = SatokenUserUtils.getUserInfo();
        Integer sysUserId = sysUser.getId();
        Integer userType = sysUser.getUserType();
        // 先从redis取
        String key = Constant.CacheConstants.MENU_TREE + sysUserId;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            return JSON.parseArray(stringRedisTemplate.opsForValue().get(key), SysMenuListVo.class);
        }
        // 查询权限
        List<SysMenuListVo> sysMenuListVo;
        if (userType.equals(UserTypeEnum.SUPER_ADMIN.getType())) {
            sysMenuListVo = this.baseMapper.getAllMenuList();
        } else {
            sysMenuListVo = this.baseMapper.getMenuList(sysUserId);
        }
        if (CollectionUtil.isNotEmpty(sysMenuListVo)) {
            // 构建树
            SysMenuListVo root = TreeUtils.getRoot(SysMenuListVo.class);
            List<SysMenuListVo> tree = TreeUtils.buildTree(sysMenuListVo, root);
            sysMenuListVo = tree.getFirst().getChildren();
            // 存redis
            String jsonString = JSON.toJSONString(sysMenuListVo);
            stringRedisTemplate.opsForValue().set(key, jsonString);
        }
        return sysMenuListVo;
    }

}
