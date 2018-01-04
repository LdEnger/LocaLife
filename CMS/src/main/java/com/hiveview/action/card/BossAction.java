package main.java.com.hiveview.action.card;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.BossReport;
import com.hiveview.entity.card.ExcelFile;
import com.hiveview.entity.card.Product;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.card.CardService;
import com.hiveview.util.StringUtils;

/**
 * Title：boss相关操作action
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-3-20下午9:16:04
 */
@Controller
@RequestMapping("/boss")
public class BossAction extends BaseAction {

	@Autowired
	private CardService cardService;

	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/show")
	public String show(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		return "card/excelFileList";
	}

	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getCardList(AjaxPage ajaxPage, ExcelFile file,HttpServletRequest request) {
		ScriptPage scriptPage = null;
		try {
			file.copy(ajaxPage);
			scriptPage = cardService.getExcelFileList(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/showProduct")
	public String showProduct(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		return "card/productList";
	}

	@RequestMapping(value = "/getProductList")
	@ResponseBody
	public ScriptPage getProductList(AjaxPage ajaxPage, Product file,HttpServletRequest request) {
		ScriptPage scriptPage = null;
		try {
			file.copy(ajaxPage);
			scriptPage = cardService.getProductList(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}

	@RequestMapping(value = "/showBossOrder")
	public String showBossOrder(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if (currentUser == null) {
			return "timeout";
		}
		return "card/bossOrder";
	}

	@RequestMapping(value = "/getBossOrderList")
	@ResponseBody
	public ScriptPage getBossOrderList(AjaxPage ajaxPage, BossOrderNew file,HttpServletRequest request) {
		ScriptPage scriptPage = null;
		try {
			Object obj = request.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
			SysUser currentUser = (SysUser) obj;
			if (ParamConstants.BRANCH_ROLE == currentUser.getRoleId()) { // 战区管理员
				file.setBranchId(currentUser.getBranchId());
			}
			file.copy(ajaxPage);
			scriptPage = cardService.getBossOrderNewList(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	@RequestMapping(value = "/updateProduct")
	@ResponseBody
	public Data updateProduct(final Product product,HttpServletRequest req) {
		int flg =cardService.updateProduct(product);
		Data data =new Data();
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	
	@RequestMapping(value = "/doExcelEdit")
	@ResponseBody
	public Data doExcelEdit(final ExcelFile file,HttpServletRequest req) {
		int flg =cardService.doEditBossProductJob(file.getId());
		Data data =new Data();
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	
	@RequestMapping(value = "/doBossOrder")
	@ResponseBody
	public Data doBossOrder(final ExcelFile file,HttpServletRequest req) {
		cardService.doBossTask(2,file.getMsg());
		Data data =new Data();
		return data;
	}
	
	
	@RequestMapping(value = "/updateBossOrder")
	@ResponseBody
	public Data updateBossOrder(final BossOrderNew order,HttpServletRequest req) {
		order.setStatus(1);
		order.setMsg("初始状态");
		int flg =cardService.updateBossOrder(order);
		Data data =new Data();
		if (flg==0) {
			data.setCode(0);
			data.setMsg("更新失败");
			return data;
		}
		return data;
	}
	/**
	 * 概述：发短信
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-6下午8:03:46
	 */
	@RequestMapping(value = "/sendActivitySms")
	@ResponseBody
	public Data sendActivitySms(final BossOrderNew order,HttpServletRequest req) {
		Data d =new Data();
		Data data = cardService.getActivityCodeByOrderId(order.getId());
		if(data.getMsg()!=null&&data.getMsg().length()>0){
			String activityCode = data.getMsg();
			String result = cardService.sendBossSms(activityCode);
			d.setCode(1);
			if(result.equals("1")){
				d.setMsg("发送成功");
			}else{
				d.setMsg(result);
			}
			
		}else{
			return data;
		}
		return d;
	}  
	/**
	 * 概述：提卡密
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-6下午8:03:03
	 */
	@RequestMapping(value = "/getActivityCode")
	@ResponseBody
	public Data getActivityCode(final BossOrderNew order,HttpServletRequest req) {
		
		return cardService.getActivityCodeByOrderId(order.getId());
	} 
	/**
	 * 概述：根据分公司获取活动列表
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-28下午1:48:53
	 */
	@RequestMapping(value = "/getActivityList")
	@ResponseBody
	public List<Activity>  getActivityList(Activity a){
		a.setStatus(1);//只查有效的
		List<Activity> list =activityService.getActivity(a);
		return list;
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
		String type =req.getParameter("q_type");
		List<BossReport> list =null;
		if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
			list =new ArrayList<BossReport>();
			req.setAttribute("list", list);
			req.setAttribute("start", "");
			req.setAttribute("end", "");
		}else{
			if(type.equals("boss")){
				list =cardService.getBossReport(startDate,endDate);
			}else if(type.equals("city")){
				list =cardService.getBossCityReport(startDate,endDate);
			}
			req.setAttribute("list", list);
			req.setAttribute("start", startDate);
			req.setAttribute("end", endDate);
		}
		req.setAttribute("q_type", type);
		return "card/showreport";
	}
	
	/**
	 * 概述：获取报告
	 * 返回值：Data
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-4-6下午8:03:03
	 */
	@RequestMapping(value = "/getTotalTxt")
	@ResponseBody
	public Data getTotalTxt(HttpServletRequest req) {
		
		return cardService.getTotalTxt();
	} 
}
