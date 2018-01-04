package main.java.com.hiveview.service.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.util.DMPayHelper;

@Service
public class LiveApi {

	private static final Logger DATA = LoggerFactory.getLogger("data");

	/**
	 * 
	 * type(0为默认，1为乐享) startTime = null取当前时间
	 * 
	 * @param sysUser
	 * @param user
	 * @param packagePrice
	 * @param liveOrderId
	 * @param type
	 * @param startTime
	 * @return
	 */
	public String openLiveService(SysUser sysUser, UserVo user, LiveOrder liveOrder, String liveOrderId, String type, String startTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mac", user.getMac());
		map.put("sn", user.getSn());
		map.put("orderNumber", liveOrderId);
		map.put("userId", user.getId() + "");
		map.put("deviceCode", user.getDevicecode());
		map.put("proCode", sysUser.getProvinceCode());
		map.put("proName", sysUser.getProvinceName());
		map.put("cityCode", sysUser.getCityCode());
		map.put("cityName", sysUser.getCityName());
		map.put("productId", liveOrder.getProductId() + "");
		map.put("productName", liveOrder.getProductName());
		map.put("drtYear", liveOrder.getChargingDurationYear() + "");
		map.put("drtMonth", liveOrder.getChargingDurationMonth() + "");
		map.put("drtDay", liveOrder.getChargingDurationDay() + "");
		map.put("from", type);
		if (startTime == null) {
			startTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
		}
		map.put("startTime", startTime);
		map.put("endTime", liveOrder.getCloseTime());
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.LIVE_OPEN_API, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
			DATA.info("[LiveOpenResponse] notifyResponse:statusCode={},dataResult={},link={}", new Object[] { response.statusCode, response.entityString, link });
			return "通知开通失败";
		}
		String result = JSONObject.parseObject(response.entityString).getString("success");
		if (!"true".equals(result)) {
			String message = JSONObject.parseObject(response.entityString).getString("message");
			DATA.info("[openLiveResponse] openLiveMessage:Message={},mac={},sn={}", new Object[] { message , user.getMac(), user.getSn()});
			return message;
		}
		return "success";
	}

	/**
	 * 直播续费接口请求
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param user
	 * @param liveOrder
	 * @param liveOrderId
	 * @return
	 */
	public String renewLiveService(UserVo user, LiveOrder liveOrder, String liveOrderId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mac", user.getMac());
		map.put("sn", user.getSn());
		map.put("orderNumber", liveOrderId);
		map.put("userId", user.getId() + "");
		map.put("deviceCode", user.getDevicecode());
		map.put("productId", liveOrder.getProductId() + "");
		map.put("drtYear", liveOrder.getChargingDurationYear() + "");
		map.put("drtMonth", liveOrder.getChargingDurationMonth() + "");
		map.put("drtDay", liveOrder.getChargingDurationDay() + "");
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.LIVE_RENEW_API, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
			DATA.info("[renewLiveResponse] notifyRespons:statusCode={},dataResult={},link={}", new Object[] { response.statusCode, response.entityString, link });
			return "error";
		}
		return response.entityString;
	}

	public String refundLive(LiveOrder liveOrder) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", liveOrder.getOrderType() + "");
		map.put("orderNumber", liveOrder.getLiveOrderId());
		DATA.info("[refundLiveResponse] type={},orderNumber={},mac={},sn={}", new Object[] { liveOrder.getOrderType(), liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn() });
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.LIVE_BACK_ORDER_API, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
			DATA.info("[refundLiveResponse] notifyRespons:statusCode={},dataResult={},link={},liveOrderId={},mac={},sn={}", new Object[] { response.statusCode, response.entityString, link, liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn() });
			return "通知退订失败";
		}
		String result = JSONObject.parseObject(response.entityString).getString("success");
		if (!"true".equals(result)) {
			String message = JSONObject.parseObject(response.entityString).getString("message");
			DATA.info("[refundLiveResponse] refundLiveMessage:liveOrderId={},mac={},sn={},Message={},link={}", new Object[] { liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn(), message ,link});
			return message;
		}
		return "success";
	}

	/**
	 * 请求播控，查询直播订单状态
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param liveOrder
	 * @return
	 */
	public String queryStatus(LiveOrder liveOrder) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", liveOrder.getLiveOrderId());
		String link = DMPayHelper.toLinkForNotifyToBase(map, ParamConstants.LIVE_TOKEN);
		HiveHttpResponse response = HiveHttpPost.postString(ApiConstants.LIVE_QUERY_STATUS_API, link, HiveHttpEntityType.STRING);
		if (response.statusCode != HttpStatus.SC_OK || StringUtils.isEmpty(response.entityString)) {
			DATA.info("[queryStatusResponse] notifyRespons:statusCode={},dataResult={},link={}", new Object[] { response.statusCode, response.entityString, link });
			return "查询状态失败";
		}
		DATA.info("[queryStatusResponse] liveQueryStatusResp:statusCode={},dataResult={},link={}", new Object[] { response.statusCode, response.entityString, link });
		return JSONObject.parseObject(response.entityString).getString("success");
	}
}
