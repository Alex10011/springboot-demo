package com.alex10011.example.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.pagehelper.PageHelper;

@Configuration
public class MybatisConfig {
	@Bean
	public PageHelper pageHelper() {
		// mybatis分页插件
		PageHelper pageHelper = new PageHelper();
		// 添加配置，也可以指定文件路径
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}
}
