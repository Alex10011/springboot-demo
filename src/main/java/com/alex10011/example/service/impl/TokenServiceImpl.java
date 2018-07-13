package com.alex10011.example.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.alex10011.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alex10011.example.service.TokenService;

//token登陆验证
@Service
public class TokenServiceImpl implements TokenService {
	private static final String prefix = "accessToken";
	private static final long timeout = 3600 * 24 * 30;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void creatToken(String openId) {
		String key = prefix + openId;
		String token = UUID.randomUUID().toString();
		ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
		redis.set(key, token, timeout, TimeUnit.SECONDS);
	}

	@Override
	public boolean verifyToken(String openId, String token) {
		if (openId == null || token == null) {
			return false;
		}

		String key = prefix + openId;
		ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
		String redisToken = redis.get(key);
		if (redisToken == null || !redisToken.equals(token)) {
			return false;
		}
		return true;
	}

}