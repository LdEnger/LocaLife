package test.java.com.hiveview.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hiveview.dao.live.ChargingInfoDao;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.test.base.BaseTest;

public class ChargingInfoDaoTest extends BaseTest {

	@Autowired
	ChargingInfoDao dao;

	@Test
	public void getBatchOpenLiveData() {
		VipPackagePriceVo result = dao.getChargingInfoByProductId(-9999);
		System.out.println("result=>" + result);
	}

}
