package main.java.com.hiveview.entity.live;

import java.io.Serializable;
import java.util.List;

import com.hiveview.entity.Entity;

public class LiveOrder extends Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String liveOrderId;// 直播订购关系
	private Integer status;// 订单状态
	private String statusName;// 直播订购关系
	private Integer recordStatus;// 活动记录状态 1：可查询 2：不可查询
	private Integer productId;// 产品id
	private String productName;// 产品名称
	private Integer chargingId;// 计费id
	private String chargingName;// 计费名称
	private Integer chargingPrice;// 计费价格
	private Integer chargingDuration;// 计费时长
	private String chargingImg;// 图片路径
	private String submitTime;// 提交时间
	private String openTime;// 开通时间
	private String closeTime;// 关闭时间
	private String openProvince;// 开通省
	private String openProvinceName;// 开通省名称
	private String openCity;// 开通市
	private String openCityName;// 开通省名称
	private Integer branchId;// 分公司ID
	private String branchName;// 分公司名称
	private Integer hallId; // 营业厅ID
	private String hallName;// 营业厅名称
	private Integer openuid;// 开通人员id
	private String openname;// 开通人员name
	private Integer uid;// 用户id
	private String uname;// 用户名称
	private String mac;// mac
	private String sn;// sn
	private String devicecode; // 设备码
	private int chargingDurationYear;// 时长-年
	private int chargingDurationMonth;// 时长-月
	private int chargingDurationDay;// 时长-日
	private Integer orderType;
	private String orderTypeName;
	private String lastUser;
	private String lastTime;
	private List<Integer> branchIdList;
	private Integer openZoneId;
	private String openZoneName;

	public Integer getOpenZoneId() {
		return openZoneId;
	}

	public void setOpenZoneId(Integer openZoneId) {
		this.openZoneId = openZoneId;
	}

	public String getOpenZoneName() {
		return openZoneName;
	}

	public void setOpenZoneName(String openZoneName) {
		this.openZoneName = openZoneName;
	}

	public List<Integer> getBranchIdList() {
		return branchIdList;
	}

	public void setBranchIdList(List<Integer> branchIdList) {
		this.branchIdList = branchIdList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLiveOrderId() {
		return liveOrderId;
	}

	public void setLiveOrderId(String liveOrderId) {
		this.liveOrderId = liveOrderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getChargingId() {
		return chargingId;
	}

	public void setChargingId(Integer chargingId) {
		this.chargingId = chargingId;
	}

	public String getChargingName() {
		return chargingName;
	}

	public void setChargingName(String chargingName) {
		this.chargingName = chargingName;
	}

	public Integer getChargingPrice() {
		return chargingPrice;
	}

	public void setChargingPrice(Integer chargingPrice) {
		this.chargingPrice = chargingPrice;
	}

	public Integer getChargingDuration() {
		return chargingDuration;
	}

	public void setChargingDuration(Integer chargingDuration) {
		this.chargingDuration = chargingDuration;
	}

	public String getChargingImg() {
		return chargingImg;
	}

	public void setChargingImg(String chargingImg) {
		this.chargingImg = chargingImg;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getOpenProvince() {
		return openProvince;
	}

	public void setOpenProvince(String openProvince) {
		this.openProvince = openProvince;
	}

	public String getOpenProvinceName() {
		return openProvinceName;
	}

	public void setOpenProvinceName(String openProvinceName) {
		this.openProvinceName = openProvinceName;
	}

	public String getOpenCity() {
		return openCity;
	}

	public void setOpenCity(String openCity) {
		this.openCity = openCity;
	}

	public String getOpenCityName() {
		return openCityName;
	}

	public void setOpenCityName(String openCityName) {
		this.openCityName = openCityName;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getHallId() {
		return hallId;
	}

	public void setHallId(Integer hallId) {
		this.hallId = hallId;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public Integer getOpenuid() {
		return openuid;
	}

	public void setOpenuid(Integer openuid) {
		this.openuid = openuid;
	}

	public String getOpenname() {
		return openname;
	}

	public void setOpenname(String openname) {
		this.openname = openname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public int getChargingDurationYear() {
		return chargingDurationYear;
	}

	public void setChargingDurationYear(int chargingDurationYear) {
		this.chargingDurationYear = chargingDurationYear;
	}

	public int getChargingDurationMonth() {
		return chargingDurationMonth;
	}

	public void setChargingDurationMonth(int chargingDurationMonth) {
		this.chargingDurationMonth = chargingDurationMonth;
	}

	public int getChargingDurationDay() {
		return chargingDurationDay;
	}

	public void setChargingDurationDay(int chargingDurationDay) {
		this.chargingDurationDay = chargingDurationDay;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

}
