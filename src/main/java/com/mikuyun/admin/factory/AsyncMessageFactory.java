package com.mikuyun.admin.factory;

import com.mikuyun.admin.rocketmq.IAsyncMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikuyun
 * @since 2026/4/2 22:43
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncMessageFactory implements InitializingBean {

    private final ApplicationContext applicationContext;

    private final Map<String, IAsyncMessageService> asyncMessageServiceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Map<String, IAsyncMessageService> beansOfType = applicationContext.getBeansOfType(IAsyncMessageService.class);
        beansOfType.values().forEach(v -> asyncMessageServiceMap.put(v.getTypeEnum().getType(), v));
        log.info("AsyncMessageFactory initializing success");
    }

    /**
     * 根据类型获取IAsyncMessageService
     *
     * @param type 类型
     * @return IAsyncMessageService
     */
    public IAsyncMessageService getAsyncMessageService(String type) {
        return asyncMessageServiceMap.getOrDefault(type, null);
    }

}
