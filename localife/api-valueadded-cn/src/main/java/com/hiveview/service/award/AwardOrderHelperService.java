package com.hiveview.service.award;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpQueryString;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.util.DateUtilThread;

@Service
public class AwardOrderHelperService {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	AwardOrderService awardOrderService;
	@Autowired
	private VipOrderApi vipOrderApi;

	public String giveVipTime(AwardDetail dl, AwardPlay ap, AwardOrder ao, Float price) {
		int duration = dl.getAwardVipDuration() * 24 * 60 * 60;
		String orderId = vipOrderApi.orderSubmit(ap.getMac(), ap.getSn(), Integer.valueOf(ap.getUserId()), ap.getDeviceCode(), 3, "", null);
		if (orderId.equals("error")) {
			DATA.info("赠送vip获取订单号失败,mac={},sn={},userId={},deviceCode={}", new Object[] { ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		if (orderId.equals("exist")) {
			DATA.info("赠送vip获取订单号失败,返回orderId={},order表orderId={}", new Object[] { orderId, ao.getOrderId() });
			return ParamConstants.SUC;
		}
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.activityOrderId = orderId;
		activityOrder.submitTime = DateUtilThread.date2Full(ao.getOrderTime());
		activityOrder.activityOrderType = 9;
		activityOrder.chargingId = -9;
		activityOrder.chargingName = "抽奖活动";
		activityOrder.chargingPrice = Integer.valueOf((price * 100) + "");
		activityOrder.chargingDuration = duration;
		String res = vipOrderApi.orderActivation(ap.getMac(), ap.getSn(), activityOrder);
		if (res.equals("error")) {
			// 赠送vip时长,orderid为vip下单获取,要修改默认生成的orderid
			ao.setOrderId(orderId);
			awardOrderService.update(ao);
			DATA.info("订单号为:" + orderId + "赠送vip失败");
			return ParamConstants.ERR;
		}
		ao.setOrderId(orderId);
		ao.setOrderStatus(1);// 已发送
		awardOrderService.update(ao);
		return ParamConstants.SUC;
	}

	public String giveTickets(String contentType, AwardPlay ap, AwardDetail dl, AwardOrder ao, Float price) {
		String albumId = dl.getAwardVideoId().toString();
		String url = new String(ApiConstants.ACT_PROD_SERIAL);
		url = url.replaceAll("contentId", albumId);
		url = url.replaceAll("contentType", contentType);
		HiveHttpResponse resp = HiveHttpGet.getEntity(url, HiveHttpEntityType.STRING);
		if (resp.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(resp.entityString)) {
			DATA.info("赠送首映,获取appendAttr失败,entityString={},mac={},sn={},userId={},deviceCode={}", new Object[] { resp.entityString, ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		String data = JSONObject.parseObject(resp.entityString).getString("data");
		if (StringUtils.isEmpty(data)) {
			DATA.info("赠送首映,获取appendAttr失败,entityString={},mac={},sn={},userId={},deviceCode={}", new Object[] { resp.entityString, ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		String code = JSONObject.parseObject(data).getString("code");
		if (StringUtils.isEmpty(code) || !code.equals("N000000")) {
			DATA.info("赠送首映,获取appendAttr失败,entityString={},mac={},sn={},userId={},deviceCode={}", new Object[] { resp.entityString, ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		String result = JSONObject.parseObject(data).getString("result");
		String appendAttr = JSONObject.parseObject(result).getString("appendAttr");
		String desc = "";
		desc = payOrder(ap, appendAttr, dl, ao, price);
		return desc;
	}

	public String payOrder(AwardPlay ap, String appendAttr, AwardDetail dl, AwardOrder ao, Float price) {
		String partnerRt = getPartnerResult(dl.getAwardVideoPartnerId());
		if (partnerRt.equals(ParamConstants.ERR)) {
			return ParamConstants.ERR;
		}
		String partnerKey = JSONObject.parseObject(partnerRt).getString("partnerKey");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", ao.getOrderId());//
		map.put("orderTime", DateUtilThread.date2Full(ao.getOrderTime()));//
		map.put("orderStatus", "2");//
		map.put("payType", "9");// 9--赠送
		map.put("cashAmt", "0");//
		map.put("mlAmt", "0");//
		map.put("productId", dl.getAwardVideoId().toString()); //
		map.put("productType", ParamConstants.PRD_TYPE_BULE);//
		map.put("productName", dl.getAwardVideoName());//
		map.put("chargingPrice", price.toString());//
		map.put("chargingDuration", "86400");//
		map.put("chargingDegree", "0");//
		map.put("userId", ap.getUserId());//
		map.put("userName", ap.getUserName()); //
		map.put("payTime", DateUtilThread.date2Full(ao.getOrderTime()));//
		map.put("payRealml", "0");//
		map.put("payFreeml", "0");//
		map.put("orderAppend", appendAttr);//
		map.put("mac", ap.getMac());//
		map.put("sn", ap.getSn());//
		map.put("devicecode", ap.getDeviceCode());//
		map.put("stopflag", "0");
		map.put("addTimeForStop", "0");
		map.put("partnerKey", partnerKey);
		String params = HiveHttpQueryString.buildQuery(map);
		params = params + "&partnerKey=" + partnerKey;
		byte[] paramsByte = null;
		try {
			paramsByte = params.getBytes("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		String sign = DigestUtils.md5Hex(paramsByte);
		params = params + "&sign=" + sign;
		try {
			params = URLEncoder.encode(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		String url = ApiConstants.ACT_PAY_ORDER;
		HiveHttpResponse resp = HiveHttpPost.postString(url, params, HiveHttpEntityType.STRING);
		if (resp.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(resp.entityString)) {
			DATA.info("赠送首映失败,url={},params={}, entityString={},mac={},sn={},userId={},deviceCode={}", new Object[] { url, params, resp.entityString, ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		String result = resp.entityString;
		if (!result.equals("success")) {
			DATA.info("赠送首映失败,url={},params={}, entityString={},mac={},sn={},userId={},deviceCode={}", new Object[] { url, params, resp.entityString, ap.getMac(), ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			return ParamConstants.ERR;
		}
		return ParamConstants.SUC;
	}

	/**
	 * 根据partnerId取partnerKey
	 * 
	 * @param partnerId
	 * @return
	 */
	public String getPartnerResult(String partnerId) {
		try {
			String partnerKeySign = ParamConstants.HIVE_PARTNER_KEY;
			String postToken = DigestUtils.md5Hex(partnerId + partnerKeySign);
			String url = ApiConstants.ACT_PAY_URL + "partner/partner/getPartner.json?partnerId=" + partnerId + "&token=" + postToken;
			HiveHttpResponse resp = HiveHttpPost.postString(url, "", HiveHttpEntityType.STRING);
			if (resp.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(resp.entityString)) {
				DATA.info("AwardOrderHelperService[getPartnerResult]错误,partnerId={},statusCode={},entityString={}", new Object[] { partnerId, resp.statusCode, resp.entityString });
				return ParamConstants.ERR;
			}
			String code = JSONObject.parseObject(resp.entityString).getString("code");
			if (StringUtils.isEmpty(code)) {
				DATA.info("AwardOrderHelperService[getPartnerResult]错误,partnerId={},statusCode={},entityString={}", new Object[] { partnerId, resp.statusCode, resp.entityString });
				return ParamConstants.ERR;
			}
			if (!code.equals("000")) {
				DATA.info("AwardOrderHelperService[getPartnerResult]错误,partnerId={},statusCode={},entityString={}", new Object[] { partnerId, resp.statusCode, resp.entityString });
				return ParamConstants.ERR;
			}
			String result = JSONObject.parseObject(resp.entityString).getString("result");
			if (StringUtils.isEmpty(result)) {
				DATA.info("AwardOrderHelperService[getPartnerResult]错误,partnerId={},statusCode={},entityString={}", new Object[] { partnerId, resp.statusCode, resp.entityString });
				return ParamConstants.ERR;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ParamConstants.ERR;
		}
	}
}
