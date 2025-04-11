package com.mikuyun.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.evt.IdEvt;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jiangQL
 * @since 2025/04/11 23:27
 */
public interface IExcelTaskService extends IService<ExcelTask> {

    /**
     * 加入excel任务队列
     *
     * @param excelTaskJsonStr excelTask json字符串
     * @param redisKey         redisKey
     */
    void joinExcelTaskQueue(String excelTaskJsonStr, String redisKey);

    /**
     * 通知导出
     *
     * @param evt id
     */
    void notice(IdEvt evt);

}
