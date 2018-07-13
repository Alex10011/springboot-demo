package com.alex10011.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//使用原生ui 访问 http://localhost:8000/swagger-ui.html 即可访问swagger页面
//使用bootstrap-ui后 访问 http://localhost:8000/doc.html ，即可访问swagger页面
@Configuration
// 启用Swagger2
@EnableSwagger2
public class Swagger2Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.alex10011.example.controller")).paths(PathSelectors.any())
				.build();

		// .ignoredParameterTypes(User.class)
	}

	// 创建该Api的基本信息
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("这是xx项目的xx服务接口说明").description("更多问题请访问：http://xxx.com/").version("1.0")
				.build();
	}
}
