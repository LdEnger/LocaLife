package test.java.com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hiveview.service.api.VipOrderApi;
import com.hiveview.test.base.BaseTest;

public class VipOrderServiceTest extends BaseTest {

	@Autowired
	VipOrderApi vipOrderService;

	String mac = "14:3d:f2:2c:16:05";
	String sn = "DMA30204150174336";


//	@Test
	public void updateOrderTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderType", "2");
		map.put("userId", "123456");
		map.put("mac", mac);
		map.put("sn", sn);
		map.put("userName", "");
		map.put("devicecode", "");
		map.put("chargingId", "11111");
		map.put("chargingName", "测试");
		map.put("chargingPrice", "10");
		map.put("chargingDuration", "100");
		System.out.println(vipOrderService.orderActivation(map));
	}
}
