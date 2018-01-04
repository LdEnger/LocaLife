package test.java.com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.activity.ActivityDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.test.base.BaseTest;

public class ActivityDaoTest extends BaseTest {

	@Autowired
	ActivityDao activityDao;
	
	@Test
	public void getActivityById(){
		Activity activity = new Activity();
		activity .setId(4);
		activity = activityDao.getActivityById(4);
		System.out.println(activity);
	}
}
