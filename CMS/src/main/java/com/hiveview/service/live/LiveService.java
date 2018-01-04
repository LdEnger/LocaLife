package main.java.com.hiveview.service.live;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.hiveview.common.ParamConstants;
import com.hiveview.dao.HallDao;
import com.hiveview.entity.Hall;
import com.hiveview.service.sys.ZoneCityService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.dao.BranchDao;
import com.hiveview.dao.live.BatchOpenLiveDataDao;
import com.hiveview.dao.live.ChargingInfoDao;
import com.hiveview.dao.live.LiveOrderDao;
import com.hiveview.entity.Branch;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.bo.ScriptPage;
import com.hiveview.entity.live.LiveOrder;
import com.hiveview.entity.sys.SysUser;
import com.hiveview.entity.vo.BatchOpenLiveVo;
import com.hiveview.entity.vo.UserVo;
import com.hiveview.entity.vo.VipPackagePriceVo;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.service.api.ChargePriceApi;
import com.hiveview.service.api.LiveApi;
import com.hiveview.service.api.PassportApi;

@Service
public class LiveService {

	@Autowired
	LiveApi liveApi;
	@Autowired
	BranchDao branchDao;
	@Autowired
	HallDao hallDao;
	@Autowired
	PassportApi passportApi;
	@Autowired
	LiveOrderDao liveOrderDao;
	@Autowired
	ChargePriceApi chargePriceApi;
	@Autowired
	ChargingInfoDao chargingInfoDao;
	@Autowired
	BatchOpenLiveDataDao batchOpenLiveDataDao;
	@Autowired
	LiveOrderBuildService liveOrderBuildService;
	@Autowired
	private ZoneCityService zoneCityService;

	private static final Logger DATA = LoggerFactory.getLogger("data");

	static final int INIT_STATUS = 1;
	static final int SUCC_STATUS = 2;
	static final int ERR_STATUS = 3;
	static final int REFUND_STATUS = 4;
	static AtomicInteger ATOMIC = new AtomicInteger(1000);

	/**
	 * 单个开通直播
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @param liveOrder
	 * @param sysUser
	 * @return
	 */
	public OpResult openLiveService(LiveOrder liveOrder, SysUser sysUser) {
		if (sysUser == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "请登录后操作");
		}
		UserVo user = passportApi.getUserInfo(liveOrder.getMac(), liveOrder.getSn());
		if (user == null) {
			OpResult opR = new OpResult();
			opR.setCode("510");
			opR.setDesc("无法获取盒子信息,请检查mac,sn是否正确");
			return opR;
		}
		if (ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId()) || ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())) {
			sysUser = this.getBranchInfo(liveOrder.getBranchId(), sysUser);
		}
		String liveOrderId = this.generateLiveOrderId();
		LiveOrder newLiveOrder = liveOrderBuildService.buildLiveOrder(sysUser, user, liveOrder, liveOrderId, INIT_STATUS, 0);
		int countExist = liveOrderDao.countExist(newLiveOrder);
		if (countExist > 0) {
			return new OpResult(OpResultTypeEnum.SYSERR, "已开通该产品包,不能重复开通");
		}
		String closeTime = new DateTime(liveOrder.getCloseTime()).toString("yyyy-MM-dd") + " " + (new DateTime().toString("HH:mm:ss"));
		liveOrder.setCloseTime(closeTime);
		String result = liveApi.openLiveService(sysUser, user, liveOrder, liveOrderId, "0", null);
		if (!"success".equals(result)) {
			// liveOrderDao.updateLiveOrderStstus(ERR_STATUS, liveOrderId,
			// null,null);本地先插入订单，需加上此句
			return new OpResult(OpResultTypeEnum.SYSERR, result);
		}
		int insert = liveOrderDao.save(newLiveOrder);// 后插入订单
		if (insert != 1) {
			DATA.info("[openLiveResponse] openLiveMessage:本地开通直播失败,mac={},sn={},liveOrderId={}", new Object[] { newLiveOrder.getMac(), newLiveOrder.getSn(), liveOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "本地开通直播失败");
		}
		int update = liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrderId, newLiveOrder.getSubmitTime(), liveOrder.getCloseTime());
		newLiveOrder.setOpenTime(newLiveOrder.getSubmitTime());
		newLiveOrder.setCloseTime(liveOrder.getCloseTime());
		if (update != 1) {
			DATA.info("[openLiveResponse] openLiveMessage:更新订单状态失败,mac={},sn={},liveOrderId={}", new Object[] { newLiveOrder.getMac(), newLiveOrder.getSn(), liveOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "更新订单状态失败");
		}
		return new OpResult(newLiveOrder);
	}

	/**
	 * 续费直播
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月9日
	 * @param liveOrder
	 */
	public OpResult renewLive(SysUser sysUser, LiveOrder liveOrder) {
		if (sysUser == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "请登录后操作");
		}
		UserVo user = passportApi.getUserInfo(liveOrder.getMac(), liveOrder.getSn());
		if (user == null) {
			OpResult opR = new OpResult();
			opR.setCode("510");
			opR.setDesc("无法获取盒子信息,请检查mac,sn是否正确");
			return opR;
		}
		if (ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId()) || ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())) {
			sysUser = this.getBranchInfo(liveOrder.getBranchId(), sysUser);
		}
		String liveOrderId = this.generateLiveOrderId();
		LiveOrder renewLiveOrder = liveOrderBuildService.buildLiveOrder(sysUser, user, liveOrder, liveOrderId, INIT_STATUS, 1);
		String responseString = liveApi.renewLiveService(user, liveOrder, liveOrderId);
		if ("error".equals(responseString)) {
			DATA.info("[renewLiveResponse] renewLiveMessage:Message={},mac={},sn={}", new Object[] { responseString, renewLiveOrder.getMac(), renewLiveOrder.getSn() });
			return new OpResult(OpResultTypeEnum.SYSERR, "通知续费失败");
		}
		JSONObject responseJson = JSONObject.parseObject(responseString);
		String success = responseJson.getString("success");
		if (!"true".equals(success)) {
			String message = responseJson.getString("message");
			DATA.info("[renewLiveResponse] renewLiveMessage:Message={},mac={},sn={}", new Object[] { message, renewLiveOrder.getMac(), renewLiveOrder.getSn() });
			return new OpResult(OpResultTypeEnum.SYSERR, message);
		}
		int insert = liveOrderDao.save(renewLiveOrder);
		if (insert != 1) {
			DATA.info("[renewLiveResponse] renewLiveMessage:本地开通直播失败,mac={},sn={},liveOrderId={}", new Object[] { renewLiveOrder.getMac(), renewLiveOrder.getSn(), liveOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "本地开通直播失败");
		}
		JSONObject resultJson = JSONObject.parseObject(responseJson.getString("result"));
		String startTime = resultJson.getString("startTime");
		String endTime = resultJson.getString("endTime");
		liveOrder.setOpenTime(startTime);
		liveOrder.setCloseTime(endTime);
		int update = liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrderId, startTime, endTime);
		if (update != 1) {
			DATA.info("[renewLiveResponse] renewLiveMessage:更新订单状态失败,mac={},sn={},liveOrderId={}", new Object[] { renewLiveOrder.getMac(), renewLiveOrder.getSn(), liveOrderId });
			return new OpResult(OpResultTypeEnum.SYSERR, "更新订单状态失败");
		}
		return new OpResult(liveOrder);
	}

	/**
	 * 退订直播
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年6月6日
	 * @param liveOrder
	 * @return
	 */
	public OpResult refundLive(LiveOrder liveOrder, SysUser sysUser) {
		if (sysUser == null) {
			return new OpResult(OpResultTypeEnum.SYSERR, "请登录后操作");
		}
		String result = liveApi.refundLive(liveOrder);
		if (!"success".equals(result)) {
			DATA.info("[refundLive] refundLiveMessage,liveOrderId={},mac={},sn={},result={}", new Object[] { liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn(), result });
			return new OpResult(OpResultTypeEnum.SYSERR, result);
		}
		liveOrder.setLastUser(sysUser.getUserName());
		liveOrder.setLastTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		try {
			int update = liveOrderDao.updateLiveOrderRefundStstus(liveOrder);
			if (update <= 0) {
				DATA.info("[refundLive] refundLiveMessage:变更订单状态失败,liveOrderId={},mac={},sn={},result={}", new Object[] { liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn(), result });
				return new OpResult(OpResultTypeEnum.DBERR, "变更订单状态失败");
			}
		} catch (Exception e) {
			DATA.info("[refundLive] refundLiveMessage:数据库操作异常,liveOrderId={},mac={},sn={},result={}", new Object[] { liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn(), result });
			return new OpResult(OpResultTypeEnum.DBERR, "变更订单状态失败");
		}

		DATA.info("[refundLive] refundLiveMessage:退订成功,liveOrderId={},mac={},sn={},result={}", new Object[] { liveOrder.getLiveOrderId(), liveOrder.getMac(), liveOrder.getSn(), result });
		return new OpResult(OpResultTypeEnum.SUCC, "退订成功");
	}

	/**
	 * 获取分公司信息
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年6月6日
	 * @param branchId
	 * @param sysUser
	 * @return
	 */
	private SysUser getBranchInfo(Integer branchId, SysUser sysUser) {
		Branch branch = branchDao.getBranchInfoById(branchId);
		if (branch != null) {
			sysUser.setCityCode(branch.getCityCode());
			sysUser.setCityName(branch.getCityName());
			sysUser.setBranchId(branch.getId());
			sysUser.setBranchName(branch.getBranchName());
			sysUser.setProvinceCode(branch.getProvinceCode());
			sysUser.setProvinceName(branch.getProvinceName());
		}
		return sysUser;
	}

	/**
	 * 获取直播列表
	 * 
	 * @param liveOrder
	 */
	public ScriptPage getLiveList(LiveOrder liveOrder) {
		try {
			List<LiveOrder> rows = liveOrderDao.getLiveListByZone(liveOrder);
			int total = liveOrderDao.countByZone(liveOrder);
			ScriptPage scriptPage = new ScriptPage();
			scriptPage.setRows(rows);
			scriptPage.setTotal(total);
			return scriptPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	private String generateLiveOrderId() {
		int orderAtomic = ATOMIC.incrementAndGet();
		String orderTime = new DateTime().toString("yyyyMMddHHmmssSSS");
		String orderId = "1001" + orderTime + orderAtomic;
		return orderId;
	}

	/**
	 * 批量开通乐享直播
	 * 
	 * liumingwei@btte.net 2016年4月15日
	 * 
	 * @return
	 * @throws Exception
	 */
	public OpResult BatchOpenLive() throws Exception {
		List<BatchOpenLiveVo> list = batchOpenLiveDataDao.getBatchOpenLiveData();
		if (list.size() <= 0) {
			return new OpResult(OpResultTypeEnum.SYSERR, "没有可以开通的数据");
		}
		List<LiveOrder> resultList = new ArrayList<LiveOrder>();
		List<BatchOpenLiveVo> noUserList = new ArrayList<BatchOpenLiveVo>();
		List<BatchOpenLiveVo> noSuccessList = new ArrayList<BatchOpenLiveVo>();
		for (BatchOpenLiveVo batchOpenLiveVo : list) {
			UserVo user = passportApi.getUserInfo(batchOpenLiveVo.getMac(), batchOpenLiveVo.getSn());
			if (user == null) {
				batchOpenLiveDataDao.updateType(batchOpenLiveVo.id, 3);// 没有用户信息
				noUserList.add(batchOpenLiveVo);
				continue;
			}
			SysUser sysUser = new SysUser();// 自定义用户信息，查库获取
			sysUser.setUserId(batchOpenLiveVo.getSysUserId());
			sysUser.setUserName(batchOpenLiveVo.getSysUserName());
			sysUser.setProvinceCode(batchOpenLiveVo.getCpChannelPnumber());
			sysUser.setProvinceName(batchOpenLiveVo.getCpChannelPname());
			sysUser.setCityCode(batchOpenLiveVo.getCpChannelCnumber());
			sysUser.setCityName(batchOpenLiveVo.getCpChannelCname());
			sysUser.setBranchId(batchOpenLiveVo.getBranchId());
			sysUser.setBranchName(batchOpenLiveVo.getBranchName());
			sysUser.setHallId(batchOpenLiveVo.getHallId());
			sysUser.setHallName(batchOpenLiveVo.getHallName());
			VipPackagePriceVo packagePrice = chargingInfoDao.getChargingInfoByProductId(batchOpenLiveVo.getProductId());
			if (packagePrice == null) {
				return new OpResult("没有产品包信息，不能开通直播业务。");
			}
			LiveOrder liveOrder = new LiveOrder();
			liveOrder.setProductId(packagePrice.getProductId());
			liveOrder.setProductName(packagePrice.getProductName());
			liveOrder.setChargingDurationYear(packagePrice.getChargingDurationYear());
			liveOrder.setChargingDurationMonth(packagePrice.getChargingDurationMonth());
			liveOrder.setChargingDurationDay(packagePrice.getChargingDurationDay());
			String liveOrderId = this.generateLiveOrderId();
			LiveOrder newLiveOrder = liveOrderBuildService.buildLiveOrder(sysUser, user, liveOrder, liveOrderId, INIT_STATUS, 1);
			int countExist = liveOrderDao.countExist(newLiveOrder);
			if (countExist > 0) {
				continue;
			}
			String result = liveApi.openLiveService(sysUser, user, liveOrder, liveOrderId, "1", batchOpenLiveVo.getOrderStartTime());
			if (!"success".equals(result)) {
				batchOpenLiveDataDao.updateType(batchOpenLiveVo.id, 2);// 未收到返回success
				noSuccessList.add(batchOpenLiveVo);
				continue;
			}
			int insert = liveOrderDao.save(newLiveOrder);
			if (insert != 1) {
				batchOpenLiveDataDao.updateType(batchOpenLiveVo.id, 4);// 本地开通直播失败
				continue;
			}
			String closeTime = com.hiveview.util.DateUtil.getEndTime(new Date(), packagePrice.chargingDurationYear, packagePrice.chargingDurationMonth, packagePrice.chargingDurationDay);
			liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrderId, batchOpenLiveVo.getOrderStartTime(), closeTime);
			batchOpenLiveDataDao.updateType(batchOpenLiveVo.id, 1);// 成功
			resultList.add(newLiveOrder);
		}
		return new OpResult(OpResultTypeEnum.SUCC, "共" + list.size() + "条,成功开通" + resultList.size() + "条,未查到用户信息" + noUserList.size() + "条，未收到success" + noSuccessList.size() + "条");
	}

	/**
	 * 调播控接口查询直播开通状态
	 * 
	 * @param liveOrder
	 * @return
	 */

	public OpResult queryStatus(LiveOrder liveOrder) {
		try {
			String result = liveApi.queryStatus(liveOrder);
			if ("true".equals(result)) {
				String closeTime = com.hiveview.util.DateUtil.getEndTime(new Date(), liveOrder.getChargingDurationYear(), liveOrder.getChargingDurationMonth(), liveOrder.getChargingDurationDay());
				int updateResult = liveOrderDao.updateLiveOrderStstus(SUCC_STATUS, liveOrder.getLiveOrderId(), liveOrder.getOpenTime(), closeTime);
				if (updateResult != 1) {
					return new OpResult(OpResultTypeEnum.SYSERR, "更新状态失败");
				}
				return new OpResult(OpResultTypeEnum.SUCC, "成功开通");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new OpResult(OpResultTypeEnum.SYSERR, "系统异常");
		}
		return new OpResult(OpResultTypeEnum.SUCC, "该订单未成功开通");
	}

	public OpResult deleteLiveOrder(String orderId) {
		int result = liveOrderDao.deleteLiveOrderByOrderId(orderId);
		if (result == 1) {
			return new OpResult(OpResultTypeEnum.SUCC, "订单删除成功");
		}
		return new OpResult(OpResultTypeEnum.SYSERR, "订单不存在");
	}

	public String getYearMonthDay(String endTime) {
		int[] ymd = com.hiveview.util.DateUtil.getYMDArray(new DateTime().toString("yyyy-MM-dd"), endTime);
		return ymd[0] + "年" + ymd[1] + "月" + ymd[2] + "天";
	}

	public String getEndDate(int year, int month, int day) {
		String date = com.hiveview.util.DateUtil.getEndTime(new Date(), year, month, day);
		return date;
	}

	/**
	 * 获取分公司列表
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @return
	 */
	public List<Branch> getBranchList(SysUser sysUser) {
		List<Branch> list = new ArrayList<Branch>();
		if(ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId())){
			list = branchDao.getBranchList();
		}else if (ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())){
			list = zoneCityService.getBranchByZone(sysUser.getZoneId());
		}else {
			list.add(branchDao.getBranchInfoById(sysUser.getBranchId()));
		}
		return list;
	}

	public List<Hall> getHallList(SysUser sysUser) {
		List<Hall> list = new ArrayList<Hall>();
		if(ParamConstants.GROUP_ROLE.equals(sysUser.getRoleId())){
			list = hallDao.getList();
		}else if (ParamConstants.ZONE_ROLE.equals(sysUser.getRoleId())){
			list = zoneCityService.getHallByZone(sysUser.getZoneId());
		}else if(ParamConstants.BRANCH_ROLE.equals(sysUser.getRoleId())){
			list = hallDao.getHallList(sysUser.getBranchId());
		}else{
			list.add(hallDao.getById(sysUser.getHallId()));
		}
		return list;
	}

	/**
	 * 获取产品包列表
	 * 
	 * @author liumingwei@btte.net
	 * @date 2016年5月11日
	 * @return
	 */
	public List<VipPackagePriceVo> getProductList(SysUser sysUser) {
		return chargePriceApi.getLiveProductPacksList(sysUser);
	}

	public int rapInfo() {
		try {
			return liveOrderDao.rapInfo();
		} catch (Exception e) {
			DATA.info("rapInfo异常", e);
		}
		return -1;
	}
}
