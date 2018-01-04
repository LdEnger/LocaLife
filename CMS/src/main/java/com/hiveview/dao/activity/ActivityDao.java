package main.java.com.hiveview.dao.activity;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityEx;

public interface ActivityDao extends BaseDao<Activity> {
	
	List<Activity> getAllList();
	
	Activity getActivityById(Integer activityId);
	
	List<Activity> getEffectiveActivity();
	
	int saveActivityEx(ActivityEx ex);
	int updateActivityEx(ActivityEx ex);
	List<ActivityEx> getActivityExList(ActivityEx ex);
	int countActivityEx(ActivityEx ex);

}
