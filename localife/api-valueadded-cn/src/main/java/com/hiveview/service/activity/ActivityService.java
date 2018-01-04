package com.hiveview.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.bo.ScriptPage;

/**
 * Title：ActivityService.java
 * Description：活动管理服务类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年11月26日 下午7:34:02
 */
@Service
public class ActivityService {

	@Autowired
	ActivityDao activityDao;
	
	//获取活动列表
	public ScriptPage getActivityList(Activity activity) {
		List<Activity> rows = activityDao.getList(activity);
		int total = activityDao.count(activity);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	//添加活动
	public boolean add(Activity activity){
		int suc = this.activityDao.save(activity);
		return suc > 0 ? true:false;
	}
	
	//修改活动
	public boolean update(Activity activity){
		int suc = this.activityDao.update(activity);
		return suc > 0 ? true:false;
	}
	
	//删除活动
	public boolean del(Activity activity){
		int suc = this.activityDao.delete(activity);
		return suc > 0 ? true:false;
	}
	
	//获取所有有效的活动列表
	public List<Activity> getAllList(){
		return this.activityDao.getAllList();
	}
	
	
	public int bindOrder(String mac,String sn,String cardPwd,String partnerOrderId){
		
		return 1;
	}
	
}
