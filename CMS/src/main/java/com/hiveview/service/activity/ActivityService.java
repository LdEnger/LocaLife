package main.java.com.hiveview.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.dao.sys.ZoneCityDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.activity.ActivityEx;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.sys.ZoneCity;
import com.hiveview.service.RedisService;
import com.hiveview.service.sys.ZoneCityService;

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
	private ActivityDao activityDao;
	@Autowired
	private ZoneCityDao zoneCityDao;
	@Autowired
	private ZoneCityService zoneCityService;
	@Autowired
	RedisService redisService;
	
	//获取活动列表
	public ScriptPage getActivityList(Activity activity,Integer roleId) {
		List<Activity> rows = activityDao.getList(activity);
		if(ParamConstants.ZONE_ROLE==roleId){
			Integer cityCode = activity.getCityId();
			ZoneCity zc = zoneCityDao.getInfoByCity(cityCode);
			List<Branch> bList = zoneCityService.getBranchByZone(zc.getZoneId());
			for (Branch branch : bList) {
				Activity tmp = new Activity();
				Integer branchId = branch.getId();
				if(branchId!=activity.getBranchId()){
					tmp.setBranchId(branch.getId());
					List<Activity> tmpActivity=activityDao.getList(tmp);
					for (Activity act : tmpActivity) {
						if(act!=null){
							rows.add(activityDao.get(act));
						}
					}
				}
			}
		}
		for (Activity activity2 : rows) {
			if(ParamConstants.GROUP_ROLE !=activity2.getOperatorRoleId()){
				Integer cityId = activity2.getCityId();
				if(cityId!=null && cityId!=0){
					ZoneCity zoneCity = zoneCityDao.getInfoByCity(cityId);
					activity2.setZoneId(zoneCity.getZoneId());
					activity2.setZoneName(zoneCity.getZoneName());
				}
			}else{
				activity2.setZoneId(-1);
				activity2.setZoneName("-");
			}
		}
		int total = activityDao.count(activity);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}
	
	public List<Activity> getActivity(Activity activity){
		try {
			List<Activity> rows = activityDao.getList(activity);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//添加活动
	public boolean add(Activity activity){
		int suc = this.activityDao.save(activity);
		return suc > 0 ? true:false;
	}
	
	//修改活动
	public boolean update(Activity activity){
		int suc = this.activityDao.update(activity);
		redisService.del("activity_"+activity.getId());
		return suc > 0 ? true:false;
	}
	
	//删除活动
	public boolean del(Activity activity){
		int suc = this.activityDao.delete(activity);
		redisService.del("activity_"+activity.getId());
		return suc > 0 ? true:false;
	}
	
	//获取所有有效的活动列表
	public List<Activity> getAllList(){
		return this.activityDao.getAllList();
	}
	
	
	public int bindOrder(String mac,String sn,String cardPwd,String partnerOrderId){
		
		return 1;
	}

	/**
	 * 概述：原计费包转移至增值自己维护 
	 * 返回值：boolean
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-6-8下午2:31:01
	 */
	public boolean addex(ActivityEx ex) {
		int suc = this.activityDao.saveActivityEx(ex);
		return suc > 0 ? true:false;
	}

	public boolean updateex(ActivityEx ex) {
		int suc = this.activityDao.updateActivityEx(ex);
		return suc > 0 ? true:false;
	}

	public ScriptPage getActivityExList(ActivityEx ex) {
		List<ActivityEx> rows = activityDao.getActivityExList(ex);
		int total = activityDao.countActivityEx(ex);
		ScriptPage scriptPage = new ScriptPage();
		scriptPage.setRows(rows);
		scriptPage.setTotal(total);
		return scriptPage;
	}

	/**
	 * 概述：
	 * 返回值：List<ActivityEx>
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-6-9下午2:32:19
	 */
	public List<ActivityEx> getActivityExListWithOutPageInfo(ActivityEx ex) {
		return activityDao.getActivityExList(ex);
	}
	
}
