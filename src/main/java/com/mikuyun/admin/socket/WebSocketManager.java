package com.mikuyun.admin.socket;


import com.mikuyun.admin.controller.websocket.WebSocketServer;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/3/15 11:27
 */
@Slf4j
public class WebSocketManager {

    /* WebSocketServer集合 */
    @Getter
    private final static CopyOnWriteArraySet<WebSocketServer> webSocketServerSet = new CopyOnWriteArraySet<>();

    /* 剩余连接用户 */
    @Getter
    private final static CopyOnWriteArraySet<String> satokenSet = new CopyOnWriteArraySet<>();

    /* key：satoken；value：WebSocketServer 集合 */
    @Getter
    private final static ConcurrentHashMap<String, WebSocketServer> webSocketServerMap = new ConcurrentHashMap<>();

    /**
     * 添加链接
     *
     * @param webSocketServer webSocket服务
     * @param satoken         链接用户令牌
     */
    public static void addWebSocketServer(WebSocketServer webSocketServer, String satoken) {
        if (webSocketServer != null) {
            satokenSet.add(satoken);
            webSocketServerSet.add(webSocketServer);
            webSocketServerMap.put(satoken, webSocketServer);
        }
    }

    /**
     * 删除链接
     *
     * @param webSocketServer webSocket服务
     * @param satoken         链接用户令牌
     */
    public static void removeWebSocketServer(WebSocketServer webSocketServer, String satoken) {
        satokenSet.remove(satoken);
        webSocketServerSet.remove(webSocketServer);
        webSocketServerMap.remove(satoken);
    }

    /**
     * 通过satoken发送消息给特定用户
     *
     * @param satoken 用户令牌
     * @param msgJson 消息
     */
    public static void sentToUser(String satoken, String msgJson) {
        if (satoken == null) {
            log.error("不存在该satoken，无法发送消息");
            return;
        }
        WebSocketServer webSocketServer = webSocketServerMap.get(satoken);
        webSocketServer.getSession().getAsyncRemote().sendText(msgJson);
    }

    /**
     * 通过session发送消息给特定用户
     *
     * @param session 用户session
     * @param msgJson 消息
     */
    public static void sentToUser(Session session, String msgJson) {
        if (session == null) {
            log.error("不存在该Session，无法发送消息");
            return;
        }
        session.getAsyncRemote().sendText(msgJson);
    }

    /**
     * 发送消息给所有用户
     *
     * @param msgJson 消息
     */
    public static void sentToAllUser(String msgJson) {
        for (WebSocketServer webSocketServer : webSocketServerSet) {
            sentToUser(webSocketServer.getSession(), msgJson);
        }
        log.info("向所有用户发送WebSocket消息完毕，消息：{}", msgJson);
    }

}
