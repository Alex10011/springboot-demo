package com.alex10011.example.annonation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.alex10011.example.bo.validator.CronValidator;
import com.alex10011.example.bo.validator.CronValidator;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
// 指定验证器
@Constraint(validatedBy = CronValidator.class)
@Documented
public @interface ValidCron {

	// 默认错误消息
	String message() default "CRON表达式非法";

	// 分组
	Class<?>[] groups() default {};

	// 负载
	Class<? extends Payload>[] payload() default {};

	// 指定多个时使用
	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ValidCron[] value();
	}
}
