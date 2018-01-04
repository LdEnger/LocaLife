package com.hiveview.service.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.util.DMPayHelper;

@Service
public class VipOrderApi {

	private static final Logger DATA = LoggerFactory.getLogger("data");
	RestTemplate restTemplate = new RestTemplate();

	/**
	 * vip下单
	 * 
	 * @param mac
	 * @param sn
	 * @param uid
	 * @param orderType
	 *            0免费包，1计费包，2活动促销款
	 * @return
	 */
	public String orderSubmit(String mac, String sn, int uid, String devicecode, int orderType, String chargingDuration, String chargingId) {
		String url = ApiConstants.VIP_SUB_API;
		String dataResult = restTemplate.getForObject(url, String.class, orderType, uid, mac, sn, devicecode, "", chargingId);
		DATA.info("[submitParamApi]notifyResp:url={},dataResult={}", new Object[] { url, dataResult });
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
		String partnerOrderId = JSONObject.parseObject(jsonData.getString("result")).getString("orderId");
		return partnerOrderId;
	}

	/**
	 * vip激活
	 * 
	 * @param mac
	 * @param sn
	 * @param activityOrder
	 * @return
	 */
	public String orderActivation(String mac, String sn, ActivityOrder activityOrder) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("partnerOrderId", activityOrder.activityOrderId);
		map.put("orderStatus", "2");// 支付成功
		map.put("payTime", activityOrder.submitTime);
		map.put("payType", activityOrder.activityOrderType + "");// 2固定值卡券
		map.put("chargingId", activityOrder.chargingId + "");
		map.put("chargingName", activityOrder.chargingName);
		map.put("chargingPrice", activityOrder.chargingPrice + "");
		map.put("chargingDuration", activityOrder.chargingDuration + "");
		map.put("vsersion", "1.0");// 版本号
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.VIP_PARTNER_KEY);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_ACTIVATION_API, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK) {
			DATA.info("[activityParamApi]ResponseCodeError:statusCode={},partnerOrderId={},url={},mac={},sn={}", new Object[] { response.statusCode, activityOrder.activityOrderId, ApiConstants.VIP_ACTIVATION_API, mac, sn });
			return "error";
		}
		if (StringUtils.isEmpty(response.entityString) || !"success".equals(response.entityString)) {
			DATA.info("[activityParamApi]:ResponseEntityError:entityString={},url={},mac={},sn={}", new Object[] { response.entityString, ApiConstants.VIP_ACTIVATION_API, mac, sn });
			return "error";
		}
		DATA.info("[activityParamApi] success:partnerOrderId={},mac={},sn={},entityString={}", new Object[] { activityOrder.activityOrderId, mac, sn, response.entityString });
		return response.entityString;
	}

	/**
	 * 调用播控为免费领取VIP下单
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月25日
	 * @param parametersMap
	 * @return
	 */
	public String freeBuyVipSaveOrder(Map<String, String> parametersMap) {
		String mac = parametersMap.get("mac");
		String sn = parametersMap.get("sn");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderType", parametersMap.get("orderType"));
		map.put("userId", parametersMap.get("uid"));
		map.put("mac", mac);
		map.put("sn", sn);
		map.put("userName", parametersMap.get("userName"));
		map.put("devicecode", parametersMap.get("devicecode"));
		map.put("chargingId", parametersMap.get("chargingId"));
		map.put("chargingName", parametersMap.get("chargingName"));
		map.put("chargingPrice", parametersMap.get("chargingPrice"));
		map.put("chargingDuration", parametersMap.get("chargingDuration"));
		map.put("chargingDegree", null);
		map.put("payType", "2");// 2固定值卡券
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.VIP_PARTNER_KEY);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.VIP_SUB_API_V3, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK) {
			DATA.info("[freeBuyVipSaveOrder]ResponseCodeError:statusCode={},param={}}", new Object[] { response.statusCode, map });
			return "error";
		}
		if (StringUtils.isEmpty(response.entityString)) {
			DATA.info("[freeBuyVipSaveOrder]:ResponseEntityError:mac={},sn={},entityString={}", new Object[] { mac, sn, response.entityString });
			return "error";
		}
		JSONObject jsonObject = JSONObject.parseObject(response.entityString);
		if (jsonObject == null) {
			DATA.info("[freeBuyVipSaveOrder] JSONObjectResultParseObjectIsNull");
			return "error";
		}
		JSONObject jsonData = JSONObject.parseObject(jsonObject.getString("data"));
		if (jsonData == null) {
			DATA.info("[freeBuyVipSaveOrder] JSONObjectDataParseObjectIsNull");
			return "error";
		}
		if ("E000008".equals(jsonData.getString("code"))) {
			DATA.info("[freeBuyVipSaveOrder]orderExist:parametersMap={},mac={},sn={},devicecode={},chargingId={},result={}", new Object[] { parametersMap.get("uid"), mac, sn, parametersMap.get("devicecode"), parametersMap.get("chargingId"), jsonData });
			return "exist";
		}
		if (!"true".equals(jsonData.getString("success"))) {
			DATA.info("[freeBuyVipSaveOrder] resultNoSuccess : result={}", new Object[] { jsonData.getString("message") });
			return "error";
		}
		String orderId = JSONObject.parseObject(jsonData.getString("result")).getString("orderId");
		return orderId;
	}
}
