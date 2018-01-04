package main.java.com.hiveview.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.pay.entity.bo.OpResult;
import com.hiveview.service.RedisService;



@Service
public class ActivityRecordService {

	@Autowired
	ActivityOrderDao activityOrderDao;
	@Autowired
	RedisService redisService;
	
	private String USER_ACTIVITY_KEY_PREFIX = "uid_activity_";	
	
	/**
	* 获取用户参与活动订单记录
	* @param uid
	* @return
	*/ 
	public OpResult getUserActivity(int uid){
		long expire = 3*60L;//三分钟
		String key = USER_ACTIVITY_KEY_PREFIX + uid;
		List<ActivityOrder> list = null;
		if (!redisService.exists(key)) {
			ActivityOrder activityOrder = new ActivityOrder();
			activityOrder.uid = uid;
			activityOrder.activityOrderStatus=2;//
			activityOrder.recordStatus = 0;
			list = activityOrderDao.getList(activityOrder);
			redisService.rPush(key, list, ActivityOrder.class, expire);
		}else{
			list= redisService.lRange(key,0L,-1L, ActivityOrder.class);
		}
		OpResult op = new OpResult();
		op.setResult(list);
		return op;
	}
	
	/**
	* 删除指定用户的活动参与记录
	* @param uid
	* @return
	*/ 
	public OpResult clearAllRecord(String uid,String activityOrderType){
		String key = USER_ACTIVITY_KEY_PREFIX + uid;
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.uid = Integer.parseInt(uid);
		activityOrder.activityOrderStatus=2;
		activityOrder.recordStatus = 1;
		if(!StringUtils.isEmpty(activityOrderType)){
			activityOrder.activityOrderType =  Integer.parseInt(activityOrderType);
		}
		int result = activityOrderDao.clearRecord(activityOrder);
		redisService.del(key);
		OpResult op = new OpResult();
		op.setResult(result);
		return op;
	}
	
	/**
	* 删除用户指定的活动参与记录
	* @param uid
	* @param orderId
	* @return
	*/ 
	public OpResult delRecordByOrderId(String uid,String orderId){
		String key = USER_ACTIVITY_KEY_PREFIX + uid;
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.uid = Integer.parseInt(uid);
		activityOrder.activityOrderStatus=2;
		activityOrder.recordStatus = 1;
		activityOrder.activityOrderId = orderId;
		int result = activityOrderDao.clearRecord(activityOrder);
		redisService.del(key);
		OpResult op = new OpResult();
		op.setResult(result);
		return op;
	}
}
