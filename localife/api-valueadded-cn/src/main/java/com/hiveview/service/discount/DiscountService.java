package com.hiveview.service.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.discount.DiscountDao;
import com.hiveview.entity.discount.Discount;

/**
 * Title：DiscountService.java
 * Description：充送管理服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月30日 下午2:57:35
 */
@Service
public class DiscountService {

	@Autowired
	DiscountDao discountDao;
	
	
	/**
	 * 根据充值金额返回该冲送信息
	 * @param rechargeAmount
	 * @return
	 */
	public Discount getDiscountInfo(Integer rechargeAmount){
		return this.discountDao.getDiscountInfo(rechargeAmount);
	}
	
	/**
	 * 活动麦粒耗尽，终止优惠活动
	 * @return
	 */
	public int endDiscount(){
		return this.discountDao.endDiscount();
	}
}
