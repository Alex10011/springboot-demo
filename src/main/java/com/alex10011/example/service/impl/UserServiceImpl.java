package com.alex10011.example.service.impl;

import java.util.List;
import java.util.Map;

import com.alex10011.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex10011.example.dao.SysUserDao;
import com.alex10011.example.domain.SysUser;
import com.alex10011.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private SysUserDao dao;

	@Override
	public List<SysUser> query(Map<String, Object> param) {
		return dao.query(param);
	}

	@Override
	
	public SysUser getUserByName(String name) {
		List<SysUser> list = dao.getUserByName(name);
		
		System.out.println("查询、、、、、、、、、、、、、");
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public SysUser getUserById(int id) {
		List<SysUser> list = dao.getUserById(id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
