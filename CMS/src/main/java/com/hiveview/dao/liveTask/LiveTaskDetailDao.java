package main.java.com.hiveview.dao.liveTask;

import java.util.List;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.liveTask.LiveTaskDetail;

public interface LiveTaskDetailDao extends BaseDao<Object> {
	
//	public LiveTaskDetail get(@Param("id")int id);

//	public List<LiveTaskDetail> getList(@Param("taskId")int taskId);

	public List<LiveTaskDetail> getTaskDetailListByTaskId(LiveTaskDetail detailTask);

}
