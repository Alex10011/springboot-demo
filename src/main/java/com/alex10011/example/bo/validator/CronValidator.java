package com.alex10011.example.bo.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.quartz.CronExpression;
import org.springframework.util.StringUtils;

import com.alex10011.example.annonation.ValidCron;

// 验证是是合法的cron表达式
public class CronValidator implements ConstraintValidator<ValidCron, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}

		return cronTest(value);
	}

	private boolean cronTest(String cron) {
		try {
			CronExpression exp = new CronExpression(cron);
			SimpleDateFormat df = new SimpleDateFormat("YYYYMMDD HH:mm:ss");
			// 得到接下来n此的触发时间点，来验证表达式是否合法
			Date d = exp.getNextValidTimeAfter(new Date());
			System.out.println("下次执行的时间：" + df.format(d));
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	@Override
	public void initialize(ValidCron constraintAnnotation) {
	}

}
