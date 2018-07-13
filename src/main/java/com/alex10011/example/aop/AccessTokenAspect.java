package com.alex10011.example.aop;

import javax.servlet.http.HttpServletRequest;

import com.alex10011.example.enums.RespEnum;
import com.alex10011.example.service.TokenService;
import com.alex10011.example.vo.RspBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alex10011.example.enums.RespEnum;
import com.alex10011.example.service.TokenService;
import com.alex10011.example.vo.RspBean;

//访问token检测
@Aspect
@Component
public class AccessTokenAspect {

	@Autowired
	private TokenService tokenService;

	@Around("@annotation(com.alex10011.example.annonation.AccessToken)")
	public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		boolean verify = tokenService.verifyToken(id, token);
		if (verify) {
			Object object = pjp.proceed(); // 执行连接点方法
			// 获取执行方法的参数

			return object;
		} else {
			return new RspBean(RespEnum.ERROR_BUSINESS_NOT_LOGIN);
		}
	}
}
