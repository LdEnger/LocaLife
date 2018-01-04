package com.hiveview.action.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.ApiResult;
import com.hiveview.entity.bo.ApiResultTypeEnum;
import com.hiveview.entity.po.award.AwardActivity;
import com.hiveview.entity.po.award.AwardActivityType;
import com.hiveview.entity.po.award.AwardDetail;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.entity.vo.api.AwardActivityVo;
import com.hiveview.pay.http.HiveHttpQueryString;
import com.hiveview.service.award.AwardActivityService;
import com.hiveview.service.award.AwardActivityTypeService;
import com.hiveview.service.award.AwardDetailService;
import com.hiveview.service.award.AwardOrderHelperService;
import com.hiveview.service.award.AwardOrderService;
import com.hiveview.service.award.AwardPlayService;

/**
 * 
 * @author wengjingchang
 *
 */
@Controller
@RequestMapping(value = "/api/activity")
public class AwardActivityInfoAction {
	private static final Logger DATA = LoggerFactory.getLogger("data");
	@Autowired
	private AwardActivityService awardActivityService;
	@Autowired
	private AwardActivityTypeService awardActivityTypeService;
	@Autowired
	private AwardOrderService awardOrderService;
	@Autowired
	private AwardDetailService awardDetailService;
	@Autowired
	private AwardPlayService awardPlayService;
	@Autowired
	private AwardOrderHelperService awardOrderHelperService;

	private String getParameterString(HttpServletRequest request) {
		String result = null;
		InputStream is = null;
		try {
			StringBuffer sb = new StringBuffer("");
			is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len, "utf-8"));
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private String getSign(String parm, String partnerKey) throws Exception {
		int index = parm.indexOf("&sign");
		String _parm = parm.substring(0, index);
		String data = _parm + "&partnerKey=" + partnerKey;
		String _sign = DigestUtils.md5Hex(data.getBytes("UTF-8"));
		return _sign;
	}

	/**
	 * 折扣卷 领取首映
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/acceptAwardBlue", method = RequestMethod.POST)
	@ResponseBody
	public String acceptAwardBlue(HttpServletRequest request) {
		try {
			DATA.info("折扣卷,领取首映");
			String parm = getParameterString(request);
			if (StringUtils.isEmpty(parm)) {
				DATA.info("AwardActivityInfoAction[acceptAward]领奖失败,request为空");
				return "success";
			}
			parm = URLDecoder.decode(parm, "UTF-8");
			Map<String, String> map = HiveHttpQueryString.buildMap(parm);
			String orderStatus = map.get("orderStatus");
			if (!"2".equals(orderStatus)) {
				DATA.info("领奖异常,支付状态不为2,request={},orderStatus={}", new Object[] { map, orderStatus });
				return "success";
			}
			String orderAppend = map.get("orderAppend");
			String tradeNo = JSONObject.parseObject(orderAppend).getString("tradeNo");
			if (StringUtils.isEmpty(tradeNo)) {
				DATA.info("领奖异常,tradeNo为空,request={}", new Object[] { map });
				return "success";
			}
			AwardOrder ao = awardOrderService.getByOrderId(tradeNo);
			if (ao == null) {
				DATA.info("领奖异常,奖品订单为空,request={},tradeNo={}", new Object[] { map, tradeNo });
				return "success";
			}
			if (ao.getOrderStatus().equals(1)) {
				return "success";
			}
			AwardDetail dl = awardDetailService.getById(ao.getAwardDetailId());
			Float mlAmt = Float.valueOf(map.get("mlAmt"));
			Float payPrice = awardOrderService.getAwardDiscountPrice((float) dl.getAwardPrice(), dl.getAwardDiscount());
			if (!mlAmt.equals(payPrice)) {
				DATA.info("领奖异常,折扣价与支付价不等,request={},mlAmt={},payPrice={}", new Object[] { map, mlAmt, payPrice });
				return "success";
			}
			String result = awardOrderHelperService.getPartnerResult(dl.getAwardVideoPartnerId());
			if (result.equals(ParamConstants.ERR)) {
				return "success";
			}
			String partnerKey = JSONObject.parseObject(result).getString("partnerKey");
			String _sign = getSign(parm, partnerKey);
			String sign = map.get("sign");
			if (!_sign.equals(sign)) {
				DATA.info("领奖异常,领奖鉴权失败,request={}", new Object[] { map });
				return "success";
			}
			AwardPlay ap = awardPlayService.getById(ao.getAwardPlayId());
			String flag = ParamConstants.ERR;
			// 首映
			flag = awardOrderHelperService.giveTickets(ParamConstants.PRD_TYPE_BULE, ap, dl, ao, mlAmt);
			if (flag.equals(ParamConstants.ERR)) {
				DATA.info("领奖异常,首映影片领奖失败,request={}", new Object[] { map });
			}
			if (flag.equals(ParamConstants.SUC)) {
				// 设置为领奖
				ao.setOrderStatus(1);
				awardOrderService.update(ao);
			}
		} catch (Exception e) {
			DATA.info("折扣卷,领取首映异常.", e);
			return "success";
		}
		return "success";
	}

	/**
	 * 折扣卷 领取vip
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/acceptAwardVip", method = RequestMethod.POST)
	@ResponseBody
	public String acceptAwardVip(HttpServletRequest request) {
		try {
			DATA.info("折扣卷 领取vip");
			String parm = getParameterString(request);
			if (StringUtils.isEmpty(parm)) {
				DATA.info("AwardActivityInfoAction[acceptAward]领奖失败,request为空");
				return "success";
			}
			parm = URLDecoder.decode(parm, "UTF-8");
			Map<String, String> map = HiveHttpQueryString.buildMap(parm);
			String orderStatus = map.get("orderStatus");
			if (!"2".equals(orderStatus)) {
				DATA.info("领奖异常,支付状态不为2,request={},orderStatus={}", new Object[] { map, orderStatus });
				return "success";
			}
			String partnerOrderId = map.get("partnerOrderId");
			if (StringUtils.isEmpty(partnerOrderId)) {
				DATA.info("领奖异常,tradeNo为空,request={}", new Object[] { map });
				return "success";
			}

			AwardOrder ao = awardOrderService.getByOrderId(partnerOrderId);
			if (ao == null) {
				DATA.info("领奖异常,奖品订单为空,request={},partnerOrderId={}", new Object[] { map, partnerOrderId });
				return "success";
			}
			if (ao.getOrderStatus().equals(1)) {
				return "success";
			}
			AwardDetail dl = awardDetailService.getById(ao.getAwardDetailId());
			Float mlAmt = Float.valueOf(map.get("mlAmt"));
			Float payPrice = awardOrderService.getAwardDiscountPrice((float) dl.getAwardPrice(), dl.getAwardDiscount());
			if (!mlAmt.equals(payPrice)) {
				DATA.info("领奖异常,折扣价与支付价不等,request={},mlAmt={},payPrice={}", new Object[] { map, mlAmt, payPrice });
				return "success";
			}
			String result = awardOrderHelperService.getPartnerResult(dl.getAwardVideoPartnerId());
			if (result.equals(ParamConstants.ERR)) {
				return "success";
			}
			String partnerKey = JSONObject.parseObject(result).getString("partnerKey");
			String _sign = getSign(parm, partnerKey);
			String sign = map.get("sign");
			if (!_sign.equals(sign)) {
				DATA.info("领奖异常,领奖鉴权失败,request={}", new Object[] { map });
				return "success";
			}
			AwardPlay ap = awardPlayService.getById(ao.getAwardPlayId());
			String flag = ParamConstants.ERR;
			// vip时长
			flag = awardOrderHelperService.giveVipTime(dl, ap, ao, mlAmt);
			if (flag.equals(ParamConstants.ERR)) {
				DATA.info("领奖异常,vip时长领奖失败,request={}", new Object[] { map });
			}
			if (flag.equals(ParamConstants.SUC)) {
				// 设置为领奖
				ao.setOrderStatus(1);
				awardOrderService.update(ao);
			}
		} catch (Exception e) {
			DATA.info("折扣卷,领取vip异常.", e);
			return "success";
		}
		return "success";
	}

	@RequestMapping(value = "/getActivityTypeList", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getActivityTypeList() {
		ApiResult op = new ApiResult();
		try {
			List<AwardActivityType> list = awardActivityTypeService.getListAll();
			op.setResult(list);
		} catch (Exception e) {
			DATA.info("活动类型获取异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}

	@RequestMapping(value = "/getActivityInfoList", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getActivityInfoList(@RequestParam Integer type, @RequestParam String title) {
		ApiResult op = new ApiResult();
		try {
			AwardActivity awardActivity = new AwardActivity();
			if (!StringUtils.isEmpty(type)) {
				awardActivity.setType(type);
			}
			if (!StringUtils.isEmpty(title)) {
				awardActivity.setTitle(title);
			}
			// 默认显示标识为1
			awardActivity.setShowFlag(1);
			awardActivity.setLimitEndDate(new Date());
			List<AwardActivityVo> list = awardActivityService.getActivityInfoList(awardActivity);

			op.setResult(list);
		} catch (Exception e) {
			DATA.info("推荐位获取活动列表异常", e);
			op.setCode(ApiResultTypeEnum.ERR.getCode());
			op.setDesc(ApiResultTypeEnum.ERR.getType());
		}
		return op;
	}
}
