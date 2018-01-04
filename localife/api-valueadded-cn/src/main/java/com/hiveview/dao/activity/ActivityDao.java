package com.hiveview.dao.activity;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.Activity;

public interface ActivityDao extends BaseDao<Activity> {
	
	List<Activity> getAllList();
	
	Activity getActivityById(Integer activityId);
	
	List<Activity> getEffectiveActivity();

}
