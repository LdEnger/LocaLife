package com.hiveview.service.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.discount.RechargeDao;
import com.hiveview.entity.discount.Discount;
import com.hiveview.entity.discount.Recharge;

/**
 * Title：RechargeService.java
 * Description：充值信息服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月31日 下午3:43:20
 */
@Service
public class RechargeService {
	
	@Autowired
	RechargeDao rechargeDao;
	@Autowired
	DiscountService discountService;
	
	/**
	 * 获取所有能用的充值金额
	 * @return
	 */
	public List<Recharge> getAllEnabledRecharge(){
		List<Recharge> rechargeList = this.rechargeDao.getAllEnabledRecharge();
		for (Recharge recharge : rechargeList) {
			Integer rechargeAmount = recharge.getRechargeAmount();
			Discount discount = discountService.getDiscountInfo(rechargeAmount);
			if(discount!=null){
				Integer discountAmount = discount.getDiscountAmount();
				String imgUrl = discount.getImgUrl();
				System.out.println("充值金额为"+rechargeAmount+"时，可以折扣"+discountAmount);
				recharge.setDiscountAmount(discountAmount);
				recharge.setImgUrl(imgUrl);
			}
		}
		return rechargeList;
	}

}
