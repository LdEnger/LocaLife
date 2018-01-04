package main.java.com.hiveview.service.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.util.DMPayHelper;

@Service
public class VipOrderApi extends BaseAction{
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	RestTemplate restTemplate =new RestTemplate();
	
	/**
	 * 激活订单
	 * @param parametersMap
	 * @return
	 */
	public String orderActivation(Map<String, String> parametersMap) {
		if(parametersMap==null){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderType", parametersMap.get("orderType"));
		map.put("userId", parametersMap.get("userId"));
		map.put("mac", parametersMap.get("mac"));
		map.put("sn", parametersMap.get("sn"));
		map.put("userName", parametersMap.get("userName"));
		map.put("devicecode", parametersMap.get("devicecode"));
		map.put("chargingId", parametersMap.get("chargingId"));
		map.put("chargingName", parametersMap.get("chargingName"));
		map.put("chargingPrice", parametersMap.get("chargingPrice"));
		map.put("chargingDuration", parametersMap.get("chargingDuration"));
		map.put("chargingDegree", null);
		map.put("payType", "2");// 2固定值卡券
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.VIP_PARTNER_KEY);
		Long start = System.currentTimeMillis();
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_SUB_API_V3, link, HiveHttpEntityType.STRING);
		Long elapse = System.currentTimeMillis() - start;
		DATA.info("[orderActivation_new]statusLine={},requestElapse={},entityString={},requestString={}", new Object[] { response.statusLine, elapse, response.entityString, link });
		if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
			return "error";
		}
		JSONObject jsonObject = JSONObject.parseObject(response.entityString);
		if (jsonObject == null||StringUtils.isEmpty(jsonObject.getString("data"))) {
			return "error";
		}
		return jsonObject.getString("data");
	}
	
	
	//##########################################以下为老激活接口###############################
	//##########################################以下为老激活接口###############################
	//##########################################以下为老激活接口###############################
	
	/**
	 * 备注：
	 * 由于播控的saveOrderV3接口线上一直没有更新，
	 * 获取不到订单号，影响财务相关数据
	 * 为满足财务相关需求，暂时恢复使用老接口
	 * 待播控上线新版本后，废弃该接口
	 * 
	* @param mac
	* @param sn
	* @param uid
	* @param orderType 0免费包，1计费包，2活动促销卡
	* @return
	*/ 
	public String orderSubmit(String mac,String sn,int uid,String devicecode,int orderType,String chargingDuration,String chargingId){
		String url = ApiConstants.VIP_SUB_API;
		String dataResult = restTemplate.getForObject(url, String.class,orderType,uid,mac,sn,devicecode,"",chargingId);
		DATA.info("[submitParamApi]notifyResp:url={},dataResult={}",new Object[]{url,dataResult});
		if (StringUtils.isEmpty(dataResult)) {
			DATA.info("[submitParamApi] ResponseEntityError");
			return "error";
		}
		JSONObject jsonObject = JSONObject.parseObject(dataResult);
		if (jsonObject == null) {
			DATA.info("[submitParamApi] JSONObjectResultParseObjectError");
			return "error";
		}
		JSONObject jsonData = JSONObject.parseObject(jsonObject.getString("data"));
		if (jsonData == null) {
			DATA.info("[submitParamApi] JSONObjectDataParseObjectError");
			return "error";
		}
		if ("E000008".equals(jsonData.getString("code"))) {
			DATA.info("[submitParamApi]orderExist:uid={},mac={},sn={},devicecode={},chargingId={},result={}", new Object[] { uid, mac, sn, devicecode, chargingId, jsonData });
			return "exist";
		}
		String orderId ="";
		try {
			orderId = JSONObject.parseObject(jsonData.getString("result")).getString("orderId");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}
	
	/**
	* vip激活
	* @param mac
	* @param sn
	* @param activityOrder
	* @return
	*/ 
	public String orderActivation(String mac,String sn,ActivityOrder activityOrder){
		Map<String,String> map = new HashMap<String,String>();
		map.put("partnerOrderId", activityOrder.activityOrderId);
		map.put("orderStatus", "2");//支付成功
		map.put("payTime", activityOrder.submitTime);
		map.put("payType", activityOrder.activityOrderType+"");//2固定值卡券
		map.put("chargingId", activityOrder.chargingId+"");
		map.put("chargingName", activityOrder.chargingName);
		map.put("chargingPrice", activityOrder.chargingPrice+"");
		map.put("chargingDuration", activityOrder.chargingDuration+"");
		map.put("vsersion", "1.0");//版本号
		String link =  DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.VIP_PARTNER_KEY);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_ACTIVATION_API, link, HiveHttpEntityType.STRING);
		if(response.statusCode != HttpStatus.SC_OK){
			DATA.info("[activityParamApi]ResponseCodeError:statusCode={},partnerOrderId={},mac={},sn={}",new Object[]{response.statusCode,activityOrder.activityOrderId,mac,sn});
			return "error";
		}
		if (StringUtils.isEmpty(response.entityString)) {
			DATA.info("[activityParamApi]:ResponseEntityError:entityString={}",new Object[]{response.entityString});
			return "error";
		}
		DATA.info("[activityParamApi] success:partnerOrderId={},mac={},sn={},entityString={}",new Object[]{activityOrder.activityOrderId,mac,sn,response.entityString});
		return response.entityString;
	}
}
