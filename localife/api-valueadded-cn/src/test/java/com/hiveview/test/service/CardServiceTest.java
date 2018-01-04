package com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.card.CardService;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;

public class CardServiceTest extends BaseTest{

	@Autowired
	CardService cardService;
	
	@Test
	public void paramTest(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardPwd", "2016 958 1903 3881 9277");
		map.put("mac", "14:3d:f2:2c:16:03");
		map.put("sn", "DMA30204150174334");
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		System.out.println(link);
	}
	
	@Test
	public void cardActityTest(){
		String url = "http://api.activity.pthv.gitv.tv/card/activation.json";
		//String url = "http://localhost:8080/activity_20151125_init/card/activation.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardPwd", "2016 753 8474 9022 9917");//2016 753 8474 9022 9917     //2016 841 3167 7872 7834
		map.put("mac", "14:3D:F2:1d:93:f4");
		map.put("sn", "DMA30104140956028");
		String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		System.out.println(link);
		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode);
		System.out.println(response.entityString);
	}
	
	@Test
	public void activationCardTest(){  
		OpResult op = cardService.useCard("14:3d:f2:2c:16:03", "DMA30204150174334", "2016 471 1767 5583 6042");
		System.out.println(op.toString());
	}
}
