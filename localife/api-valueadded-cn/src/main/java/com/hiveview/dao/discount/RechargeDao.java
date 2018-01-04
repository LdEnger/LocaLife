package com.hiveview.dao.discount;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.discount.Recharge;

public interface RechargeDao extends BaseDao<Recharge>{
	
	public List<Recharge> getAllEnabledRecharge(); //获取所有可用的充值金额

}
