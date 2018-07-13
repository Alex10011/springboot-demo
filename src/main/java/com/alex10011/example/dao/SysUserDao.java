package com.alex10011.example.dao;

import java.util.List;
import java.util.Map;

import com.alex10011.example.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.alex10011.example.domain.SysUser;

public interface SysUserDao {
	public List<SysUser> query(Map<String, Object> param);

	public List<SysUser> getUserById(Integer id);

	@Select("SELECT * FROM sys_user WHERE NAME = #{name}")
	public List<SysUser> getUserByName(@Param("name") String name);
}
