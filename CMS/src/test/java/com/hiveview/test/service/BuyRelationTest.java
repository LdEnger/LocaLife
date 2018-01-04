package test.java.com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;

public class BuyRelationTest extends BaseTest{

	@Test
	public void getRelationTest(){
		String url = "http://localhost:8080/activity_20151125_init/buyRelation/free.json";
		Map<String,String> map = new HashMap<String,String>();
		
//		String chargingId = parametersMap.get("chargingId");
//		String chargingName = parametersMap.get("chargingName");
//		String chargingPrice = parametersMap.get("chargingPrice");
//		String chargingDuration = parametersMap.get("chargingDuration");
//		String chargingImg = parametersMap.get("chargingImg");
//		String uid = parametersMap.get("uid");
//		String mac = parametersMap.get("mac");
//		String sn = parametersMap.get("sn");
//		String devicecode = parametersMap.get("devicecode");
		
		map.put("chargingId", "15");
		map.put("chargingName", "测试专用");
		map.put("chargingPrice", "56");
		map.put("chargingDuration", "684000");
		map.put("chargingImg", "http://www.baidu.com");
		map.put("uid", "10");
		map.put("mac", "bc:96:80:7f:ed:c8");
		map.put("sn", "DMA30101131200333");
		map.put("devicecode", "asdasdasd");
		String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
		System.out.println(response.entityString);
		
	}
	
}
