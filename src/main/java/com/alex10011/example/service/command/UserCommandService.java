package com.alex10011.example.service.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.alex10011.example.domain.SysUser;
import com.alex10011.example.vo.RspBean;

// 调用关联系统的接口基于hystrix做熔断保护和降级处理，避免雪崩
@Service
public class UserCommandService {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(commandKey = "getUserByName", fallbackMethod = "getUserByNameFallback")
	public SysUser getUserByName(String name1, String name2) {
		String url = "http://127.0.0.1:8000/getUserByName?name1={name1}&name2={name2}";
		Map<String, String> param = new HashMap<String, String>();
		param.put("name1", name1);
		param.put("name2", name1);

		TypeReference<RspBean<SysUser>> type = new TypeReference<RspBean<SysUser>>() {
		};

		return RestTemplateUtil.getRestData(restTemplate, url, param, type);
	}

	// 降级方法
	public SysUser getUserByNameFallback(String name1, String name2) {
		System.out.println("触发熔断，降级响应");
		return new SysUser();
	}
}

//// ============= get目标接口及參數
// String url = "http://127.0.0.1:8000/getUserByName";
// Map<String, String> param = new HashMap<String, String>();
// param.put("name1", name);
// // 包裹体中data对应的对象类型
// TypeReference<RspBean<SysUser>> type = new TypeReference<RspBean<SysUser>>()
// {
// };
//
// // get调用demo1，得到返回包裹中的data对象
// SysUser result = RestTemplateUtil.getRestData(restTemplate, url, param,
// type);
// System.out.println("1===" + result);
//
// // get调用demo2，得到返回包裹
// RspBean<SysUser> result1 = RestTemplateUtil.getRest(restTemplate, url, param,
// type);
// System.out.println("2===" + result1);
//
// // ============= post目标接口及參數
// url = "http://127.0.0.1:8000/getUserByBo";
// param = new HashMap<String, String>();
// param.put("userName", name);
//
// // post调用demo3，得到返回包裹中的data对象
// SysUser result3 = RestTemplateUtil.postRestData(restTemplate, url, param,
// type);
// System.out.println("3===" + result3);
//
// // post 调用demo4，得到返回包裹
// RspBean<SysUser> result4 = RestTemplateUtil.postRest(restTemplate, url,
// param, type);
// System.out.println("4===" + result4);