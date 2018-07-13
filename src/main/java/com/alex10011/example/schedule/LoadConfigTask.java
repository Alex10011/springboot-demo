package com.alex10011.example.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alex10011.example.service.SysConfigService;


@Component
public class LoadConfigTask {
    private static final Logger logger = LoggerFactory.getLogger(LoadConfigTask.class);

    @Autowired
    SysConfigService sysConfigService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void initConfig() {
    	sysConfigService.initConfig();

    }

}
