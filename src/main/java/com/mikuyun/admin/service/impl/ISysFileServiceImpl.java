package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysFile;
import com.mikuyun.admin.mapper.SysFileMapper;
import com.mikuyun.admin.service.ISysFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2023-04-22
 */
@Service
@RequiredArgsConstructor
public class ISysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

}
