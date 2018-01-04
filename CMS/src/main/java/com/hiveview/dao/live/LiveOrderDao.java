package main.java.com.hiveview.dao.live;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.live.LiveOrder;

public interface LiveOrderDao extends BaseDao<LiveOrder> {

	List<LiveOrder> getLiveList(LiveOrder liveOrder);

	int updateLiveOrderStstus(@Param("status") int status, @Param("liveOrderId") String liveOrderId, @Param("startTime") String startTime, @Param("closeTime") String closeTime);

	int countExist(LiveOrder liveOrder);

	int deleteLiveOrderByOrderId(String orderId);
	
	int updateLiveOrderRefundStstus(LiveOrder liveOrde);

	List<LiveOrder> getLiveListByZone(LiveOrder liveOrder);

	int countByZone(LiveOrder liveOrder);

	int rapInfo();
}
