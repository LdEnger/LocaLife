package com.hiveview.action.card;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
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
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.card.CardService;
import com.hiveview.service.sys.SysRoleService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;


/**
 * Title：CardAction.java
 * Description：活动卡管理类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年11月30日 下午3:20:47
 */
@Controller
@RequestMapping("/card")
public class CardAction extends BaseAction{
	
	@Autowired
	ActivityService activityService;
	@Autowired
	CardService cardService;
	@Autowired
	BranchService branchService;
	@Autowired
	HallService hallService;
	@Autowired
	SysRoleService sysRoleService;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@	RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		req.setAttribute("currentUser", currentUser);
		DATA.info("CardDisplayInitSessionParam：userId={},roleId={}", new Object[] {currentUser.getUserId(), currentUser.getRoleId()});
		Boolean bol=sysRoleService.getBatchRole(currentUser.getRoleId());
		if(bol){
			req.setAttribute("isBatch", "1");
		}else{
			req.setAttribute("isBatch", "0");
		}
		//获取分公司和营业厅名单
		List<Activity> actList=activityService.getAllList();
		List<Branch> braList=branchService.getBranchList();
		Integer branchId= currentUser.getBranchId();
		if(branchId!=null){
			List<Hall> hallList = hallService.getHallList(branchId);
			req.setAttribute("hallList", hallList);
		}
		req.setAttribute("activityList", actList);
		req.setAttribute("branchList", braList);
		//获取partnerKey 用于激活卡
		String partnerKey = ParamConstants.VIP_PARTNER_KEY;
		req.setAttribute("partnerKey", partnerKey);
		return "card/card_detail";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getCardList(AjaxPage ajaxPage,Card card) {
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
		String mac=card.getTerminalMac();
		String sn = card.getTerminalSn();
		Integer activityId= card.getActivityId();
		//判定用户是否已参与活动
		if(mac!=null&&sn!=null&&activityId!=null){
			Card cond=new Card();
			cond.setActivityId(activityId);
			cond.setTerminalMac(mac);
			cond.setTerminalSn(sn);
			if(cardService.getRepeatCard(cond)!=null){
				data.setCode(0);
				data.setMsg("该用户已参与活动，不能重复参与！");
				return data;
			}
		}
			try {
				boolean bool = cardService.add(card);
				if (bool){
					data.setCode(1);
					data.setMsg(card.getActivationCode());
				}
				else
					data.setCode(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
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
	* @param request
	* @return
	*/ 
	@RequestMapping(value = "/activation",method = RequestMethod.POST)
	@ResponseBody
	public OpResult activation(HttpServletRequest request){
		String parameters = super.getParameters(request);
		try {
			parameters = URLDecoder.decode(parameters, "UTF-8");
			Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
			if(parametersMap.isEmpty()){
				return new OpResult(OpResultTypeEnum.MSGERR);
			}
			Map<String, String> map = new HashMap<String,String>();
			String cardPwd = parametersMap.get("cardPwd");
			String mac = parametersMap.get("mac");
			String sn = parametersMap.get("sn");
			//判空
			if(StringUtils.isEmpty(mac)||StringUtils.isEmpty(sn)){
				DATA.info("[activation] mac,sn is null:cardPwd={}", new Object[] {cardPwd});
				return new OpResult(OpResultTypeEnum.MSGERR,"没有mac或sn");
			}
			Card userCard = cardService.getCardByActivityCode(cardPwd);
			if(userCard == null){
				DATA.info("[activation] InfoError:parameters={}", new Object[] {parameters});
				return new OpResult(OpResultTypeEnum.MSGERR,"没有该促销卡");
			}
			Card cond = new Card();
			cond.setActivityId(userCard.getActivityId());
			cond.setTerminalMac(mac);
			cond.setTerminalSn(sn);
			//防止同一用户同时参与两次相同活动
			if(cardService.getRepeatCard(cond)!=null){
				DATA.info("[activation] user：mac={},sn={}已经参与过本次活动", new Object[] {mac,sn});
				return new OpResult(OpResultTypeEnum.SYSERR,"该用户已经参与过本次活动！");
			}
			map.put("cardPwd", cardPwd);
			map.put("mac",mac);
			map.put("sn",sn);
			String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
			if(link.equals(parameters)){
				DATA.info("[activation] RequestParameters:parameters={}", new Object[] {parameters});
				return cardService.useCard(mac,sn,cardPwd);
			}
			DATA.info("[activation] LinkError:link={},parameters={},cardPwd={},mac={},sn={}", new Object[] { link, parameters, cardPwd,mac,sn});
			return new OpResult(OpResultTypeEnum.UNSAFE,"用户信息不全");
		} catch (Exception e) {
			e.printStackTrace();
			DATA.info("[activation] SystemError:parameters={}", new Object[] {parameters});
			return new OpResult(OpResultTypeEnum.SYSERR,"系统处理异常");
		}
	}
	
	/**
	 * 注销卡券
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancel",method = RequestMethod.POST)
	@ResponseBody
	public OpResult cancelCard(HttpServletRequest request){
		String parameters = super.getParameters(request);
		if(parameters !=null){
			return cardService.cancelCard(parameters);
		}
		DATA.info("parameters is null");
		return new OpResult(OpResultTypeEnum.MSGERR,"缺少必要参数");
	}
	
	@	RequestMapping(value="/getCardById")
	public Card getCardById(Integer id){
		return cardService.getCardById(id);
	}
	
}
