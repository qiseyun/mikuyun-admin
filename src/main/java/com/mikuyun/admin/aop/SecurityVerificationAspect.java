package com.mikuyun.admin.aop;

import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author mikuyun
 * @since 2026/4/2 23:04
 */
@Aspect
@Component
@Slf4j
public class SecurityVerificationAspect {

    @Value("${mikuyun.innerToken}")
    private String innerToken;

    /**
     * 定义 @Pointcut注解表达式, 通过特定的规则来筛选连接点, 就是Pointcut，选中那几个你想要的方法
     * 在程序中主要体现为书写切入点表达式（通过通配、正则表达式）过滤出特定的一组 JointPoint连接点
     * 方式一：@annotation：当执行的方法上拥有指定的注解时生效（本博客采用这）
     * 方式二：execution：一般用于指定方法的执行
     */
    @Pointcut("@annotation(com.mikuyun.admin.annotation.SecurityVerification)")
    public void pointCutSecurityVerification() {

    }

    /**
     * 环绕通知, 围绕着方法执行
     *
     * @param joinPoint 连接点
     * @return Object
     */
    @Around(value = "pointCutSecurityVerification()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (Objects.equals(innerToken, request.getHeader("access_token"))) {
            return joinPoint.proceed();
        }
        throw new ServiceException(ResultCode.ACCESS_TOKEN_ERROR);
    }

}
