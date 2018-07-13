package com.alex10011.example.controller;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alex10011.example.domain.City;
import com.alex10011.example.vo.RspBean;

@RestController
public class RedisController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(RedisController.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping(value = "/testRedis", method = RequestMethod.GET)
	public RspBean<String> testRedis() {
		City city = new City();
		city.setId(1l);
		city.setCityName("cityName");

		String key = "one-ctiy";

		ValueOperations<String, City> operations = redisTemplate.opsForValue();
		ValueOperations<String, String> operations2 = stringRedisTemplate.opsForValue();

		log.info("redisTemplate=============");
		operations.set(key, city, 60, TimeUnit.SECONDS);
		log.info("redisTemplate提取key=" + key + ",value = " + operations.get(key));
		stringRedisTemplate.delete(key);
		log.info("stringRedisTemplate删除key之后,value = " + operations.get(key));
		operations.set(key, city, 60, TimeUnit.SECONDS);
		redisTemplate.delete(key);
		log.info("redisTemplate删除key之后,value = " + operations.get(key));

		log.info("stringRedisTemplate=============");
		operations2.set(key, "hello stringRedisTemplate", 60, TimeUnit.SECONDS);
		log.info("stringRedisTemplate提取key=" + key + ",value = " + operations2.get(key));
		redisTemplate.delete(key);
		log.info("redisTemplate删除key之后,value = " + operations2.get(key));
		operations2.set(key, "hello stringRedisTemplate", 60, TimeUnit.SECONDS);
		stringRedisTemplate.delete(key);
		log.info("stringRedisTemplate删除key之后,value = " + operations2.get(key));

		return success("");
	}

}
