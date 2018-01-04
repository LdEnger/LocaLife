package com.hiveview.action.buyRelation;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BuyService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;

@Controller
@RequestMapping("/buyRelation")
public class BuyRelationAction extends BaseAction {

	@Autowired
	BuyService buyService;

	private static final Logger DATA = LoggerFactory.getLogger("data");

	@RequestMapping(value = "/free_old", method = RequestMethod.POST)
	@ResponseBody
	public OpResult AddBuyRelation(HttpServletRequest request) {
		String parameters = super.getParameters(request);
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			if (parametersMap.isEmpty()) {
				DATA.info("[buyRelationFree] parameters is null : 参数不能为空");
				return new OpResult(OpResultTypeEnum.MSGERR, "参数不能为空");
			}
			String sign = this.getSigntoparameters(parametersMap);
			if (!sign.equals(parametersMap.get("sign"))) {
				DATA.info("[buyRelationFree] sign is error:sign={},sign_={},parametersMap={}", new Object[] { sign, parametersMap.get("sign"), parametersMap });
				return new OpResult(OpResultTypeEnum.UNSAFE, "签名错误");
			}
			return buyService.buyOrder(parametersMap);
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[freeBuyRelation] system exception");
			return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
		}
	}

	@RequestMapping(value = "/free", method = RequestMethod.POST)
	@ResponseBody
	public OpResult freeBuyRelation(HttpServletRequest request) {
		String parameters = super.getParameters(request);
		try {
			return buyService.freeBuyVipOrder(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[freeBuyRelation] system exception");
			return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
		}
	}

	/**
	 * 生成sign
	 * 
	 * @param parametersMap
	 * @return
	 */
	private String getSigntoparameters(Map<String, String> parametersMap) {
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
