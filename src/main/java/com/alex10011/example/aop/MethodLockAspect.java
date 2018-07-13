package com.alex10011.example.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.alex10011.example.annonation.MethodLock;
import com.alex10011.example.enums.RespEnum;
import com.alex10011.example.vo.RspBean;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alex10011.example.annonation.MethodLock;
import com.alex10011.example.enums.RespEnum;
import com.alex10011.example.vo.RspBean;

/**
 * 用于保护目标controller方法在执行过程中重复提交不被重复执行
 * 场景：如果一段逻辑正在处理订单的某流程，而用户等不及又重复提交请求，基于redis加锁可防止其他机器响应处理
 *
 * @author alex10011
 */
@Aspect
@Component
public class MethodLockAspect {
    private final Logger log = LoggerFactory.getLogger(MethodLockAspect.class);
    private static String suffix = "mlock";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.alex10011.example.annonation.MethodLock)")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        Class<?> classTarget = pjp.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);

        MethodLock ml = objMethod.getAnnotation(MethodLock.class);
        if (ml == null) {
            return pjp.proceed();
        }

        int lockSeconds = ml.lockSeconds();
        String key = ml.key();
        if (StringUtils.isBlank(key)) {
            return pjp.proceed();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        if (key.startsWith("request.")) {
            // 从请求中获取约定参数
            String param = key.substring(key.indexOf("request.") + "request.".length());
            key = request.getParameter(param);
        }

        key = suffix + "_" + className + "_" + methodName + "_" + key;

        String value = stringRedisTemplate.opsForValue().get(key);
        if (value != null) {
            log.info("重复请求----");
            // 重复请求
            return new RspBean(RespEnum.ERROR_REQ_AGAIN);
        } else {
            log.info("加锁----" + key);
            // 加锁
            stringRedisTemplate.opsForValue().set(key, "1", 60 * 10, TimeUnit.SECONDS);
            // 执行
            Object result = pjp.proceed();
            // 释放锁
            stringRedisTemplate.delete(key);

            return result;
        }

    }
}
