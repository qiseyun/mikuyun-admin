package com.mikuyun.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.excel.enums.ExcelTaskTypeEnum;
import com.mikuyun.admin.mapper.ExcelTaskMapper;
import com.mikuyun.admin.service.IExcelTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiangQL
 * @since 2025/04/11 23:27
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExcelTaskServiceImpl extends ServiceImpl<ExcelTaskMapper, ExcelTask> implements IExcelTaskService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void joinExcelTaskQueue(String excelTaskJsonStr, String redisKey) {
        stringRedisTemplate.opsForList().rightPush(redisKey, excelTaskJsonStr);
    }

    @Override
    public void notice(IdEvt evt) {
        ExcelTask excelTask = this.getById(evt.getId());
        if (excelTask.getStatus().equals(2)) {
            log.info("excelTaskId:{} 状态已完成", excelTask.getId());
            return;
        }
        ExcelTaskTypeEnum enumByType = ExcelTaskTypeEnum.getEnumByType(excelTask.getExcelType());
        String redisKey = Constant.CacheConstants.NORMAL_EXCEL_TASK;
        if (enumByType.getQueueEnum().equals(ExcelTaskTypeEnum.ExcelQueueTypeEnum.SLOW)) {
            redisKey = Constant.CacheConstants.SLOW_EXCEL_TASK;
        }
        this.joinExcelTaskQueue(JSON.toJSONString(excelTask), redisKey);
    }

}
