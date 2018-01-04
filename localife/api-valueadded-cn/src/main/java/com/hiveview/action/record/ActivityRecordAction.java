package com.hiveview.action.record;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.entity.bo.OpResult;
import com.hiveview.service.activity.ActivityRecordService;

@Controller
@RequestMapping("/record")
public class ActivityRecordAction extends BaseAction{

	@Autowired
	ActivityRecordService activityRecordService;
	
	private static final Logger DATA = LoggerFactory.getLogger("data");
	
	/**
	* 获取指定用户的活动订单记录
	* @param request
	* @return
	*/ 
	@RequestMapping(value = "/getUserActivity", method = RequestMethod.POST)
	@ResponseBody
	public OpResult getUserActivityRecord(HttpServletRequest request){
		String uid = request.getParameter("uid");
		if(StringUtils.isEmpty(uid)){
			DATA.info("uid is null");
			return new OpResult(OpResultTypeEnum.MSGERR,"  空的用户id");
		}
		return activityRecordService.getUserActivity(Integer.parseInt(uid));
	}

	/**
	* 清空指定用户的全部活动记录
	* @param request
	* @return
	*/ 
	@RequestMapping(value = "/clearAllRecord", method = RequestMethod.POST)
	@ResponseBody
	public OpResult clearAllRecord(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String activityOrderType = request.getParameter("activityOrderType");
		if(StringUtils.isEmpty(uid)){
			DATA.info("uid is null");
			return new OpResult(OpResultTypeEnum.MSGERR,"  空的用户id");
		}
		return activityRecordService.clearAllRecord(uid,activityOrderType);
	}

	/**
	* 清空用户指定订单记录
	* @param request
	* @return
	*/ 
	@RequestMapping(value = "/delRecordById", method = RequestMethod.POST)
	@ResponseBody
	public OpResult delRecordById(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		if(StringUtils.isEmpty(uid)){
			DATA.info("uid is null");
			return new OpResult(OpResultTypeEnum.MSGERR,"  用户id为空");
		}
		String orderId = request.getParameter("orderId");
		if(StringUtils.isEmpty(orderId)){
			DATA.info("orderId is null:uid={}",new Object[]{uid});
			return new OpResult(OpResultTypeEnum.MSGERR,"  订单信息为空");
		}
		return activityRecordService.delRecordByOrderId(uid,orderId);
	}
}
