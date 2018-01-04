package main.java.com.hiveview.dao.live;

import com.hiveview.entity.vo.VipPackagePriceVo;

public interface ChargingInfoDao {

	VipPackagePriceVo getChargingInfoByProductId(int productId);

}
