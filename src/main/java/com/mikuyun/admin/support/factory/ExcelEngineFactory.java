package com.mikuyun.admin.support.factory;

import com.mikuyun.admin.excel.IBaseExcelTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 20:33
 */
@Component
@RequiredArgsConstructor
public class ExcelEngineFactory implements InitializingBean {

    @Resource
    private final ApplicationContext applicationContext;

    private final Map<Integer, IBaseExcelTaskService> map = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Map<String, IBaseExcelTaskService> beansOfType = applicationContext.getBeansOfType(IBaseExcelTaskService.class);
        beansOfType.forEach((k, v) -> map.put(v.getExcelTaskType().getType(), v));
    }

    public Boolean isSupport(Integer excelType) {
        return map.containsKey(excelType);
    }

    public IBaseExcelTaskService getService(Integer excelType) {
        return map.get(excelType);
    }

}
