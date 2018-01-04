package com.hiveview.action.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.entity.discount.Recharge;
import com.hiveview.service.discount.RechargeService;

/**
 * Title：RechargeApi.java
 * Description：充值金额API
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年4月5日 上午11:12:36
 */
@Controller
@RequestMapping("/recharge")
public class RechargeApi {
	@Autowired
	RechargeService rechargeService;
	
	/**
	 * 获取所有有效的充值金额，并返回此金额下的优惠金额、图片
	 * @return
	 */
	@RequestMapping(value = "/getEnabledList")
	@ResponseBody
	public List<Recharge> getRechargeList() {
		 List<Recharge>	rechargeList = rechargeService.getAllEnabledRecharge();
		return rechargeList;
	}
}
