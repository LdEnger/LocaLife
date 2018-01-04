package com.hiveview.service.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.util.DMPayHelper;

@Service
public class LiveApi {

	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	public String openLiveService(SysUser sysUser,UserVo user,VipPackagePriceVo packagePrice,String liveOrderId){
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("mac", user.getMac());
		map.put("sn", user.getSn());
		map.put("orderNumber", liveOrderId);
		map.put("userId", user.getId()+"");
		map.put("deviceCode", user.getDevicecode());
		map.put("proCode", sysUser.getProvinceCode());
		map.put("proName", sysUser.getProvinceName());
		map.put("cityCode", sysUser.getCityCode());
		map.put("cityName", sysUser.getCityName());
		map.put("chargingId", packagePrice.chargingId+"");
		//map.put("chargingId", ""+(new Random()).nextInt(1000));
		map.put("chargingPrice", packagePrice.chargingPrice+"");
		map.put("time", packagePrice.chargingDuration+"");
		map.put("chargingName", packagePrice.chargingName);
		map.put("productId", packagePrice.productId+"");
		//map.put("productId", (new Random()).nextInt(1000)+"");
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.LIVE_OPEN_API, link, HiveHttpEntityType.STRING);
		DATA.info("[LiveResp] notifyResp:statusCode={},dataResult={},link={}",new Object[]{response.statusCode,response.entityString,link});
		if(response.statusCode != HttpStatus.SC_OK){
			return "通知开通失败";
		}
		if (StringUtils.isEmpty(response.entityString)) {
			return "通知开通失败";
		}
		String result = JSONObject.parseObject(response.entityString).getString("success");
		if(!"true".equals(result)){
			return JSONObject.parseObject(response.entityString).getString("message");
		}
		return "success";
	}
}
