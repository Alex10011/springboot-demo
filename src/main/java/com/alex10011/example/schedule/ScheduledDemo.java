package com.alex10011.example.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//使用@Scheduled方式的任务调度
@Component
public class ScheduledDemo {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledDemo.class);

	@Scheduled(cron = "0 55 * * * ?")
	public void executeFileDownLoadTask() {

		Thread current = Thread.currentThread();
		System.out.println("定时任务1:" + current.getId());
		logger.info("ScheduledDemo 定时任务1:" + current.getId() + ",name:" + current.getName());
	}
}
