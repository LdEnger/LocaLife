package com.hiveview.test.api;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;

public class LiveApiTest extends BaseTest{

	//直播开通失败查询接口
	@Test
	public void selectOrderTest(){
		String url = "http://api.newyunping.pthv.gitv.tv/api/open/live/userInfo/selectOrder.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("orderNumber","1001201603041140138981034");
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode);
		System.out.println(response.entityString);
	}
	
}
