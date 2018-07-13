package com.alex10011.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//异步调用方法
@Component
public class AsyncTaskTest {

	@Async
	public void doSomething() throws Exception {
		Thread.sleep(10000);
		System.out.println("begin doSomthing....");
		Thread.sleep(10000);
		System.out.println("end doSomthing....");
	}
}
