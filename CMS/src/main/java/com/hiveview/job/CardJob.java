package main.java.com.hiveview.job;/*
package com.hiveview.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hiveview.entity.bo.Data;
import com.hiveview.service.card.CardService;

*/
/**
 * Title：CardJob.java
 * Description：定时任务
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年12月24日 上午10:48:10
 *//*

@Component
public class CardJob {
	
	@Autowired
	private CardService cardService;
	
	private Logger DATA = LoggerFactory.getLogger("data");
	
	*/
/**
	 * 卡券自动失效
	 * 每天凌晨
	 *//*

	@Scheduled(cron = "0 0 0 * * ?")
	public void autoInvalidRun() {
		Data rst = cardService.autoInvalidCardService();
		DATA.info("#################定时任务(卡券自动失效)#######################");
		DATA.info(rst.getMsg());
		DATA.info("###########################################################");
	}
	*/
/**
	 * 概述：处理boss任务
	 * 返回值：void
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-18下午5:58:33
	 *//*

	@Scheduled(cron="0 0 3 * * ?")
//	@Scheduled(cron="59 * * * * ?")
	public void autoBossTask(){
		DATA.info("#################boss任务处理开始#######################");
		System.out.println("boss任务处理开始");
		int flag =cardService.moveFile();
		DATA.info("#################boss任务处理开始，有"+flag+"文件更新#######################");
		if(flag>0){
			//有文件，则进行当日的调度任务
			cardService.doBossTask(1,"");
			//自动处理订单类数据
			cardService.doBossTask(2,"");
		}
		
	}
}
*/
