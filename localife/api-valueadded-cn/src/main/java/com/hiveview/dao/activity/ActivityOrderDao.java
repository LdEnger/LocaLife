package com.hiveview.dao.activity;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.ActivityOrder;



public interface ActivityOrderDao extends BaseDao<ActivityOrder>{

	int clearRecord(ActivityOrder activityOrder);
	
	List<ActivityOrder> getUnsucList();
	
	int changeOrderStatus(String activityOrderId);
	
}
