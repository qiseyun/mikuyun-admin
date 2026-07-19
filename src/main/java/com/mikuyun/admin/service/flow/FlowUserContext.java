package com.mikuyun.admin.service.flow;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.support.SpringContextUtils;
import org.dromara.warm.flow.core.dto.FlowParams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程用户上下文工具类
 * <p>
 * 桥接 Sa-Token 用户体系与 Warm-Flow 流程引擎，
 * 提供当前操作用户的 ID、姓名、角色等上下文信息。
 *
 * @author mikuyun
 * @since 2026/7/5
 */
public class FlowUserContext {

    /**
     * 获取当前登录用户 ID（String 形式，适配 Warm-Flow）
     */
    public static String getCurrentUserId() {
        try {
            return StpUtil.getLoginId().toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前登录用户的真实姓名
     */
    public static String getCurrentUserName() {
        String userId = getCurrentUserId();
        if (userId == null) {
            return null;
        }
        SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
        SysUser user = sysUserService.getById(Integer.valueOf(userId));
        return user != null ? user.getRealName() : null;
    }

    /**
     * 获取当前用户的角色标识列表（Warm-Flow permissionFlag 格式）
     * 返回格式：["role:admin", "role:user", ...]
     */
    public static List<String> getCurrentRoleFlags() {
        try {
            List<String> roleList = StpUtil.getRoleList();
            if (CollectionUtil.isEmpty(roleList)) {
                return Collections.emptyList();
            }
            return roleList.stream()
                    .map(roleCode -> "role:" + roleCode)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * 获取当前用户的完整权限标识列表（包含角色和用户自身）
     * 返回格式：["role:admin", "user:123", ...]
     */
    public static List<String> getCurrentPermissionFlags() {
        List<String> flags = new ArrayList<>(getCurrentRoleFlags());
        String userId = getCurrentUserId();
        if (userId != null) {
            flags.add("user:" + userId);
        }
        return flags;
    }

    /**
     * 构建 FlowParams，自动注入当前用户的 handler 和 permissionFlag
     *
     * @param flowCode 流程编码
     * @return FlowParams（已填充 handler 和 permissionFlag）
     */
    public static FlowParams buildFlowParams(String flowCode) {
        FlowParams flowParams = FlowParams.build()
                .flowCode(flowCode)
                .handler(getCurrentUserId());
        List<String> permissionFlags = getCurrentPermissionFlags();
        if (CollectionUtil.isNotEmpty(permissionFlags)) {
            flowParams.permissionFlag(permissionFlags);
        }
        return flowParams;
    }

    /**
     * 构建 FlowParams（不指定 flowCode），自动注入当前用户的 handler 和 permissionFlag
     */
    public static FlowParams buildFlowParams() {
        FlowParams flowParams = FlowParams.build()
                .handler(getCurrentUserId());
        List<String> permissionFlags = getCurrentPermissionFlags();
        if (CollectionUtil.isNotEmpty(permissionFlags)) {
            flowParams.permissionFlag(permissionFlags);
        }
        return flowParams;
    }

    /**
     * 批量将用户 ID 转为用户姓名
     *
     * @param userIds 用户 ID 字符串列表
     * @return userId → realName 映射
     */
    public static Map<String, String> getUserNameMap(List<String> userIds) {
        if (CollectionUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        // 去重 + 过滤空值
        Set<Integer> idSet = userIds.stream()
                .filter(Objects::nonNull)
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return Collections.emptyMap();
        }
        SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
        List<SysUser> users = sysUserService.listByIds(new ArrayList<>(idSet));
        if (CollectionUtil.isEmpty(users)) {
            return Collections.emptyMap();
        }
        return users.stream()
                .collect(Collectors.toMap(
                        user -> String.valueOf(user.getId()),
                        user -> user.getRealName() != null ? user.getRealName() : user.getUsername(),
                        (a, b) -> a
                ));
    }

}
