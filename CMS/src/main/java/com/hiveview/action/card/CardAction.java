package main.java.com.hiveview.action.card;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.sys.Zone;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.HallService;
import com.hiveview.service.api.DeviceApi;
import com.hiveview.service.card.CardService;
import com.hiveview.service.sys.SysRoleService;
import com.hiveview.service.sys.ZoneCityService;
import com.hiveview.service.sys.ZoneService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;

/**
 * Title：CardAction.java Description：活动卡管理类 Company：hiveview.com Author：韩贺鹏
 * Email：hanhepeng@btte.net 2015年11月30日 下午3:20:47
 */
@Controller
@RequestMapping("/card")
public class CardAction extends BaseAction {

	@Autowired
	private CardService cardService;
	@Autowired
	private HallService hallService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	private DeviceApi deviceApi;

	private static final Logger DATA = LoggerFactory.getLogger("data");

	@RequestMapping(value = "/show")
	public String show(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		// 战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
		Boolean bol = sysRoleService.getBatchRole(currentUser.getRoleId());
		if (bol) {
			req.setAttribute("isBatch", "1");
		} else {
			req.setAttribute("isBatch", "0");
		}
		Integer zoneId = currentUser.getZoneId();
		List<Branch> braList = null;
		if (ParamConstants.ZONE_ROLE == currentUser.getRoleId()) { // 战区管理员
			braList = zoneCityService.getBranchByZone(zoneId);
		}
		Integer branchId = currentUser.getBranchId();
		if (branchId != null && branchId != -1) {
			List<Hall> hallList = hallService.getHallList(branchId);
			req.setAttribute("hallList", hallList);
		}
		// 根据角色区分，只能看到自己或上级创建的活动
		Integer roleId = currentUser.getRoleId();
		Integer cityId = Integer.valueOf(currentUser.getCityCode());
		Integer hallId = currentUser.getHallId();
		List<Activity> actList = cardService.getBackActivityList(roleId, cityId, branchId, hallId); //
		req.setAttribute("activityList", actList);
		req.setAttribute("branchList", braList);
		List<Zone> zoneList = zoneService.getAllList();
		req.setAttribute("zoneList", zoneList);
		if (ParamConstants.HORIZ_ZONE.equals(zoneId)) {
			req.setAttribute("horizflag", "1"); // 水平市场战区
		}
		// 获取partnerKey 用于激活卡
		String partnerKey = ParamConstants.VIP_PARTNER_KEY;
		req.setAttribute("partnerKey", partnerKey);
		return "card/card_detail";
	}

	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getCardList(AjaxPage ajaxPage, Card card,HttpServletRequest request) {
		ScriptPage scriptPage = null;
		try {
			card.copy(ajaxPage);
			scriptPage = cardService.getCardList(card);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final Card card) {
		Data data = new Data();
		String mac = card.getTerminalMac();
		String sn = card.getTerminalSn();
		card.setCardType("vip");
		card.setSource(3);
		if (StringUtils.isNotEmpty(mac) || StringUtils.isNotEmpty(sn)) {
			boolean flg = this.cardService.isRealUser(mac, sn);
			if (!flg) {
				data.setCode(0);
				data.setMsg("根据mac，sn没有找到对应用户");
				return data;
			}
		}
		return cardService.add(card);
	}

	@RequestMapping(value = "/addBatch")
	@ResponseBody
	public Data addBatch(final Card card, HttpServletRequest request) {
		Data data = new Data();
		String mac = card.getTerminalMac();
		String sn = card.getTerminalSn();
		card.setCardType("vip");
		if (StringUtils.isNotEmpty(mac) || StringUtils.isNotEmpty(sn)) {
			boolean flg = this.cardService.isRealUser(mac, sn);
			if (!flg) {
				data.setCode(0);
				data.setMsg("根据mac，sn没有找到对应用户");
				return data;
			}
		}
		int batchSize = ServletRequestUtils.getIntParameter(request, "batchSize", -1);
		return cardService.addBatchForHoriz(card, batchSize);
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(final Card card) {
		Data data = new Data();
		try {
			boolean bool = cardService.update(card);
			if (bool)
				data.setCode(1);
			else
				data.setCode(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 卡激活
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activation", method = RequestMethod.POST)
	@ResponseBody
	public OpResult activation(HttpServletRequest request) {
		String parameters = super.getParameters(request);
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			String cardPwd = parametersMap.get("cardPwd");
			String mac = parametersMap.get("mac");
			String sn = parametersMap.get("sn");
			if (parametersMap.isEmpty() || StringUtils.isEmpty(cardPwd) || StringUtils.isEmpty(mac)
					|| StringUtils.isEmpty(sn)) {
				return new OpResult(OpResultTypeEnum.MSGERR, "参数不全");
			}
			Card userCard = cardService.getCardByActivityCode(cardPwd);
			if (userCard == null) {
				return new OpResult(OpResultTypeEnum.MSGERR, "没有该促销卡");
			}
			if (StringUtils.isNotEmpty(userCard.getTerminalMac()) || StringUtils.isNotEmpty(userCard.getTerminalSn())) {
				if (!mac.replaceAll(":", "").toUpperCase().equals(userCard.getTerminalMac())
						|| !sn.toUpperCase().equals(userCard.getTerminalSn())) {
					return new OpResult(OpResultTypeEnum.MSGERR, "mac,sn信息与数据库不符");
				}
			}
			if (1 != userCard.getStatus()) {
				return new OpResult(OpResultTypeEnum.MSGERR, "该促销卡已经被使用或过期");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardPwd", cardPwd);
			map.put("mac", mac);
			map.put("sn", sn);
			String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
			if (link.equals(parameters)) {
				DATA.info("[RequestParameters]parameters={}", new Object[] { parameters });
				// return cardService.useCard(mac, sn, userCard);//新接口
//				return cardService.useCard(mac, sn, cardPwd,);// 老接口
				String model  =deviceApi.getDeviceModel(mac, sn);
				return cardService.useCardInUse(mac, sn, cardPwd,model);// 3.2新接口
			}
			DATA.info("[LinkError]link={},parameters={}", new Object[] { link, parameters });
			return new OpResult(OpResultTypeEnum.UNSAFE, "用户信息不全");
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[activation] SystemError:parameters={}", new Object[] { parameters });
			return new OpResult(OpResultTypeEnum.SYSERR, "系统处理异常");
		}
	}

	/**
	 * 注销卡券
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public OpResult cancelCard(HttpServletRequest request) {
		String parameters = super.getParameters(request);
		if (parameters != null) {
			return cardService.cancelCardinUse(parameters);
		}
		DATA.info("parameters is null");
		return new OpResult(OpResultTypeEnum.MSGERR, "缺少必要参数");
	}

	@RequestMapping(value = "/getCardById")
	public Card getCardById(Integer id) {
		return cardService.getCardById(id);
	}

	@RequestMapping(value = "/delCard")
	@ResponseBody
	public Data delCard(Integer id) {
		Data data = new Data();
		try {
			boolean bool = cardService.delCard(id);
			if (bool)
				data.setCode(1);
			else
				data.setCode(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
