package main.java.com.hiveview.service.liveTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.liveTask.LiveTaskDetailDao;

@Service
public class LiveTaskDetailService {

	@Autowired
	LiveTaskDetailDao liveTaskDetailDao;

}
