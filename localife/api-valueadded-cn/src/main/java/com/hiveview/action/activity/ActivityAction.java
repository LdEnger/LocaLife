package com.hiveview.action.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.AjaxPage;
import com.hiveview.entity.bo.Data;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.service.activity.ActivityService;
import com.hiveview.service.api.ChargePriceApi;

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
	ActivityService activityService;
	@Autowired
	ChargePriceApi chargPriceApi;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ScriptPage getActivityList(AjaxPage ajaxPage,Activity activity) {
		ScriptPage scriptPage = null;
		try {
			activity.copy(ajaxPage);
			scriptPage = activityService.getActivityList(activity);
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

	
	@	RequestMapping(value="/show")
	public String show(HttpServletRequest req){
		try{
			List<VipPackagePriceVo> list = chargPriceApi.getVipChargPriceList();
			req.setAttribute("list",list);
		}catch(Exception e){
			DATA.info("获取计费包异常");
			e.printStackTrace();
		}
		return "activity/activity_detail";
	}
}
