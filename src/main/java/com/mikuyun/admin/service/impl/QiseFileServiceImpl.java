package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.QiseFile;
import com.mikuyun.admin.mapper.QiseFileMapper;
import com.mikuyun.admin.service.QiseFileService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class QiseFileServiceImpl extends ServiceImpl<QiseFileMapper, QiseFile> implements QiseFileService {

}
