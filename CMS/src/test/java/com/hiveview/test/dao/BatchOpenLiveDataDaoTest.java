package test.java.com.hiveview.test.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.live.BatchOpenLiveDataDao;
import com.hiveview.entity.vo.BatchOpenLiveVo;
import com.hiveview.test.base.BaseTest;

public class BatchOpenLiveDataDaoTest extends BaseTest {

	@Autowired
	BatchOpenLiveDataDao dao;

	@Test
	public void getBatchOpenLiveData() {
		List<BatchOpenLiveVo> result = dao.getBatchOpenLiveData();
		System.out.println("result=>" + result);
	}

	@Test
	public void updateType() {
		int result = dao.updateType(123456789, 1);
		System.out.println("result=>" + result);
	}

}
