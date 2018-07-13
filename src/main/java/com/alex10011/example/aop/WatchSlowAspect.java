package com.alex10011.example.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WatchSlowAspect {
	private Logger log = Logger.getLogger(getClass());
	private static int warrning = 3000;// 报警阀值，单位毫秒

	@Around("execution(* com.alex10011.example.controller..*(..))")
	public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
		long l = System.currentTimeMillis();
		Object object = pjp.proceed();

		long dt = System.currentTimeMillis() - l;

		if (dt > warrning) {
			String className = pjp.getTarget().getClass().getSimpleName();
			String methodName = pjp.getSignature().getName();
			// 触发报警，根据需要做动作，此处做一个日志
			log.warn("有个慢方法，请关注" + className + "." + methodName + "，执行耗时" + dt + "毫秒");
		}

		return object;
	}
}
