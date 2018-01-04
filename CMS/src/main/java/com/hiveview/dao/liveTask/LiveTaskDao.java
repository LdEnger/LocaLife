package main.java.com.hiveview.dao.liveTask;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.liveTask.LiveTask;

public interface LiveTaskDao extends BaseDao<Object> {

	List<LiveTask> getList(LiveTask task);

	int count(LiveTask task);

	int save(LiveTask task);

	int updateCloseTime(LiveTask task);

	List<LiveTask> getListByZone(LiveTask task);

	int countByZone(LiveTask task);
}
