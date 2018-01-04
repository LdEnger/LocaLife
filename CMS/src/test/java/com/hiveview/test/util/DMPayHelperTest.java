package test.java.com.hiveview.test.util;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.util.DMPayHelper;

public class DMPayHelperTest {

	@Test
	public void toLinkForNotifyTest(){
		Map<String,String> map = new HashMap<String,String>();
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
		System.out.println(link);
		System.out.println(ParamConstants.VIP_PARTNER_KEY);
	}
}
