package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.MqMsgRecord;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2023-09-05
 */
public interface MqMsgRecordService extends IService<MqMsgRecord> {

    /**
     * 新增mqMsg
     *
     * @param mqMsgRecord 新增参数
     */
    void saveMassage(MqMsgRecord mqMsgRecord);

    /**
     * 修改消费状态
     *
     * @param mqMsgRecord 参数
     */
    void updateMqMsgStatus(MqMsgRecord mqMsgRecord);

}
