package com.hiveview.dao.discount;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.discount.Discount;

public interface DiscountDao extends BaseDao<Discount>{

	public Discount getDiscountInfo(Integer rechargeAmount);//根据充值金额返回该冲送信息
	
	public int endDiscount();//活动麦粒耗尽，终止优惠活动
	
}
