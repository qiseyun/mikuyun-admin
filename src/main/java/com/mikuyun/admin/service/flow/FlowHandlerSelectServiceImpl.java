package com.mikuyun.admin.service.flow;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.support.SpringContextUtils;
import org.dromara.warm.flow.core.dto.FlowPage;
import org.dromara.warm.flow.ui.dto.HandlerQuery;
import org.dromara.warm.flow.ui.service.HandlerSelectService;
import org.dromara.warm.flow.ui.vo.HandlerAuth;
import org.dromara.warm.flow.ui.vo.HandlerSelectVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Warm-Flow 流程设计器处理人选择服务实现
 * <p>
 * 实现 Warm-Flow 的 {@link HandlerSelectService} SPI 接口，
 * 将流程设计器中的处理人选择功能对接系统用户表（mk_sys_user）。
 * <p>
 * 支持的查询类型：
 * <ul>
 *   <li>user — 按用户名/姓名搜索系统用户</li>
 *   <li>role — 按角色搜索（预留）</li>
 *   <li>dept — 按部门搜索（预留）</li>
 * </ul>
 *
 * @author mikuyun
 * @since 2026/7/5
 */
@Service
public class FlowHandlerSelectServiceImpl implements HandlerSelectService {

    /**
     * 返回支持的查询类型列表
     */
    @Override
    public List<String> getHandlerType() {
        return Arrays.asList("user", "role", "dept");
    }

    /**
     * 根据查询条件搜索处理人
     *
     * @param handlerQuery 查询条件（handlerCode/Name/Type + 分页参数）
     * @return 处理人选择结果（分页）
     */
    @Override
    public HandlerSelectVo getHandlerSelect(HandlerQuery handlerQuery) {
        String handlerType = handlerQuery.getHandlerType();
        if (StrUtil.isBlank(handlerType)) {
            handlerType = "user";
        }

        HandlerSelectVo vo = new HandlerSelectVo();
        List<HandlerAuth> handlerAuths = new ArrayList<>();
        long total = 0;

        switch (handlerType) {
            case "user" -> {
                List<HandlerAuth> result = queryUsers(handlerQuery);
                handlerAuths = result;
                total = handlerAuths.size();
            }
            case "role" -> {
                // 角色查询预留 — 可扩展为查询 mk_sys_role 表
                handlerAuths = new ArrayList<>();
                total = 0;
            }
            case "dept" -> {
                // 部门查询预留 — 可扩展为查询部门表
                handlerAuths = new ArrayList<>();
                total = 0;
            }
            default -> {
                handlerAuths = new ArrayList<>();
                total = 0;
            }
        }

        // 手动分页
        int pageNum = handlerQuery.getPageNum() != null ? handlerQuery.getPageNum() : 1;
        int pageSize = handlerQuery.getPageSize() != null ? handlerQuery.getPageSize() : 20;
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, handlerAuths.size());
        List<HandlerAuth> pagedList = (fromIndex < handlerAuths.size())
                ? handlerAuths.subList(fromIndex, toIndex)
                : new ArrayList<>();

        FlowPage<HandlerAuth> flowPage = new FlowPage<>();
        flowPage.setRows(pagedList);
        flowPage.setTotal(total);
        flowPage.setCode(200);
        vo.setHandlerAuths(flowPage);

        return vo;
    }

    /**
     * 查询系统用户列表
     */
    private List<HandlerAuth> queryUsers(HandlerQuery query) {
        SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getIsDelete, 0);

        // 按编码（用户名）或姓名模糊搜索
        String keyword = query.getHandlerCode();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.and(w -> w
                    .like(SysUser::getUsername, keyword)
                    .or()
                    .like(SysUser::getRealName, keyword));
        }
        // 也支持按名称搜索
        if (StrUtil.isNotBlank(query.getHandlerName()) && !query.getHandlerName().equals(keyword)) {
            wrapper.like(SysUser::getRealName, query.getHandlerName());
        }

        wrapper.orderByAsc(SysUser::getId);
        List<SysUser> users = sysUserService.list(wrapper);

        if (CollectionUtil.isEmpty(users)) {
            return new ArrayList<>();
        }

        return users.stream()
                .map(user -> new HandlerAuth()
                        .setStorageId(String.valueOf(user.getId()))
                        .setHandlerCode(String.valueOf(user.getId()))
                        .setHandlerName(StrUtil.isNotBlank(user.getRealName())
                                ? user.getRealName() : user.getUsername())
                        .setGroupName(user.getDeptId() != null ? "部门" + user.getDeptId() : "未分配"))
                .collect(Collectors.toList());
    }

}
