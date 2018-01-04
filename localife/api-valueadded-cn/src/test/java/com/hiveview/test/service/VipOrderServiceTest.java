package com.hiveview.test.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.test.base.BaseTest;

public class VipOrderServiceTest extends BaseTest {

	@Autowired
	VipOrderApi vipOrderService;

	String mac = "14:3d:f2:2c:16:05";
	String sn = "DMA30204150174336";

//	 @Test
	public void saveOrderTest() {
		System.out.println(vipOrderService.orderSubmit(mac, sn, 1585387, "asdadadasd", 0, "", "11111"));
	}

//	@Test
	public void updateOrderTest() {
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.activityOrderId = "3116042018531161482";
		activityOrder.submitTime = "2016-04-19 12:18:00";
		activityOrder.activityOrderType = 0;
		activityOrder.chargingId = 11111;
		activityOrder.chargingName = "测试";
		activityOrder.chargingPrice = 10;
		activityOrder.chargingDuration = 00000;
		System.out.println(vipOrderService.orderActivation(mac, sn, activityOrder));
	}
}
