package main.java.com.hiveview.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.entity.activity.ActivityOrder;

@Service
public class ActivityOrderService {
	
	@Autowired
	ActivityOrderDao aoDao;
	
	public List<ActivityOrder> getUnsucList(){
		return this.aoDao.getUnsucList();
	}
	
	public boolean changeOrderStatus(String activityOrderId){
		int suc = this.aoDao.changeOrderStatus(activityOrderId);
		return suc > 0 ? true:false;
	}

}
