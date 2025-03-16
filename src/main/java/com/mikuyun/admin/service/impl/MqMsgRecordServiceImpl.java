package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.MqMsgRecord;
import com.mikuyun.admin.mapper.MqMsgRecordMapper;
import com.mikuyun.admin.service.MqMsgRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2023-09-05
 */
@Service
public class MqMsgRecordServiceImpl extends ServiceImpl<MqMsgRecordMapper, MqMsgRecord> implements MqMsgRecordService {

    @Override
    public void saveMassage(MqMsgRecord mqMsgRecord) {
        this.save(mqMsgRecord);
    }

    @Override
    public void updateMqMsgStatus(MqMsgRecord mqMsgRecord) {
        mqMsgRecord.setStatus(1);
        this.updateById(mqMsgRecord);
    }

}
