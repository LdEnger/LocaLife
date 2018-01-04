package com.hiveview.service.live;

import org.springframework.stereotype.Service;

import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.util.StringUtils;

@Service
public class LiveOrderBuildService {
	
	public LiveOrder buildLiveOrder(SysUser sysUser,UserVo user,VipPackagePriceVo vipPackagePriceVo,String liveOrderId,int status){
		LiveOrder liveOrder = new LiveOrder();
		liveOrder.setLiveOrderId(liveOrderId);
		if(status==1){
			liveOrder.setStatus(status);
			liveOrder.setStatusName("已提交");
		}else if(status==2){
			liveOrder.setStatus(status);
			liveOrder.setStatusName("已激活");
		}
		liveOrder.setProductId(vipPackagePriceVo.productId);
		liveOrder.setProductName(vipPackagePriceVo.productName);
		liveOrder.setChargingId(vipPackagePriceVo.chargingId);
		liveOrder.setChargingName(vipPackagePriceVo.chargingName);
		liveOrder.setChargingPrice(vipPackagePriceVo.chargingPrice);
		liveOrder.setChargingDuration(vipPackagePriceVo.chargingDuration);
		liveOrder.setChargingImg(vipPackagePriceVo.chargingPic);
		liveOrder.setSubmitTime(StringUtils.getRealTimeString());
		liveOrder.setOpenProvince(sysUser.getProvinceCode());
		liveOrder.setOpenProvinceName(sysUser.getProvinceName());
		liveOrder.setOpenCity(sysUser.getCityCode());
		liveOrder.setOpenCityName(sysUser.getCityName());
		liveOrder.setBranchId(sysUser.getBranchId());
		liveOrder.setBranchName(sysUser.getBranchName());
		liveOrder.setHallId(sysUser.getHallId());
		liveOrder.setHallName(liveOrder.getHallName());
		liveOrder.setOpenuid(sysUser.getUserId());
		liveOrder.setOpenname(sysUser.getUserName());
		liveOrder.setUid(user.getId());
		liveOrder.setUname(user.getUserName());
		liveOrder.setMac(user.getMac());
		liveOrder.setSn(user.getSn());
		liveOrder.setDevicecode(user.getDevicecode());
		return liveOrder;
	}
	
}
