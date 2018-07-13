package com.alex10011.example.startup;

import com.alex10011.example.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alex10011.example.service.SysConfigService;

//在项目服务启动的时候就去加载一些数据或做一些事情
@Component
@Order(value = 2) // 第二个被执行
public class LoadOnStartup implements CommandLineRunner {

	@Autowired
    SysConfigService sysConfigService;

	@Override
	public void run(String... args) throws Exception {
		sysConfigService.initConfig();

		// do something
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
	}

}
