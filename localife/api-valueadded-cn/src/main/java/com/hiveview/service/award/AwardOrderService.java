package com.hiveview.service.award;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ApiConstants;
import com.hiveview.common.ParamConstants;
import com.hiveview.dao.award.AwardOrderDao;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.vo.api.AwardDiscountInfoVo;
import com.hiveview.pay.encryption.DES;
import com.hiveview.service.api.VipOrderApi;
import com.hiveview.util.DateUtilThread;

@Service
public class AwardOrderService {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	AwardOrderDao awardOrderDao;
	@Autowired
	private AwardOrderHelperService awardOrderHelperService;
	@Autowired
	private AwardDetailService awardDetailService;
	@Autowired
	private VipOrderApi vipOrderApi;

	public AwardOrder getByOrderId(String orderId) {
		AwardOrder t = new AwardOrder();
		t.setOrderId(orderId);
		return get(t);
	}

	public AwardOrder get(AwardOrder t) {
		return awardOrderDao.get(t);
	}

	public int save(AwardOrder t) {
		return awardOrderDao.save(t);
	}

	public int update(AwardOrder t) {
		return awardOrderDao.update(t);
	}

	/**
	 * 中奖下单,开通服务(vip时长,首映,麦币) </br> 订单表,已发送-1,未发送-2
	 * 
	 * @return
	 */
	public String awardOrdering(AwardPlay ap, int detailId) {
		try {
			AwardDetail dl = awardDetailService.getById(detailId);
			int property = dl.getAwardProperty();
			AwardOrder ao = new AwardOrder();
			ao.setAwardCode(ap.getAwardCode());
			ao.setAwardUserId(ap.getUserId());
			ao.setAwardDetailId(ap.getDetailId());
			AwardOrder order = get(ao);
			if (order == null) {
				String orderId = generateLiveOrderId(ap.getPlayTime(), ap.getId());
				ao.setOrderId(orderId);
				ao.setOrderStatus(2);// 未发送
				ao.setOrderTime(new Date());
				ao.setAwardPlayId(ap.getId());
				ao.setAwardProperty(property);
				save(ao);
			}
			if (ao.getOrderStatus() != null && ao.getOrderStatus() == 1) {
				return ParamConstants.SUC;
			}
			if (property == 3) {
				return awardOrderHelperService.giveVipTime(dl, ap, ao, 0f);
			}
			if (property == 4) {
				String res = awardOrderHelperService.giveTickets(ParamConstants.PRD_TYPE_BULE, ap, dl, ao, 0f);
				if (res.equals(ParamConstants.ERR)) {
					return ParamConstants.ERR;
				}
				ao.setOrderStatus(1);// 已发送
				update(ao);
				return ParamConstants.SUC;
			}
			// TODO 赠送麦粒功能暂缓测试开通
			// if (property == 5) {
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("givemlAmt", dl.getAwardMlAmount().toString());
			// map.put("giveMsgId", ao.getOrderId());
			// map.put("time", DateUtilThread.now2String(ao.getOrderTime()));
			// String userId = ap.getUserId();
			// String partnerKey = ParamConstants.HIVE_PARTNER_KEY;
			// String uuid = DES.encrypt(userId, partnerKey);
			// map.put("useruuid", uuid);
			// String plain = toBuffer(map, uuid).toString();
			// String sign = TokenUtils.generatePaymentToken(plain, partnerKey);
			// map.put("sign", sign);
			// HiveHttpResponse resp = HiveHttpPost.postMap(
			// ApiConstants.ACT_AWARD_API, map, HiveHttpEntityType.STRING);
			// if (resp.statusCode != HttpStatus.SC_OK
			// || StringUtils.isEmpty(resp.entityString)) {
			// DATA.info(
			// "赠送麦粒失败,statusCode={},mac={},sn={},userId={},deviceCode={}",
			// new Object[] { resp.statusCode, ap.getMac(),
			// ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			// return ParamConstants.ERR;
			// }
			// boolean success = JSONObject.parseObject(resp.entityString)
			// .getBoolean("success");
			// if (!success) {
			// DATA.info(
			// "赠送麦粒失败,entityString={},mac={},sn={},userId={},deviceCode={}",
			// new Object[] { resp.entityString, ap.getMac(),
			// ap.getSn(), ap.getUserId(), ap.getDeviceCode() });
			// return ParamConstants.ERR;
			// }
			// ao.setOrderStatus(1);// 已发送
			// update(ao);
			// return ParamConstants.SUC;
			// }
		} catch (Exception e) {
			DATA.info("生成订单失败,userId={},detailId={}", new Object[] { ap.getUserId(), detailId });
			return ParamConstants.ERR;
		}
		return ParamConstants.SUC;
	}

	static AtomicInteger ACT_ATOMIC = new AtomicInteger(1000);

	private String generateLiveOrderId(Date date, Integer playId) {
		// int orderAtomic = ACT_ATOMIC.incrementAndGet();
		String orderTime = DateUtilThread.date2Long(date);
		String orderId = orderTime + playId;
		return orderId;
	}

	// 赠送麦币用，暂缓开通
	// private StringBuffer toBuffer(Map<String, String> map, String useruuid) {
	// StringBuffer buffer = new StringBuffer("");
	// buffer.append("givemlAmt=").append(map.get("givemlAmt"));
	// buffer.append("&giveMsgId=").append(map.get("giveMsgId"));
	// buffer.append("&time=").append(map.get("time"));
	// buffer.append("&useruuid=").append(useruuid);
	// return buffer;
	// }

	/**
	 * 获取折扣卷支付基本信息
	 * 
	 * @param m
	 * @return
	 */
	public AwardDiscountInfoVo getAwardDiscountBaseInfo(AwardOrder m) {
		return awardOrderDao.getAwardDiscountInfo(m);
	}

	/**
	 * 对折扣卷基本信息加工
	 * 
	 * @param m
	 * @return
	 */
	public AwardDiscountInfoVo getAwardDiscountProcessInfo(AwardDiscountInfoVo v) {
		Float price = v.getAwardPrice();
		v.setAwardPrice(price / 100f);
		v.setPayPrice(getAwardDiscountPrice(price, v.getAwardDiscount()));
		String partnerRt = awardOrderHelperService.getPartnerResult(v.getPartnerId());
		if (partnerRt.equals(ParamConstants.ERR)) {
			return null;
		}
		String partnerKey = JSONObject.parseObject(partnerRt).getString("partnerKey");
		String type = v.getAwardVideoType();
		String notifyUrl = ApiConstants.ACT_URL;
		if (!StringUtils.isEmpty(type)) {
			if (type.equals(ParamConstants.PRD_TYPE_BULE)) {
				// 首映
				notifyUrl = notifyUrl + "api/activity/acceptAwardBlue.json";
			}
			if (type.equals(ParamConstants.PRD_TYPE_VIP)) {
				// vip
				notifyUrl = notifyUrl + "api/activity/acceptAwardVip.json";
			}
		}
		partnerKey = DES.encrypt(partnerKey, ParamConstants.ACT_KEY);
		v.setPartnerKey(partnerKey);
		v.setNotifyUrl(notifyUrl);
		String orderAppend = "{\"tradeNo\":\"" + v.getTradeNo() + "\"}";
		v.setOrderAppend(orderAppend);
		return v;
	}

	/**
	 * 获取打折后价格
	 * 
	 * @param price
	 * @param discount
	 * @return
	 */
	public Float getAwardDiscountPrice(Float price, Integer discount) {
		if (price == null || discount == null) {
			return null;
		}
		Float AawardDiscountPrice = null;
		price = (price / 100f) * discount / 10f;
		AawardDiscountPrice = (float) (Math.round(price * 100)) / 100;
		return AawardDiscountPrice;
	}
}
