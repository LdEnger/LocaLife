package main.java.com.hiveview.entity.card;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.hiveview.entity.Entity;


public class Card extends Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; //
	private String cardOrderId;//卡激活后即绑定的订单号
	private Integer cardOrderStatus;//绑定订单状态，1：已下单 2：已激活 3：激活失败 4：已退单 
	private String userName; //用户名（使用者）
	private String creatorName; //创建者
	private Integer cardFromHall; //活动卡券来源营业厅ID
	private String hallName; //活动卡券来源营业厅名称
	private Integer cardFromBranch; //活动卡券来源分公司ID
	private String branchName; //活动卡券来源分公司名称
	private Integer activityId; //活动ID
	private String activityName; //活动名
	private String cardNumber;  //卡号
	private String activationCode; //激活码
	private Integer status; //卡状态，1：未激活 2：已激活 3:已注销  4：已退订 5：已过期
	private String createTime; //卡生成时间
	private String activationTime; //激活时间
	private Date cancelTime; //注销时间
	private String terminalMac; //终端MAC
	private String terminalSn; //终端SN
	private Integer uid;//用户ID
	private String devicecode; //设备码
	private Integer effectiveTimeLength; //有效时长 天 (对应activity.java中的duration)
	private Date updateTime; //更新时间
	private Integer delStatus; //软删除状态
	public Integer zoneId;//生成卡券的战区ID
	public String zoneName;//生成卡券的战区名称
	public Integer cityId;//生成卡券的城市ID
	private Timestamp effectTime; //订单实际生效时间
	private Timestamp invalidTime;//订单实际失效时间
	private Integer queryMethod;//统计专用，1：按激活时间查询，2：按生效时间查询 3:按注销时间查询
	private Integer price;//价格 
	private Integer settlementCycle;//结算周期
	private Integer autoActiveTimeLength;//自生成之日起至自动过期之间间隔时间，单位：天
	private Integer isAutoActive;//是否是自动激活，0：不是，1：是
	private String userNum;//用户号（北京分公司填写）
	private String orderNum;//业务单号（北京分公司填写）
	private String activityType;//活动类型
	private Integer source;//来源 0：线下生成（默认） 1：自动化接口生成（支付宝，微信等）3：cms系统生成  4：excel导入
	private String excelName;//导出excel时用到
	
	private String phone;//add by xuhaobo 北分手机号
	private String cardType;// 卡类型vip,live,agio,multi
	
	public String createStopTime; //卡生成时间,用于查询
	public String activationStopTime; //激活时间，用于查询
	
	public String citys;//分公司列表 用于查询
	
	private Integer productId;// 计费包ID  for 财务系统
	private String productName;//计费包名称  for 财务系统
	
	private String versions ;//3.2版本标识
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCardOrderId() {
		return cardOrderId;
	}
	public void setCardOrderId(String cardOrderId) {
		this.cardOrderId = cardOrderId;
	}
	public Integer getCardOrderStatus() {
		return cardOrderStatus;
	}
	public void setCardOrderStatus(Integer cardOrderStatus) {
		this.cardOrderStatus = cardOrderStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getCardFromHall() {
		return cardFromHall;
	}
	public void setCardFromHall(Integer cardFromHall) {
		this.cardFromHall = cardFromHall;
	}
	public Integer getCardFromBranch() {
		return cardFromBranch;
	}
	public void setCardFromBranch(Integer cardFromBranch) {
		this.cardFromBranch = cardFromBranch;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTerminalMac() {
		return terminalMac;
	}
	public void setTerminalMac(String terminalMac) {
		this.terminalMac = terminalMac;
	}
	public String getTerminalSn() {
		return terminalSn;
	}
	public void setTerminalSn(String terminalSn) {
		this.terminalSn = terminalSn;
	}
	public Integer getEffectiveTimeLength() {
		return effectiveTimeLength;
	}
	public void setEffectiveTimeLength(Integer effectiveTimeLength) {
		this.effectiveTimeLength = effectiveTimeLength;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getDevicecode() {
		return devicecode;
	}
	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public String getActivationTime() {
		return activationTime;
	}
	public void setActivationTime(String activationTime) {
		this.activationTime = activationTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Timestamp getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Timestamp effectTime) {
		this.effectTime = effectTime;
	}
	public Timestamp getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}
	public Integer getQueryMethod() {
		return queryMethod;
	}
	public void setQueryMethod(Integer queryMethod) {
		this.queryMethod = queryMethod;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSettlementCycle() {
		return settlementCycle;
	}
	public void setSettlementCycle(Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}
	public Integer getAutoActiveTimeLength() {
		return autoActiveTimeLength;
	}
	public void setAutoActiveTimeLength(Integer autoActiveTimeLength) {
		this.autoActiveTimeLength = autoActiveTimeLength;
	}
	public Integer getIsAutoActive() {
		return isAutoActive;
	}
	public void setIsAutoActive(Integer isAutoActive) {
		this.isAutoActive = isAutoActive;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCreateStopTime() {
		return createStopTime;
	}
	public void setCreateStopTime(String createStopTime) {
		this.createStopTime = createStopTime;
	}
	public String getActivationStopTime() {
		return activationStopTime;
	}
	public void setActivationStopTime(String activationStopTime) {
		this.activationStopTime = activationStopTime;
	}
	/**
	 * @return the citys
	 */
	public String getCitys() {
		return citys;
	}
	/**
	 * @param citys the citys to set
	 */
	public void setCitys(String citys) {
		this.citys = citys;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the versions
	 */
	public String getVersions() {
		return versions;
	}
	/**
	 * @param versions the versions to set
	 */
	public void setVersions(String versions) {
		this.versions = versions;
	}
}
