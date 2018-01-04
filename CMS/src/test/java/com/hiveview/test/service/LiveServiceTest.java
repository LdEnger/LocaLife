package test.java.com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;

public class LiveServiceTest extends BaseTest{

	@Test
	public void linkTest(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("mac", "aaaaaaa");
		map.put("sn", "bbbb");
		map.put("orderNumber", "cccccc");
		map.put("userId", "10086");
		map.put("deviceCode", "qqqq");
		map.put("proCode", "10");
		map.put("proName", "北京");
		map.put("cityCode", "1010");
		map.put("cityName", "北京市");
		map.put("chargingId", "1001");
		map.put("chargingPrice", "5000");
		map.put("Time", "2016-01-01 00:00:00");
		map.put("chargingName", "包年");
		map.put("productId", "25");
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.LIVE_TOKEN);
		System.out.println(link);
	}
}
