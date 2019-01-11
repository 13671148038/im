package com.kaiyun.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class Arr {
	
	@Scheduled(cron="0/5 * * * * ?")
	public void taskTest(){
		System.out.println("dingshiren===============");
	}

}
