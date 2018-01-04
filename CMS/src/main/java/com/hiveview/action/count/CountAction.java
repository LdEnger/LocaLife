package main.java.com.hiveview.action.count;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.BossReport;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.service.count.CountService;
import com.hiveview.util.DateUtil;
import com.hiveview.util.StringUtils;

@Controller
@RequestMapping("/count")
public class CountAction {
	
	@Autowired
	CountService countService;
	@Autowired
	private ChargePriceApi chargePriceApi;
	
	@	RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		List<VipPackagePriceVo> list = chargePriceApi.getVipChargPriceList();
		req.setAttribute("activityList", list);
		return "count/count_detail";
	}
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getCountList(AjaxPage ajaxPage,Card card) {
		ScriptPage scriptPage = null;
		try {
			String activationTime =card.getActivationTime();
			if(activationTime!=null&&activationTime.length()==10){
				activationTime = DateUtil.dateToString(DateUtil.getMotherTargetDate(DateUtil.stringToDate(activationTime, 0), 1));
				card.setActivationTime(activationTime);
			}
			card.copy(ajaxPage);
			scriptPage = countService.getCountRecordList(card);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/showReport")
	public String showReport(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		String startDate =req.getParameter("start");
		String endDate =req.getParameter("end");
		List<BossReport> list =null;
		if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
			list =new ArrayList<BossReport>();
			req.setAttribute("list", list);
			req.setAttribute("start", "");
			req.setAttribute("end", "");
		}else{
			list =countService.getBossReport(startDate,endDate);
			req.setAttribute("list", list);
			req.setAttribute("start", startDate);
			req.setAttribute("end", endDate);
		}
		return "count/showreport";
	}

}
