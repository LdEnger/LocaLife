package test.java.com.hiveview.test.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.PassportApi;
import com.hiveview.test.base.BaseTest;

public class PassportApiTest extends BaseTest{
	
	@Autowired
	PassportApi passportApi;
	
	@Test
	public void getUserInfoTest(){
		//5C:C6:D0:E2:36:6D
		//DMA30102140100740
		//UserVo user = passportApi.getUserInfo("14:3D:F2:1d:93:f4","DMA30104140956028");
		//User user = passportApi.getUserInfo("5C:C6:D0:E2:36:6D","DMA30102140100740");
		UserVo user = passportApi.getUserInfo("143DF1F1176D","DMA11111150805998");
		System.out.println("UserVo=======>"+JSONObject.toJSONString(user));
	}
	
	//@Test
	public void getUserDevicecodeTest(){
		String url = "http://passport.pthv.gitv.tv/api/user/getUserInfo.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("mac", "5C:C6:D0:E2:36:6D");
		map.put("sn", "DMA30102140100740");
		map.put("userType", "1");
		map.put("version", "PB10014");
		String params = JSONObject.toJSONString(map);
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("params", params);
		map2.put("partnerid", "hiveview");
		String token = DigestUtils.md5Hex(params+"|"+"31871fa18f49742f95295ef7fe5d3550");
		map2.put("token", token);
		HiveHttpResponse response = HiveHttpPost.postMap(url, map2, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode+"       "+response.entityString);
	}
}
