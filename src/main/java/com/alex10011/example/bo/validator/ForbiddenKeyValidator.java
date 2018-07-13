package com.alex10011.example.bo.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.alex10011.example.annonation.ValidForbiddenKey;

//验证器，关键字过滤，涉黄涉政
public class ForbiddenKeyValidator implements ConstraintValidator<ValidForbiddenKey, String> {
	private Set<String> forbiddenWords = new HashSet<String>();

	@Override
	public void initialize(ValidForbiddenKey constraintAnnotation) {
		// TODO 可以从数据量得到关键字列表加入过滤set中
		forbiddenWords.add("admin");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		for (String word : forbiddenWords) {
			if (value.contains(word)) {
				return false;// 验证失败
			}
		}
		return true;
	}
}