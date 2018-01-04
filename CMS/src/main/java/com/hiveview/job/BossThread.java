package main.java.com.hiveview.job;/*
package com.hiveview.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.service.card.CardService;

*/
/**
 * Title：
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-4-7上午9:38:35
 *//*

public class BossThread{
	@Autowired
	private CardService cardservice;
	public String doBatchJob(){
		String result = "开始执行";
		if(BossThreadMeta.flag==0){
			BossThreadMeta.msg.clear();
			BossThreadMeta.flag =1;
			BossThreadMeta.beginTime =new Date();
			//FIXME 批量开卡逻辑
		}else{
			result ="有任务正在执行";
		}
		return result;
    }
}
*/
