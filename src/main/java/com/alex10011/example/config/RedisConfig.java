package com.alex10011.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(connectionFactory);

		// 把RedisTemplate和StringRedisTemplate
		// 就算可以相同也不能互相删除，因为采用的key序列化方法不一样，此处统一将两个模版的key序列方法统一
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		// 值保持原来各自的序列化方法
		// template.setHashValueSerializer(new JdkSerializationRedisSerializer());
		// template.setValueSerializer(new JdkSerializationRedisSerializer());

		template.afterPropertiesSet();

		return template;
	}

}
