package com.kaiyun.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WebTask {
	
	
	//@Scheduled(cron="0/5 * * * * ?")
	public void taskJob(){
		System.out.println("sdsdsd===============");
	}

}
