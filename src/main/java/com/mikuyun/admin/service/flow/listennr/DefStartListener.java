package com.mikuyun.admin.service.flow.listennr;

import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.listener.Listener;
import org.dromara.warm.flow.core.listener.ListenerVariable;

import java.util.Map;

/**
 * @auth mikuyun
 * @since 2026/5/31 17:08
 */
@Slf4j
public class DefStartListener implements Listener {

    @Override
    public void notify(ListenerVariable variable) {
        Instance instance = variable.getInstance();
        Map<String, Object> variableMap = variable.getVariable();
        log.info("流程 {} 开始", instance.getFlowName());
    }

}
