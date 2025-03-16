package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.Captcha;
import com.mikuyun.admin.mapper.CaptchaMapper;
import com.mikuyun.admin.service.CaptchaService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 验证码表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2023-04-16
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha> implements CaptchaService {

}
