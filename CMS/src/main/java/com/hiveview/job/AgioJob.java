package main.java.com.hiveview.job;/*
package com.hiveview.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hiveview.service.card.CardService;

*/
/**
 * Title：
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-1-4下午8:58:42
 *//*

@Component
public class AgioJob {
	@Autowired
	private CardService cardService;
	
	private Logger DATA = LoggerFactory.getLogger("data");
	
	@Scheduled(cron="0 0/15 * * * ?")
//	@Scheduled(cron="20 * * * * ?")
	public void doAgioBacthTask(){
		if(AgioJobMeta.flag==0){
			String result = cardService.doAgioBacthTask();
			System.out.println(result);
			DATA.debug(result);
		}else{
			System.out.println("上一次任务未执行完成:"+AgioJobMeta.beginTime+"本次任务不执行");
			DATA.debug("上一次任务未执行完成:"+AgioJobMeta.beginTime+"本次任务不执行");
		}
	}
}
*/
