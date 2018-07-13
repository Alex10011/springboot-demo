package com.alex10011.example.service;

public interface TokenService {
	public void creatToken(String openId);

	public boolean verifyToken(String openId, String token);
}
