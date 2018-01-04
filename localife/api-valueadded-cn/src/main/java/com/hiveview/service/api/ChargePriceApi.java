package com.hiveview.service.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.RedisService;

@Service
public class ChargePriceApi {
	
	@Autowired
	RedisService redisService;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");

	public List<VipPackagePriceVo> getVipChargPriceList(){
		List<VipPackagePriceVo> list = null;
		String key = "VIP_CHARGE_LIST";
		if(redisService.exists(key)){
			list = redisService.lRange(key,0L,-1L, VipPackagePriceVo.class);
			if(list!=null){
				return list;
			}
		}
		synchronized (key) {
			if(redisService.exists(key)){
				list = redisService.lRange(key,0L,-1L, VipPackagePriceVo.class);
				if(list!=null){
					return list;
				}
			}
			HiveHttpResponse response= HiveHttpGet.getEntity(ApiConstants.CHARGE_LIST_API, HiveHttpEntityType.STRING);
			if(response.statusCode!=HttpStatus.SC_OK){
				DATA.info("获取vip计费信息失败");
				return null;
			}
			try{
				String result = JSONObject.parseObject(JSONObject.parseObject(response.entityString).getString("data")).getString("result");
				list = new ArrayList<VipPackagePriceVo>();
				JSONArray array = JSONObject.parseArray(result);
				Iterator<Object> iterator = array.iterator();
				while (iterator.hasNext()) {
					VipPackagePriceVo vipPackagePriceVo = new VipPackagePriceVo();
					JSONObject json= (JSONObject) iterator.next();
					JSONObject record = JSONObject.parseObject(json.getString("vipPackageContentPriceVo"));
					vipPackagePriceVo.productId = json.getIntValue("id");
					vipPackagePriceVo.productName = json.getString("name");
					vipPackagePriceVo.chargingId = record.getIntValue("pricePkgId");
					vipPackagePriceVo.chargingName = record.getString("name");
					vipPackagePriceVo.chargingPrice = record.getIntValue("price");
					vipPackagePriceVo.chargingDuration = record.getIntValue("expiryTime")/3600/24;
					vipPackagePriceVo.chargingPic = record.getString("pic");
					vipPackagePriceVo.chargingStr = vipPackagePriceVo.productId+"||"+vipPackagePriceVo.productName+"||"+vipPackagePriceVo.chargingId+"||"+vipPackagePriceVo.chargingName+"||"+vipPackagePriceVo.chargingPrice+"||"+vipPackagePriceVo.chargingDuration+"||"+vipPackagePriceVo.chargingPic;
					list.add(vipPackagePriceVo);
				}
				redisService.rPush(key, list, VipPackagePriceVo.class, 3*60L);
				return list;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	* 拉取直播计费的计费包
	* @return
	*/ 
	public List<VipPackagePriceVo> getLiveChargPriceList(){
		List<VipPackagePriceVo> list = new ArrayList<VipPackagePriceVo>(); 
		VipPackagePriceVo vipPackagePriceVo1 = new VipPackagePriceVo();
		vipPackagePriceVo1.productId = 10;
		vipPackagePriceVo1.productName = "直播包半年";
		vipPackagePriceVo1.chargingId = 1010;
		vipPackagePriceVo1.chargingName = "直播包半年";
		vipPackagePriceVo1.chargingPrice = 0;
		vipPackagePriceVo1.chargingDuration = 15897600;
		vipPackagePriceVo1.chargingPic = "www.baidu.com";
		vipPackagePriceVo1.chargingStr ="temp1";
		list.add(vipPackagePriceVo1);
		VipPackagePriceVo vipPackagePriceVo2 = new VipPackagePriceVo();
		vipPackagePriceVo2.productId = 20;
		vipPackagePriceVo2.productName = "直播包年";
		vipPackagePriceVo2.chargingId = 2020;
		vipPackagePriceVo2.chargingName = "直播包年";
		vipPackagePriceVo2.chargingPrice = 0;
		vipPackagePriceVo2.chargingDuration = 31622400;
		vipPackagePriceVo2.chargingPic = "www.baidu.com";
		vipPackagePriceVo2.chargingStr ="temp2";
		list.add(vipPackagePriceVo2);
		VipPackagePriceVo vipPackagePriceVo3 = new VipPackagePriceVo();
		vipPackagePriceVo3.productId = 30;
		vipPackagePriceVo3.productName = "直播包两年";
		vipPackagePriceVo3.chargingId = 3030;
		vipPackagePriceVo3.chargingName = "直播包两年";
		vipPackagePriceVo3.chargingPrice = 0;
		vipPackagePriceVo3.chargingDuration = 63244800;
		vipPackagePriceVo3.chargingPic = "www.baidu.com";
		vipPackagePriceVo3.chargingStr ="temp3";
		list.add(vipPackagePriceVo3);
		VipPackagePriceVo vipPackagePriceVo4 = new VipPackagePriceVo();
		vipPackagePriceVo4.productId = 40;
		vipPackagePriceVo4.productName = "直播包三年";
		vipPackagePriceVo4.chargingId = 4040;
		vipPackagePriceVo4.chargingName = "直播包三年";
		vipPackagePriceVo4.chargingPrice = 0;
		vipPackagePriceVo4.chargingDuration = 94867200;
		vipPackagePriceVo4.chargingPic = "www.baidu.com";
		vipPackagePriceVo4.chargingStr ="temp4";
		list.add(vipPackagePriceVo4);
		return list;
	}
	
	/**
	* 临时获取产品包，后期需确定直播计费包的配置再行设计
	* @param productId
	* @return
	*/ 
	public VipPackagePriceVo getPackagePriceByProductId(int productId){
		VipPackagePriceVo packagePrice = new VipPackagePriceVo();
		switch(productId){
			case 10:
				packagePrice.productId = 10;
				packagePrice.productName = "直播包半年";
				packagePrice.chargingId = 1010;
				packagePrice.chargingName = "直播包半年";
				packagePrice.chargingPrice = 0;
				packagePrice.chargingDuration = 15897600;
				packagePrice.chargingPic = "www.baidu.com";
				packagePrice.chargingStr ="temp1";
				break;
			case 20:
				packagePrice.productId = 20;
				packagePrice.productName = "直播包年";
				packagePrice.chargingId = 2020;
				packagePrice.chargingName = "直播包年";
				packagePrice.chargingPrice = 0;
				packagePrice.chargingDuration = 31622400;
				packagePrice.chargingPic = "www.baidu.com";
				packagePrice.chargingStr ="temp2";
				break;
			case 30:
				packagePrice.productId = 30;
				packagePrice.productName = "直播包两年";
				packagePrice.chargingId = 3030;
				packagePrice.chargingName = "直播包两年";
				packagePrice.chargingPrice = 0;
				packagePrice.chargingDuration = 63244800;
				packagePrice.chargingPic = "www.baidu.com";
				packagePrice.chargingStr ="temp3";
				break;
			case 40:
				packagePrice.productId = 40;
				packagePrice.productName = "直播包三年";
				packagePrice.chargingId = 4040;
				packagePrice.chargingName = "直播包三年";
				packagePrice.chargingPrice = 0;
				packagePrice.chargingDuration = 94867200;
				packagePrice.chargingPic = "www.baidu.com";
				packagePrice.chargingStr ="temp4";
				break;
		}
		return packagePrice;
	}
}
