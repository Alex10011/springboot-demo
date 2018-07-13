package com.alex10011.example.aop;

import javax.servlet.http.HttpServletRequest;

import com.alex10011.example.bo.Bo_Interface;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alex10011.example.bo.Bo_Interface;

@Aspect
@Component
public class WebLogAspect {
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 定义拦截规则：拦截com.alex10011.example.controller包下面的所有类中，有@RequestMapping注解的方法。
	 */
	@Pointcut("execution(* com.alex10011.example.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		StringBuilder stb = new StringBuilder();
		// IP
		stb.append(request.getRemoteAddr() + "|");
		// HTTP_METHOD
		stb.append(request.getMethod() + "|");
		// CLASS_METHOD
		stb.append(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "|");
		// 对象封装的参数进行日志，此处假设工程中都按约定使用bo方式传参
		Object[] args = joinPoint.getArgs();
		for (Object object : args) {
			if (object instanceof Bo_Interface) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				String req = mapper.writeValueAsString(object);
				stb.append(req);
			}
		}

		// 切面记录请求日志
		logger.info(stb.toString());
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String response = mapper.writeValueAsString(ret);

		// 切面记录返回内容
		logger.info("RESPONSE : " + response);
	}

}