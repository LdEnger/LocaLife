package com.hiveview.test.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.pay.util.DateUtils;
import com.hiveview.test.base.BaseTest;

public class LiveOrderDaoTest extends BaseTest {

	@Autowired
	LiveOrderDao dao;

	@Test
	public void insertLiveOrderDaoTest() {
		LiveOrder liveOrder = new LiveOrder();
		liveOrder.setLiveOrderId("aaaaaqqaaaaa");
		liveOrder.setStatus(2);
		liveOrder.setStatusName("已开通");
		liveOrder.setProductId(2001);
		liveOrder.setProductName("直播包年");
		liveOrder.setChargingId(10);
		liveOrder.setChargingName("直播计费");
		liveOrder.setChargingPrice(6500);
		liveOrder.setChargingDuration(86400);
		liveOrder.setChargingImg("aaaaqqaaaaaaaaaaa");
		liveOrder.setSubmitTime(DateUtils.now());
		liveOrder.setOpenTime(DateUtils.now());
		liveOrder.setCloseTime(DateUtils.now());
		liveOrder.setOpenuid(10010);
		liveOrder.setOpenname("一零零一零");
		liveOrder.setUid(10086);
		liveOrder.setUname("一零零八六");
		liveOrder.setMac("dx:as:As:10:w2:a4");
		liveOrder.setSn("DMS14513213213");
		liveOrder.setDevicecode("qwee4512sad");
		System.out.println(dao.save(liveOrder));
	}

	@Test
	public void getLivezList() {
		LiveOrder liveOrder = new LiveOrder();
		List<LiveOrder> result = dao.getLiveList(liveOrder);
		System.out.println("result=>" + result);
	}
	
	@Test
	public void updateLiveOrderByStstus(){
		System.out.println(dao.updateLiveOrderByStstus(2,"aaaaaaaaaa"));
	}
}
