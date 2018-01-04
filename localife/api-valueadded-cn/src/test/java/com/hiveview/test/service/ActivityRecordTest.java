package com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.pay.entity.bo.OpResult;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.activity.ActivityRecordService;
import com.hiveview.test.base.BaseTest;

public class ActivityRecordTest extends BaseTest{
	
	@Autowired
	ActivityRecordService service;
	
//	@Test
	public void getUserActivityTest(){
		//String url = "http://124.207.119.66:8014/activity/record/getUserActivity.json";
		String url = "http://api.activity.pthv.gitv.tv/record/getUserActivity.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("uid", "2551310");
		HiveHttpResponse response = HiveHttpPost.postMap(url, map, HiveHttpEntityType.STRING);
		System.out.println(response.entityString);
	}
	
	@Test
	public void getUserActivityByUidTest(){
		OpResult op= service.getUserActivity(2551310);
		String str= JSONObject.toJSONString(op.getResult());
		System.out.println(str);
	}
	
//	@Test
	public void clearAllRecordTest(){
		OpResult op= service.clearAllRecord("2551310","");
		String str= JSONObject.toJSONString(op.getResult());
		System.out.println(str);
	}
	
//	@Test
	public void delRecordTest(){
		OpResult op= service.delRecordByOrderId("3010044","820152161022192");
		String str= JSONObject.toJSONString(op.getResult());
		System.out.println(str);
	}
}
