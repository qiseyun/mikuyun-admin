package com.mikuyun.admin.controller.websocket;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mikuyun.admin.exception.BizException;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.socket.WebSocketManager;
import com.mikuyun.admin.vo.UserInfo;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/3/15 11:25
 */
@Getter
@Slf4j
@Component
@RequiredArgsConstructor
@ServerEndpoint("/WebSocket/{satoken}")
public class WebSocketServer {

    private Session session;

    private final SysUserService sysUserService;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "satoken") String satoken) {
        this.session = session;
        log.info("与satoken：{}建立连接", satoken);
        UserInfo sysUserInfo = sysUserService.getSysUserInfo(Integer.parseInt(StpUtil.getLoginId().toString()));
        if (ObjectUtil.isEmpty(sysUserInfo)) {
            throw new BizException("管理员不存在");
        }
        WebSocketManager.addWebSocketServer(this, satoken);
        WebSocketManager.sentToUser(satoken, "WebSocket is connected!");
        WebSocketManager.sentToAllUser("管理员" + sysUserInfo.getRealName() + "已上线");
        log.info("WebSocket剩余连接用户数:{}", WebSocketManager.getSatokenSet().size());
    }

    @OnClose
    public void onClose(@PathParam(value = "satoken") String satoken) {
        WebSocketManager.removeWebSocketServer(this, satoken);
        UserInfo sysUserInfo = sysUserService.getSysUserInfo(Integer.parseInt(StpUtil.getLoginId().toString()));
        WebSocketManager.sentToAllUser("管理员" + sysUserInfo.getRealName() + "已下线");
        log.info("satoken:{}的WebSocket连接关闭", satoken);
        log.info("WebSocket剩余连接用户数:{}", WebSocketManager.getSatokenSet().size());
    }

    @OnMessage
    public void onMessage(String message, @PathParam(value = "satoken") String satoken) {
        log.info("来自satoken:{} 的消息:{}", satoken, message);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam(value = "satoken") String satoken) {
        log.error("satoken:{}, session:{} 的WebSocket发生错误:", satoken, session, error);
    }

    public String getSessionId() {
        return session.getId();
    }

}
