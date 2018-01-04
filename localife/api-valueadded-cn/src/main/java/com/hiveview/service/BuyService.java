package com.hiveview.service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.service.card.CardService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;

@Service
public class BuyService {

	@Autowired
	ActivityOrderDao activityOrderDao;
	@Autowired
	VipOrderApi vipOrderApi;
	@Autowired
	CardService cardService;

	public static final int ORDER_CARD = 0;// 免费VIP包类型

	public static final int ORDER_SUBMIT = 1;// 下单
	public static final String ORDER_SUBMIT_STRING = "下单";
	public static final int ORDER_ACTIVITY = 2;// 激活
	public static final String ORDER_ACTIVITY_STRING = "激活";

	private static final Logger DATA = LoggerFactory.getLogger("data");

	/**
	 * 创建领取免费vip订购关系
	 * 
	 * @param parametersMap
	 * @return
	 */
	public OpResult buyOrder(Map<String, String> parametersMap) {
		String chargingId = parametersMap.get("chargingId");
		String chargingName = parametersMap.get("chargingName");
		String chargingPrice = parametersMap.get("chargingPrice");
		String chargingDuration = parametersMap.get("chargingDuration");
		String chargingImg = parametersMap.get("chargingImg");
		String uid = parametersMap.get("uid");
		String uname = parametersMap.get("uname");
		String mac = parametersMap.get("mac");
		String sn = parametersMap.get("sn");
		String devicecode = parametersMap.get("devicecode");
		if (StringUtils.isEmpty(chargingId) || StringUtils.isEmpty(chargingName) || StringUtils.isEmpty(chargingPrice) || StringUtils.isEmpty(chargingDuration)
				|| StringUtils.isEmpty(uid) || StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn) || StringUtils.isEmpty(devicecode)) {
			DATA.info("[buyRelation]paramError:chargingId={},chargingName={},chargingPrice={},chargingDuration={},uid={},mac={},sn={},devicecode={}"
					, new Object[] { chargingId, chargingName, chargingPrice, chargingDuration, uid, mac, sn, devicecode });
			return new OpResult(OpResultTypeEnum.MSGERR, "必要参数缺失");
		}
		long saveBegin = System.currentTimeMillis();
		String activityOrderId = vipOrderApi.orderSubmit(mac, sn, Integer.parseInt(uid), devicecode, ORDER_CARD, chargingDuration, chargingId);
		if ("exist".equals(activityOrderId)) {
			return new OpResult(OpResultTypeEnum.SUCC, "不能重复领取");// 如果存在，返回成功，客户端领取成功，但时长不累加。
		}
		long saveElapse = System.currentTimeMillis() - saveBegin;
		if (saveElapse > ParamConstants.ELAPSE_TIME) {
			DATA.info("[orderSubmitSaveOrder]mac={},sn={},activityOrderId={}", new Object[] { mac, sn, activityOrderId });
		}
		ActivityOrder activityOrder = new ActivityOrder(Integer.parseInt(chargingId), Integer.parseInt(chargingPrice), chargingName,
				Integer.parseInt(chargingDuration), chargingImg, activityOrderId, ORDER_CARD, Integer.parseInt(uid), uname, mac, sn, devicecode,
				ORDER_SUBMIT, StringUtils.getRealTimeString());

		if (activityOrderDao.save(activityOrder) != 1) {
			DATA.info("[buyRelation]submitError:activityOrder={}", new Object[] { activityOrder });
			return new OpResult(OpResultTypeEnum.SYSERR, "下单失败");
		}
		String cardPwd = null;
		long updateBegin = System.currentTimeMillis();
		OpResult op = cardService.activationCard(mac, sn, activityOrder, cardPwd);
		long updateElapse = System.currentTimeMillis() - updateBegin;
		if (updateElapse > ParamConstants.ELAPSE_TIME) {
			DATA.info("[orderSubmitUpdateOrder]mac={},sn={},activityOrder={}", new Object[] { mac, sn, activityOrder });
		}
		return op;
	}

	/**
	 * 免费领取VIP
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月25日重构代码
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public OpResult freeBuyVipOrder(String parameters) throws Exception {
		parameters = URLDecoder.decode(parameters, "UTF-8");
		Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
		if (parametersMap.isEmpty()) {
			DATA.info("[freeBuyVipOrder] parameters is null : 参数不能为空");
			return new OpResult(OpResultTypeEnum.MSGERR, "参数不能为空");
		}
		String sign = this.getSignToParameters(parametersMap);
		if (!sign.equals(parametersMap.get("sign"))) {
			DATA.info("[freeBuyVipOrder] sign is error:sign={},sign_={},parametersMap={}", new Object[] { sign, parametersMap.get("sign"), parametersMap });
			return new OpResult(OpResultTypeEnum.UNSAFE, "签名错误");
		}
		String chargingId = parametersMap.get("chargingId");
		String chargingName = parametersMap.get("chargingName");
		String chargingPrice = parametersMap.get("chargingPrice");
		String chargingDuration = parametersMap.get("chargingDuration");
		String chargingImg = parametersMap.get("chargingImg");
		String uid = parametersMap.get("uid");
		String uname = parametersMap.get("uname");
		String mac = parametersMap.get("mac");
		String sn = parametersMap.get("sn");
		String devicecode = parametersMap.get("devicecode");
		if (StringUtils.isEmpty(chargingId) || StringUtils.isEmpty(chargingName) || StringUtils.isEmpty(chargingPrice) || StringUtils.isEmpty(chargingDuration)
				|| StringUtils.isEmpty(uid) || StringUtils.isEmpty(mac) || StringUtils.isEmpty(sn) || StringUtils.isEmpty(devicecode)) {
			DATA.info("[freeBuyVipOrder]paramError:chargingId={},chargingName={},chargingPrice={},chargingDuration={},uid={},mac={},sn={},devicecode={}"
					, new Object[] { chargingId, chargingName, chargingPrice, chargingDuration, uid, mac, sn, devicecode });
			return new OpResult(OpResultTypeEnum.MSGERR, "必要参数缺失");
		}
		long saveBegin = System.currentTimeMillis();
		parametersMap.put("orderType", ORDER_CARD + "");
		String saveResult = vipOrderApi.freeBuyVipSaveOrder(parametersMap);
		long saveElapse = System.currentTimeMillis() - saveBegin;
		if (saveElapse > ParamConstants.ELAPSE_TIME) {
			DATA.info("[freeBuyVipOrder] 响应时间过长 ：mac={},sn={}", new Object[] { mac, sn });
		}
		if ("error".equals(saveResult)) {
			DATA.info("[freeBuyVipOrder] requestVipSaveOrder error : result ={},mac={},sn={},uid={},chargingId={}", new Object[] { saveResult, mac, sn, uid, chargingId });
			return new OpResult(OpResultTypeEnum.SYSERR, "saveResult");
		}
		if ("exist".equals(saveResult)) {
			DATA.info("[freeBuyVipOrder] order exist : result ={},mac={},sn={},uid={},chargingId={}", new Object[] { saveResult, mac, sn, uid, chargingId });
			return new OpResult(OpResultTypeEnum.SUCC, "不能重复领取");// 如果存在，返回成功，客户端显示领取成功，但时长不累加。
		}
		ActivityOrder activityOrder = new ActivityOrder(Integer.parseInt(chargingId), Integer.parseInt(chargingPrice), chargingName,
				Integer.parseInt(chargingDuration), chargingImg, saveResult, ORDER_CARD, Integer.parseInt(uid), uname, mac, sn, devicecode,
				ORDER_ACTIVITY, StringUtils.getRealTimeString());
		try {
			int result = activityOrderDao.save(activityOrder);
			if (result != 1) {
				DATA.info("[freeBuyVipOrder]saveOrderError:activityOrder={}", new Object[] { activityOrder });
				return new OpResult(OpResultTypeEnum.SYSERR, "保存失败");
			}
			DATA.info("[freeBuyVipOrder]freeBuyVipOrderSuccess:activityOrder={}", new Object[] { activityOrder });
			return new OpResult(OpResultTypeEnum.SUCC, "免费领取VIP成功");
		} catch (Exception e) {
			DATA.info("[freeBuyVipOrder]saveOrderException:activityOrder={}", new Object[] { activityOrder });
			return new OpResult(OpResultTypeEnum.DBERR, "插入数据失败");
		}
	}

	/**
	 * 生成sign
	 * 
	 * @param parametersMap
	 * @return
	 */
	private String getSignToParameters(Map<String, String> parametersMap) {
		if (parametersMap == null) {
			return "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", parametersMap.get("uid"));
		map.put("mac", parametersMap.get("mac"));
		map.put("sn", parametersMap.get("sn"));
		map.put("chargingId", parametersMap.get("chargingId"));
		map.put("chargingPrice", parametersMap.get("chargingPrice"));
		map.put("chargingDuration", parametersMap.get("chargingDuration"));
		map.put("chargingName", parametersMap.get("chargingName"));
		map.put("devicecode", parametersMap.get("devicecode"));
		map.put("uname", parametersMap.get("uname"));
		return DMPayHelper.toSignForNotify(map, ParamConstants.VIP_PARTNER_KEY);
	}
}
