package com.alex10011.example.service;

import java.util.List;
import java.util.Map;

import com.alex10011.example.domain.SysUser;
import com.alex10011.example.domain.SysUser;

//采用mybatis演示业务
public interface UserService {

	public List<SysUser> query(Map<String, Object> param);

	public SysUser getUserByName(String name);
	
	public SysUser getUserById(int id);

}
