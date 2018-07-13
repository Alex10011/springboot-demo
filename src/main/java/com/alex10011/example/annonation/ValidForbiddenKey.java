package com.alex10011.example.annonation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.alex10011.example.bo.validator.ForbiddenKeyValidator;
import com.alex10011.example.bo.validator.ForbiddenKeyValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
// 指定验证器
@Constraint(validatedBy = ForbiddenKeyValidator.class)
@Documented
public @interface ValidForbiddenKey {

	// 默认错误消息
	String message() default "含有关键字";

	// 分组
	Class<?>[] groups() default {};

	// 负载
	Class<? extends Payload>[] payload() default {};

	// 指定多个时使用
	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ValidForbiddenKey[] value();
	}
}
