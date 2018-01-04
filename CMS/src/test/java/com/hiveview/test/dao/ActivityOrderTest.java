package test.java.com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.activity.ActivityOrderDao;
import com.hiveview.entity.activity.ActivityOrder;
import com.hiveview.test.base.BaseTest;

public class ActivityOrderTest extends BaseTest {

	@Autowired
	ActivityOrderDao dao;
	
	@Test
	public void insertActivity(){
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.activityOrderId="asdasd";
		activityOrder.activityOrderStatus=1;
		activityOrder.activityOrderStatusName="asdasd";
		activityOrder.activityOrderType=1;
		activityOrder.activityOrderTypeName="asdsa";
		activityOrder.chargingId=1;
		activityOrder.chargingDuration=10086;
		activityOrder.chargingImg="asdasd";
		activityOrder.chargingName="	qweqw";
		activityOrder.chargingPrice=11;
		activityOrder.activityTime="2015-01-01 10:10:10";
		activityOrder.submitTime="2015-01-01 10:10:10";
		activityOrder.activityTime="2015-01-01 10:10:10";
		activityOrder.uid=1000;
		activityOrder.mac="asdasdsad";
		activityOrder.sn="asdasd";
		activityOrder.devicecode="asdasd";
		dao.save(activityOrder);
		
	}
	
	@Test
	public void getTest(){
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.activityOrderId="2201512091604558";
		activityOrder = dao.get(activityOrder);
		System.out.println(activityOrder.toString());
	}
	
	@Test
	public void updateTest(){
		ActivityOrder activityOrder = new ActivityOrder();
		activityOrder.activityOrderId="2201512091604558";
		activityOrder = dao.get(activityOrder);
		activityOrder.activityOrderStatus=8;
		activityOrder.activityOrderStatusName="qqqqqqqq";
		dao.update(activityOrder);
	}
	
}
