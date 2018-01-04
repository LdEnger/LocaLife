package com.hiveview.action.award;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hiveview.entity.bo.award.AwardMailInfo;
import com.hiveview.entity.po.award.AwardOrder;
import com.hiveview.entity.po.award.AwardPlay;
import com.hiveview.service.award.AwardOrderService;
import com.hiveview.service.award.AwardPlayService;
import com.hiveview.service.award.AwardUserService;

@Controller
@RequestMapping(value = "/award/awardMail")
public class AwardMailAction {

	@Autowired
	private AwardUserService awardUserService;
	@Autowired
	private AwardPlayService apService;
	@Autowired
	private AwardOrderService awardOrderService;

	@RequestMapping(value = "/show")
	public ModelAndView show(String userId, String awardCode, Integer detailId,
			String userInfoType) {

		AwardPlay tmp = new AwardPlay();
		tmp.setUserId(userId);
		tmp.setAwardCode(awardCode);
		tmp.setDetailId(detailId);
		tmp.setAcceptFlag(1);
		AwardPlay ap = apService.get(tmp);
		if (ap == null) {
			return new ModelAndView("redirect:/award/awardMail/error.html")
					.addObject("msg", "未找到用户中奖数据,无法操作!");
		}
		AwardOrder t = new AwardOrder();
		t.setAwardDetailId(detailId);
		t.setAwardUserId(userId);
		t.setAwardCode(awardCode);
		AwardOrder ao = awardOrderService.get(t);
		if (ao == null) {
			return new ModelAndView("redirect:/award/awardMail/error.html")
					.addObject("msg", "未找到订单数据,无法操作!");
		}
		if (ao.getOrderStatus().equals(1)) {
			return new ModelAndView("redirect:/award/awardMail/error.html")
					.addObject("msg", "邮寄信息已填写,请查看我的奖品!");
		}
		Map<String, String> m = new HashMap<String, String>();
		// 设置是否显示填写属性,姓名等
		m.put("notHide1", "false");
		m.put("notHide2", "false");
		m.put("notHide3", "false");
		m.put("notHide4", "false");
		if (!StringUtils.isEmpty(userInfoType)) {
			String[] type = userInfoType.split(",");
			for (int i = 0; i < type.length; i++) {
				if (type[i].equals("1")) {
					m.put("notHide1", "true");
				}
				if (type[i].equals("2")) {
					m.put("notHide2", "true");
				}
				if (type[i].equals("3")) {
					m.put("notHide3", "true");
				}
				if (type[i].equals("4")) {
					m.put("notHide4", "true");
				}
			}
		}
		m.put("userId", userId);
		m.put("awardCode", awardCode);
		m.put("detailId", detailId.toString());
		m.put("awardOrderId", ao.getId().toString());
		m.put("awardPlayId", ap.getId().toString());
		ModelAndView mv = new ModelAndView("award/award_mail_info", m);
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(AwardMailInfo awardMailInfo) {
		try {
			AwardPlay ap = apService.getById(awardMailInfo.getAwardPlayId());
			if (ap == null) {
				return new ModelAndView("redirect:/award/awardMail/error.html")
						.addObject("msg", "未找到用户中奖数据,无法操作!");
			}
			AwardOrder t = new AwardOrder();
			t.setId(awardMailInfo.getAwardOrderId());
			AwardOrder ao = awardOrderService.get(t);
			if (ao == null) {
				return new ModelAndView("redirect:/award/awardMail/error.html")
						.addObject("msg", "未找到订单数据,无法操作!");
			}
			ap.setRealName(awardMailInfo.getRealName());
			ap.setUserPhone(awardMailInfo.getUserPhone());
			ap.setPostcode(awardMailInfo.getPostcode());
			ap.setUserAddr(awardMailInfo.getUserAddr());
			apService.update(ap);
			ao.setOrderStatus(1);
			awardOrderService.update(ao);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/award/awardMail/error.html")
					.addObject("msg", "保存数据出错");
		}
		return new ModelAndView("redirect:/award/awardMail/succuss.html");
	}

	@RequestMapping(value = "/succuss")
	public ModelAndView succuss() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("msg", "保存成功");
		return new ModelAndView("award/award_suc", m);
	}

	@RequestMapping(value = "/error")
	public ModelAndView error(String msg) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("msg", msg);
		return new ModelAndView("award/award_suc", m);
	}
}
