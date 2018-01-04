package com.hiveview.action.UnsucOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.action.base.BaseAction;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.entity.bo.OpResult;
import com.hiveview.service.activity.ActivityOrderService;
import com.hiveview.service.card.CardService;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.StringUtils;

/**
 * Title：UnsucOrderApi.java
 * Description：异常订单接口，对外提供
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年12月10日 下午3:48:57
 */
@Controller
@RequestMapping("/api")
public class UnsucOrderApi extends BaseAction{
	
	@Autowired
	CardService cardService;
	@Autowired
	ActivityOrderService aoService;
	
	/**
	 * 获取有问题的活动订单
	 * @return
	 */
	@RequestMapping(value = "/getUnsucList")
	@ResponseBody
	public List<ActivityOrder> getUnsucList(){
		return this.aoService.getUnsucList();
	}
	
	/**
	 * 用于根据订单号改卡定单的状态
	 * @param parameters 加密后的参数加签名
	 * @return
	 */
	@RequestMapping(value = "/changeOrderStatus",method = RequestMethod.POST)
	@ResponseBody
	public OpResult changeOrderStatus(HttpServletRequest request){
		String parameters = super.getParameters(request);
		Map<String, String> parametersMap = StringUtils.linkToMap(parameters);
		if(parametersMap==null){
			return new OpResult(OpResultTypeEnum.MSGERR,"参数为空");
		}
		Map<String, String> map = new HashMap<String,String>();
		String activityOrderId = parametersMap.get("activityOrderId");
		map.put("activityOrderId", activityOrderId);
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		if(link.equals(parameters)){
			try {
				
				boolean bool = this.aoService.changeOrderStatus(activityOrderId);//更改活动订单表中状态
				boolean flag = this.cardService.changeOrderStatus(activityOrderId);//更改卡信息中状态
				
				if (bool&&flag){
					return new OpResult(OpResultTypeEnum.SUCC);
				}else if(bool){
					return new OpResult(OpResultTypeEnum.SYSERR,"卡订单状态更新失败");
				}else if(flag){
					return new OpResult(OpResultTypeEnum.SYSERR,"活动订单状态更新失败");
				}else
					return new OpResult(OpResultTypeEnum.SYSERR,"状态更新失败");
			} catch (Exception e) {
				e.printStackTrace();
				return new OpResult(OpResultTypeEnum.SYSERR,"处理异常");
			}
		}else{
			return new OpResult(OpResultTypeEnum.UNSAFE,"签名错误");
		}
	}

}
