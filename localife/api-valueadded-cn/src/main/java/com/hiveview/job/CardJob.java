package com.hiveview.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hiveview.entity.bo.Data;
import com.hiveview.service.card.CardService;

/**
 * Title：CardJob.java
 * Description：过期处理相关任务
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年12月24日 上午10:48:10
 */
@Component
public class CardJob {
	
	@Autowired
	CardService cardService;
	
	private Logger DATA = LoggerFactory.getLogger("data");
	
	
	/**
	 * 查询卡券过期
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void expiredRun() {
		Data rst = cardService.expiredActivityRun();
		DATA.info(rst.getMsg());
	}

}
