package com.hiveview.test.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;

public class VipApiTest extends BaseTest {
	
	@Autowired
	VipOrderApi vipOrderApi;

	// @Test
	public void saveOrderUrl() {
		String url = "http://125.39.118.61:9997/api/open/vip/order/saveOrder/2-1221s2-20:20:89:40:40:40-as65231552-zxcqerl-65535-1.0.json";
		HiveHttpResponse response = HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
		System.out.println(response.entityString);
	}

	// @Test
	public void activationOrderUrl() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("partnerOrderId", "2201512041706526");
		map.put("orderStatus", "2");
		map.put("payTime", "2015-11-11 00:00:00");
		map.put("payType", "2");
		map.put("chargingId", "1001");
		map.put("chargingName", "计费半年包");
		map.put("chargingPrice", "6000");
		map.put("chargingDuration", "648000");
		map.put("vsersion", "1.0");
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_ACTIVATION_API, link, HiveHttpEntityType.STRING);
		String data = JSONObject.parseObject(JSONObject.parseObject(response.entityString).getString("data")).getString("result");
		System.out.println(data);
		System.out.println("url---->" + ApiConstants.VIP_ACTIVATION_API);
		System.out.println("link---->" + link);
		System.out.println("key---->" + ParamConstants.VIP_PARTNER_KEY);
		System.out.println("statusCode---->" + response.statusCode);
		System.out.println("entityString---->" + response.entityString);
	}

//	@Test
	public void freeBuyVipSaveOrder() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderType", "0");
		map.put("userId", "1000");
		map.put("userName", "userName");
		map.put("mac", "bc:96:80:7f:ed:c8");
		map.put("sn", "DMA30101131200333");
		map.put("devicecode", "asdasdasd");
		map.put("chargingId", "15");
		map.put("chargingName", "测试");
		map.put("chargingPrice", "0");
		map.put("chargingDuration", "684000");
		map.put("chargingDegree", null);
		map.put("payType", "2");// 2固定值卡券
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.VIP_PARTNER_KEY);
		// HiveHttpResponse response =
		// HiveHttpGet.getEntity(ApiConstants.VIP_SUB_API_V3 +"?"+ link,
		// HiveHttpEntityType.STRING);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_SUB_API_V3, link, HiveHttpEntityType.STRING);
		System.out.println("result=>" + response.entityString);
	}

	@Test
	public void vipOrderApi() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderType", "0");
		map.put("uid", "1000");
		map.put("userName", "userName");
		map.put("mac", "bc:96:80:7f:ed:c8");
		map.put("sn", "DMA30101131200333");
		map.put("devicecode", "asdasdasd");
		map.put("chargingId", "16");
		map.put("chargingName", "测试");
		map.put("chargingPrice", "0");
		map.put("chargingDuration", "684000");
		map.put("chargingDegree", null);
		map.put("payType", "2");// 2固定值卡券
		String result = vipOrderApi.freeBuyVipSaveOrder(map);
		System.out.println("result=>" + result);
	}
}
