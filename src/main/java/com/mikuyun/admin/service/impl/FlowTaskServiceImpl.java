package com.mikuyun.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.FlowTask;
import com.mikuyun.admin.mapper.FlowTaskMapper;
import com.mikuyun.admin.service.IFlowTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 待办任务表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements IFlowTaskService {

}
