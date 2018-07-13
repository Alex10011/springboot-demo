package com.alex10011.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alex10011.example.domain.SysUser;
import com.alex10011.example.service.command.UserCommandService;
import com.alex10011.example.vo.RspBean;

import io.swagger.annotations.ApiOperation;

//使用RestTemplate来消费Restful服务
//当前示例是脱离eureka治理的微服务，所以演示的是直接调用服务方接口
@RestController
public class DemoClientController extends BaseController {
	@Autowired
	private UserCommandService userCommandService;

	@ApiOperation(value = "根据名字查用户", notes = "调用第三方rest接口")
	@RequestMapping(value = "/reqRestFull", method = RequestMethod.GET)
	public RspBean<SysUser> reqRestFull(@RequestParam(value = "name", required = true) String name) {

		SysUser result = userCommandService.getUserByName(name, "");
		return success(result);

	}

	@ApiOperation(value = "根据名字查用户", notes = "调用第三方webservice接口")
	@RequestMapping(value = "/reqWebservice", method = RequestMethod.GET)
	public RspBean<SysUser> reqWebservice(@RequestParam(value = "name", required = true) String name) {

		SysUser result = userCommandService.getUserByName(name, "");
		return success(result);

	}

}
