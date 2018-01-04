package com.hiveview.service.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.RedisService;

@Service
public class PassportApi {

	@Autowired
	RedisService redisService;
	
	private String USER_INFO_KEY_PREFIX = "pay_uid_";
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	/**
	 * 获取指定设备对应的用户信息
	 * @param mac
	 * @param sn
	 * @return
	 */
	public UserVo getUserInfo(String mac, String sn) {
		String key = USER_INFO_KEY_PREFIX + mac + "_" + sn;
		if (redisService.exists(key)) {
			UserVo userVo= redisService.get(key,UserVo.class);
			if (userVo != null) {
				return userVo;
			}
		}
		synchronized (mac) {
			if (redisService.exists(key)) {
				UserVo userVo= redisService.get(key,UserVo.class);
				if (userVo != null) {
					return userVo;
				}
			}
			try {
				String body = this.getUserInfoHttp(mac, sn);
				if (StringUtils.isEmpty(body)) {
					return null;
				}
				JSONObject data = JSONObject.parseObject(body);
				if (data == null || StringUtils.isEmpty(data.getString("result"))) {
					return null;
				}
				JSONObject result = JSONObject.parseObject(data.getString("result"));
				UserVo user = new UserVo(result.getIntValue("id"),result.getString("userAccount"),result.getString("userAccount")
						,mac,sn,result.getString("userCode"),result.getIntValue("walletStatus"));
				redisService.set(key, user, 1 * 180L,UserVo.class);//缓存三分钟
				return user;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	
	String getUserInfoHttp(String mac, String sn) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("mac", mac);
		paramsMap.put("sn", sn);
		paramsMap.put("userType", "1");
		paramsMap.put("version", "PB10014");
		String params = JSONObject.toJSONString(paramsMap);
		Map<String,String> map = new HashMap<String,String>();
		map.put("params", params);
		map.put("partnerid", ParamConstants.HIVE_PARTNER_ID);
		String token = DigestUtils.md5Hex(params+"|"+ParamConstants.HIVE_PARTNER_KEY);
		map.put("token", token);
		HiveHttpResponse hiveHttpResponse = HiveHttpPost.postMap(ApiConstants.USER_API, map,HiveHttpEntityType.STRING);
		if(hiveHttpResponse.statusCode!=HttpStatus.SC_OK){
			DATA.info("[getUserInfo] error:mac={},sn={}",new Object[]{mac,sn});
			return "error";
		}else{
			return hiveHttpResponse.entityString;
		}
	}
	
}
