package com.mikuyun.admin.service.flow.listennr;

import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.listener.Listener;
import org.dromara.warm.flow.core.listener.ListenerVariable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 流程启动监听器
 * <p>
 * 监听流程实例启动事件，记录流程启动日志。
 * 通过 @Component 注解注册到 Spring 容器，Warm-Flow 自动发现。
 *
 * @author mikuyun
 * @since 2026/5/31 17:08
 */
@Slf4j
@Component
public class DefStartListener implements Listener {

    @Override
    public void notify(ListenerVariable variable) {
        Instance instance = variable.getInstance();
        Map<String, Object> variableMap = variable.getVariable();
        log.info("流程 {} 开始", instance.getFlowName());
    }

}
