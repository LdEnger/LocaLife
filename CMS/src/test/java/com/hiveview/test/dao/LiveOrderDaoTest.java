package test.java.com.hiveview.test.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.dao.liveTask.LiveTaskDetailDao;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.liveTask.LiveTaskDetail;
import com.hiveview.pay.util.DateUtils;
import com.hiveview.test.base.BaseTest;

public class LiveOrderDaoTest extends BaseTest {

	@Autowired
	LiveOrderDao dao;
	@Autowired
	LiveTaskDetailDao taskDetailDao;

	@Test
	public void testDetailDao() {
		LiveTaskDetail detailTask = new LiveTaskDetail();
		detailTask.setTaskId("201605171017071101");
		System.out.println(taskDetailDao.getTaskDetailListByTaskId(detailTask));
	}

	// @Test
	public void insertLiveOrderDaoTest() {
		LiveOrder liveOrder = new LiveOrder();
		liveOrder.setLiveOrderId("aaaaaqqaaaaa");
		liveOrder.setStatus(2);
		liveOrder.setStatusName("已开通");
		liveOrder.setProductId(2001);
		liveOrder.setProductName("直播包年");
		liveOrder.setChargingId(10);
		liveOrder.setChargingName("直播计费");
		liveOrder.setChargingPrice(6500);
		liveOrder.setChargingDuration(86400);
		liveOrder.setChargingImg("aaaaqqaaaaaaaaaaa");
		liveOrder.setSubmitTime(DateUtils.now());
		liveOrder.setOpenTime(DateUtils.now());
		liveOrder.setCloseTime(DateUtils.now());
		liveOrder.setOpenuid(10010);
		liveOrder.setOpenname("一零零一零");
		liveOrder.setUid(10086);
		liveOrder.setUname("一零零八六");
		liveOrder.setMac("dx:as:As:10:w2:a4");
		liveOrder.setSn("DMS14513213213");
		liveOrder.setDevicecode("qwee4512sad");
		System.out.println(dao.save(liveOrder));
	}

	// @Test
	public void getLivezList() {
		LiveOrder liveOrder = new LiveOrder();
		List<LiveOrder> result = dao.getLiveList(liveOrder);
		System.out.println("result=>" + result);
	}

	// @Test
	public void updateLiveOrderByStstus() {
		System.out.println(dao.updateLiveOrderStstus(2, "1001201603091558456261003", "2016-05-10", null));
	}

	// @Test
	public void countExist() {
		LiveOrder liveOrder = new LiveOrder();
		liveOrder.setProductId(50);
		liveOrder.setMac("14:3D:F2:27:03:D1");
		liveOrder.setSn("DMA30304141227066");
		int result = dao.countExist(liveOrder);
		System.out.println("result=>" + result);
	}

	// @Test
	public void deleteLiveOrderByOrderId() {
		String orderId = "1001201604221106063561001";
		int result = dao.deleteLiveOrderByOrderId(orderId);
		System.out.println("result=>" + result);
	}
}
