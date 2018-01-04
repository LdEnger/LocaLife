package main.java.com.hiveview.service.live;

import org.springframework.stereotype.Service;

import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.util.StringUtils;

@Service
public class LiveOrderBuildService {
	/**
	 * 
	 * 组装订单信息
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param sysUser
	 * @param user
	 * @param liveOrder
	 * @param liveOrderId
	 * @param status
	 *            1为订单初始状态
	 * @param orderType
	 *            0开通单，1续费单
	 * @return
	 */
	public LiveOrder buildLiveOrder(SysUser sysUser, UserVo user, LiveOrder liveOrder, String liveOrderId, int status, int orderType) {
		LiveOrder newLiveOrder = new LiveOrder();
		newLiveOrder.setLiveOrderId(liveOrderId);
		if (status == 1) {
			newLiveOrder.setStatus(status);
			newLiveOrder.setStatusName("已提交");
		} else if (status == 2) {
			newLiveOrder.setStatus(status);
			newLiveOrder.setStatusName("已激活");
		}
		newLiveOrder.setProductId(liveOrder.getProductId());
		newLiveOrder.setProductName(liveOrder.getProductName());
		newLiveOrder.setChargingDurationYear(liveOrder.getChargingDurationYear());
		newLiveOrder.setChargingDurationMonth(liveOrder.getChargingDurationMonth());
		newLiveOrder.setChargingDurationDay(liveOrder.getChargingDurationDay());
		newLiveOrder.setSubmitTime(StringUtils.getRealTimeString());
		newLiveOrder.setOpenProvince(sysUser.getProvinceCode());
		newLiveOrder.setOpenProvinceName(sysUser.getProvinceName());
		newLiveOrder.setOpenCity(sysUser.getCityCode());
		newLiveOrder.setOpenCityName(sysUser.getCityName());
		newLiveOrder.setBranchId(liveOrder.getBranchId());
		newLiveOrder.setBranchName(liveOrder.getBranchName());
		newLiveOrder.setHallId(liveOrder.getHallId());
		newLiveOrder.setHallName(liveOrder.getHallName());
		newLiveOrder.setOpenuid(sysUser.getUserId());
		newLiveOrder.setOpenname(sysUser.getUserName());
		newLiveOrder.setUid(user.getId());
		newLiveOrder.setUname(user.getUserName());
		newLiveOrder.setMac(user.getMac().replaceAll(":", "").toUpperCase());
		newLiveOrder.setSn(user.getSn().toUpperCase());
		newLiveOrder.setDevicecode(user.getDevicecode());
		if (0 == orderType) {
			newLiveOrder.setOrderType(0);
			newLiveOrder.setOrderTypeName("开通单");
		} else {
			newLiveOrder.setOrderType(1);
			newLiveOrder.setOrderTypeName("续费单");
		}
		return newLiveOrder;
	}

}
