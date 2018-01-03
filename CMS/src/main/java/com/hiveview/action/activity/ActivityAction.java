package main.java.com.hiveview.action.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityEx;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.LoginAttribute;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.service.sys.ZoneCityService;

/**
 * Title：ActivityAction.java
 * Description：活动管理类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年11月26日 下午7:34:18
 */
@Controller
@RequestMapping("/activity")
public class ActivityAction {
	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ChargePriceApi chargePriceApi;
	@Autowired
	private ZoneCityService zoneCityService;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getActivityList(AjaxPage ajaxPage,Activity activity,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		Integer roleId = currentUser.getRoleId();
		try {
			activity.copy(ajaxPage);
			scriptPage = activityService.getActivityList(activity,roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Data add(final Activity activity) {
		Data data = new Data();
			try {
				boolean bool = activityService.add(activity);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Data update(final Activity activity) {
		Data data = new Data();
			try {
				boolean bool = activityService.update(activity);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	@RequestMapping(value = "/del")
	@ResponseBody
	public Data del(final Activity activity) {
		Data data = new Data();
			try {
				boolean bool = activityService.del(activity);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 

	
	@RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		//战区信息从城市和战区关联表里查询
		currentUser = zoneCityService.setZoneInfo(currentUser);
		req.setAttribute("currentUser", currentUser);
		DATA.info("ActivityDisplayInitSessionParam：userId={},roleId={}", new Object[] {currentUser.getUserId(), currentUser.getRoleId()});
		try{
			List<VipPackagePriceVo> list = chargePriceApi.getVipChargPriceList();
			req.setAttribute("list",list);
		}catch(Exception e){
			DATA.info("获取计费包异常");
			e.printStackTrace();
		}
		return "activity/activity_detail";
	}
	
	
	@RequestMapping(value="/showex")
	public String showEx(HttpServletRequest req){
		Object obj = req.getSession().getAttribute(LoginAttribute.ATTRIBUTE_USER);
		SysUser currentUser = (SysUser) obj;
		if(currentUser==null){
			return "timeout";
		}
		return "activity/activityex_detail";
	}
	@RequestMapping(value = "/addex")
	@ResponseBody
	public Data addex(final ActivityEx ex) {
		Data data = new Data();
			try {
				ex.setChargingId(9999);
				ex.setChargingName("nousecol");
				ex.setChargingPic("");
				int prodcutDay =ex.getProductCycle()*30;
				ex.setProductDay(prodcutDay);
				int chargingDuration =(prodcutDay+ex.getProductFreeDay())*24*3600;
				ex.setChargingDuration(chargingDuration);
				boolean bool = activityService.addex(ex);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		} 
	
	
	@RequestMapping(value = "/updateex")
	@ResponseBody
	public Data updateex(final ActivityEx ex) {
		Data data = new Data();
			try {
				boolean bool = activityService.updateex(ex);
				if (bool){
					data.setCode(1);
				}else{
					data.setCode(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
	}
	@RequestMapping(value = "/getListex")
	@ResponseBody
	public ScriptPage getActivityListex(AjaxPage ajaxPage,ActivityEx ex,HttpServletRequest req) {
		ScriptPage scriptPage = null;
		try {
			ex.copy(ajaxPage);
			scriptPage = activityService.getActivityExList(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scriptPage;
	}
	
}
