package com.mikuyun.admin.factory;

import com.mikuyun.admin.excel.ExcelDataSupplier;
import com.mikuyun.admin.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikuyun
 * @since 2025/4/11 20:33
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelEngineFactory implements InitializingBean {

    private final ApplicationContext applicationContext;

    private final Map<Integer, ExcelDataSupplier> supplierMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Map<String, ExcelDataSupplier> beansOfType = applicationContext.getBeansOfType(ExcelDataSupplier.class);
        beansOfType.forEach((k, v) -> supplierMap.put(v.getExcelTaskType().getType(), v));
        log.info("ExcelEngineFactory initializing success");
    }

    /**
     * 获取导出数据实现类
     *
     * @param excelType excel任务类型
     * @return ExcelDataSupplier
     */
    public ExcelDataSupplier createService(Integer excelType) {
        ExcelDataSupplier supplier = supplierMap.get(excelType);
        if (supplier == null) {
            log.error("不支持的excel任务类型: {}", excelType);
            throw new BizException("导出失败");
        }
        return supplier;
    }

    /**
     * 是否支持该类型的导出
     *
     * @param excelType excel任务类型
     * @return Boolean
     */
    public Boolean isSupport(Integer excelType) {
        return supplierMap.containsKey(excelType);
    }

}
