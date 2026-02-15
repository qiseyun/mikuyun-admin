package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.SysMenu;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddOrEditMenuOrButtonEvt;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.SysMenuMapper;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.util.TreeUtils;
import com.mikuyun.admin.vo.sysmenu.SysMenuListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleService sysRoleService;

    private final StringRedisTemplate stringRedisTemplate;

    private final SysUserService sysUserService;

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
    public void addMenu(AddOrEditMenuOrButtonEvt evt) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(evt, sysMenu, "id");
        sysMenu.setCreateBy(Integer.valueOf(StpUtil.getLoginId().toString()));
        this.save(sysMenu);
    }

    @Override
    public void updateMenu(AddOrEditMenuOrButtonEvt evt) {
        if (ObjectUtil.isEmpty(evt.getId())) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        SysMenu menu = this.getById(evt.getId());
        String beforeData = JSON.toJSONString(menu);
        menu.setName(evt.getName());
        menu.setPermission(evt.getPermission());
        menu.setKeepAlive(evt.getKeepAlive());
        menu.setDescribe(evt.getDescribe());
        menu.setUpdateBy(Integer.valueOf(StpUtil.getLoginId().toString()));
        menu.setGmtModified(LocalDateTime.now());
        this.updateById(menu);
        String afterData = JSON.toJSONString(menu);
        log.info("权限编辑: id={} \n beforeData={} \n afterData={}", evt.getId(), beforeData, afterData);
    }

    @Override
    public void delete(IdEvt evt) {
        this.lambdaUpdate()
                .set(BaseEntity::getIsDelete, 1)
                .set(BaseEntity::getUpdateBy, Integer.valueOf(StpUtil.getLoginId().toString()))
                .set(BaseEntity::getGmtModified, LocalDateTime.now())
                .eq(SysMenu::getId, evt.getId())
                .update();
    }

    @Override
    public List<Integer> getRolePermissions(Integer roleId) {
        // 查询权限列表
        return this.baseMapper.getRoleMenuIds(roleId);
    }

}
