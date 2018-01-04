package com.hiveview.service.live;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.dao.sys.SysUserDao;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.service.api.LiveApi;
import com.hiveview.service.api.PassportApi;

@Service
public final class LiveService {

	@Autowired
	ChargePriceApi chargPriceApi;
	@Autowired
	LiveOrderDao liveOrderDao;
	@Autowired
	PassportApi passportApi;
	@Autowired
	SysUserDao sysUserDao;
	@Autowired
	LiveApi liveApi;
	@Autowired
	LiveOrderBuildService liveOrderBuildService;
	
	static final int INIT_STATUS = 1;
	static final int SUCC_STATUS = 2;
	static final int ERR_STATUS = 3;

	public OpResult openService(String mac,String sn,int openuid,int productId) {
		OpResult op = new OpResult();
		UserVo user = passportApi.getUserInfo(mac, sn);
		if(user==null){
			return new OpResult(OpResultTypeEnum.SYSERR,"无法获取用户信息");
		}
		SysUser sysUser= sysUserDao.getSysUserById(openuid);
		if(sysUser==null){
			return new OpResult(OpResultTypeEnum.SYSERR,"非法操作者");
		}
		VipPackagePriceVo packagePrice = chargPriceApi.getPackagePriceByProductId(productId);
		String liveOrderId = this.generateLiveOrderId();
		LiveOrder liveOrder= liveOrderBuildService.buildLiveOrder(sysUser, user, packagePrice, liveOrderId, INIT_STATUS);
		int insert = liveOrderDao.save(liveOrder);
		if(insert != 1){
			return new OpResult(OpResultTypeEnum.SYSERR,"本地开通直播失败"); 
		}
		String result = liveApi.openLiveService(sysUser, user, packagePrice, liveOrderId);
		if("success".equals(result)){
			liveOrderDao.updateLiveOrderByStstus(SUCC_STATUS, liveOrderId);
			op.setResult(liveOrder);
			return op;
		}else{
			liveOrderDao.updateLiveOrderByStstus(ERR_STATUS, liveOrderId);
			return new OpResult(OpResultTypeEnum.SYSERR,result);
		}
	}

	/**
	 * 获取直播产品列表
	 * 
	 * @return
	 */
	public List<VipPackagePriceVo> getLiveChargPriceList() {
		return chargPriceApi.getLiveChargPriceList();
	}

	/**
	 * 获取直播列表
	 * 
	 * @param liveOrder
	 */
	public ScriptPage getLiveList(LiveOrder liveOrder) {
		try {
			List<LiveOrder> rows = liveOrderDao.getLiveList(liveOrder);
			int total = liveOrderDao.count(liveOrder);
			ScriptPage scriptPage = new ScriptPage();
			scriptPage.setRows(rows);
			scriptPage.setTotal(total);
			return scriptPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static AtomicInteger ATOMIC = new AtomicInteger(1000);
	
	private String generateLiveOrderId() {
		int orderAtomic = ATOMIC.incrementAndGet();
		String orderTime = new DateTime().toString("yyyyMMddHHmmssSSS");
		String orderId = "1001" + orderTime + orderAtomic;
		return orderId;
	}

}
