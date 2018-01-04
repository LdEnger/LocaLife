package com.hiveview.action.live;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.Branch;
import com.hiveview.entity.Hall;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.BranchService;
import com.hiveview.service.HallService;
import com.hiveview.service.live.LiveService;

@Controller
@RequestMapping("/live")
public class LiveAction {

	@Autowired
	BranchService branchService;
	@Autowired
	HallService hallService;
	@Autowired
	LiveService liveService;

	@RequestMapping(value = "/show")
	public String showLive(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		request.setAttribute("currentUser", currentUser);
		List<Branch> braList = branchService.getBranchList();
		Integer branchId = currentUser.getBranchId();
		if (branchId != null) {
			List<Hall> hallList = hallService.getHallList(branchId);
			request.setAttribute("hallList", hallList);
		}
		request.setAttribute("liveList", liveService.getLiveChargPriceList());
		request.setAttribute("branchList", braList);
		return "live/live";
	}

	@RequestMapping(value = "/getLiveList")
	@ResponseBody
	public ScriptPage getLiveList(AjaxPage ajaxPage, LiveOrder liveOrder) {
		liveOrder.copy(ajaxPage);
		return liveService.getLiveList(liveOrder);
	}

	@RequestMapping(value = "/open")
	@ResponseBody
	public OpResult openLive(HttpServletRequest request,LiveOrder liveOrder) {
		String mac = liveOrder.getMac();
		String sn = liveOrder.getSn();
		int openuid = liveOrder.getOpenuid();
		int productId = liveOrder.getProductId();
		if(StringUtils.isEmpty(mac)||StringUtils.isEmpty(sn)||StringUtils.isEmpty(openuid)||StringUtils.isEmpty(productId)){
			return new OpResult(OpResultTypeEnum.MSGERR);
		}
		return liveService.openService(mac,sn,openuid,productId);
	}
}
