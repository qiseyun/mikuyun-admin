package com.mikuyun.admin.socket;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.support.SpringContextUtils;
import com.mikuyun.admin.vo.SysUserInfo;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author mikuyun
 * @since 2024/3/15 11:25
 */
@Slf4j
@Component
@ServerEndpoint("/WebSocket/{token}")
public class WebSocketServer {

    @Getter
    private Session session;

    /**
     * 连接建立时缓存的用户姓名，供 onClose 使用（onClose 无 HTTP 请求上下文，无法调用 StpUtil）
     */
    private String userName;

    /**
     * Tomcat WebSocket 容器要求无参构造，Spring Bean 通过静态持有器获取
     */
    private SysUserService sysUserService;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "token") String token) {
        this.session = session;
        log.info("与token：{}建立连接", token);
        try {
            // 通过 URL 中的 token 显式解析登录 ID，避免依赖 HTTP 请求上下文
            Object loginId = StpUtil.getLoginIdByToken(token);
            if (loginId == null) {
                log.error("WebSocket token 无效，无法建立连接: {}", token);
                closeSession(session);
                return;
            }
            if (this.sysUserService == null) {
                this.sysUserService = SpringContextUtils.getBean(SysUserService.class);
            }
            SysUserInfo sysUserInfo = sysUserService.getSysUserInfo(Integer.parseInt(loginId.toString()));
            if (ObjectUtil.isEmpty(sysUserInfo)) {
                log.warn("用户不存在，loginId: {}", loginId);
                closeSession(session);
                return;
            }
            // 缓存用户名，供 onClose 使用
            this.userName = sysUserInfo.getRealName();
            // 验证通过后再加入管理器
            WebSocketManager.addWebSocketServer(this, token);
            WebSocketManager.sentToUser(token, "WebSocket is connected!");
            WebSocketManager.sentToAllUser("用户" + this.userName + "已上线");
            log.info("WebSocket剩余连接用户数:{}", WebSocketManager.getSatokenSet().size());
        } catch (Exception e) {
            log.error("WebSocket onOpen 异常, token: {}", token, e);
            closeSession(session);
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "token") String token) {
        try {
            WebSocketManager.removeWebSocketServer(this, token);
            if (this.userName != null) {
                WebSocketManager.sentToAllUser("管理员" + this.userName + "已下线");
            }
            log.info("token:{} 的WebSocket连接关闭, WebSocket剩余连接用户数:{}", token, WebSocketManager.getSatokenSet().size());
        } catch (Exception e) {
            log.error("WebSocket onClose 异常, token: {}", token, e);
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam(value = "token") String token) {
        if ("ping".equals(message)) {
            return;
        }
        if (this.sysUserService == null) {
            this.sysUserService = SpringContextUtils.getBean(SysUserService.class);
        }
        Object loginId = StpUtil.getLoginIdByToken(token);
        log.info("收到消息, 用户id: {}, 消息内容: {}", loginId.toString(), message);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam(value = "token") String token) {
        log.error("token:{}, session:{} 的WebSocket发生错误:", token, session, error);
        // 确保异常时清理连接，防止连接泄漏
        try {
            WebSocketManager.removeWebSocketServer(this, token);
        } catch (Exception e) {
            log.error("WebSocket onError 清理连接失败, token: {}", token, e);
        }
        closeSession(session);
    }

    /**
     * 安全关闭 WebSocket session
     */
    private void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (Exception e) {
                log.error("关闭 WebSocket session 失败", e);
            }
        }
    }

}
