package com.alex10011.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 启动swagger注解
@EnableSwagger2
@SpringBootApplication
// dao扫包
@MapperScan(basePackages = "com.alex10011.example.dao")
// 扫包filter，servlet，listener
@ServletComponentScan
// 开启熔断器功能
@EnableCircuitBreaker
// 允许异步执行方法
@EnableAsync
// 启动缓存
@EnableCaching
// 启动定时任务
@EnableScheduling
public class SpringbootDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ  启动成功      ヾ(◍°∇°◍)ﾉﾞ");
	}
}
