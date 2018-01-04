package com.hiveview.dao.live;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.live.LiveOrder;

public interface LiveOrderDao extends BaseDao<LiveOrder>{

	List<LiveOrder> getLiveList(LiveOrder liveOrder);
	
	int updateLiveOrderByStstus(@Param("status") int status, @Param("liveOrderId") String liveOrderId);
	
}
