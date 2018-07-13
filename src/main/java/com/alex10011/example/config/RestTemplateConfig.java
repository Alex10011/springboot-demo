package com.alex10011.example.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	// 消费者只需要通过被 @LoadBalanced 注释的RestTemplate来实现面向服务的接口调用 (需要Eureka治理)
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
