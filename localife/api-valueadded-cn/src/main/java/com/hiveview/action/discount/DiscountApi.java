package com.hiveview.action.discount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.RetResult;
import com.hiveview.entity.discount.Discount;
import com.hiveview.service.discount.DiscountService;

/**
 * Title：DiscountAction.java
 * Description：充送管理类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月30日 下午2:56:48
 */
@Controller
@RequestMapping("/discount")
public class DiscountApi {
	
	private static final Logger LOG = LoggerFactory.getLogger("data");
	
	@Autowired
	DiscountService discountService;
	
	/**
	 * 对外接口，根据充值金额返回充送信息
	 * @param rechargeAmount
	 * @return
	 */
	@RequestMapping(value = "/getDiscountInfo")
	@ResponseBody
	public Discount getDiscountInfo(Integer rechargeAmount){
		Discount discount = new Discount();
		if(rechargeAmount!=null&&0!=rechargeAmount){
			discount = this.discountService.getDiscountInfo(rechargeAmount);
			discount.setCode("success");
		}else{
			discount.setCode("fail");
			discount.setMessage("Parameter is not valid");
		}
		return discount;
	}
	
	/**
	 * 活动麦粒耗尽时，将当前所有充送活动置为无效
	 * @return
	 */
	@RequestMapping(value = "/endDiscount")
	@ResponseBody
	public RetResult endDiscount(String hivePartnerKey){
		RetResult result = new RetResult();
		result.setCode("fail");
		if(ParamConstants.HIVE_PARTNER_KEY.equals(hivePartnerKey)){  //鉴权
			int suc = this.discountService.endDiscount();
			if(suc>0){
				result.setCode("success");
				result.setMessage("Operation success");
			}else{
				result.setMessage("Operation failed");
			}
		}else{
			result.setMessage("Authentication failure");
		}
		LOG.info("HIVE_PARTNER_KEY="+ParamConstants.HIVE_PARTNER_KEY+", hivePartnerKey："+hivePartnerKey);
		return result;
	}

}
