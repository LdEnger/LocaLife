package main.java.com.hiveview.entity.card;

import java.io.Serializable;

import com.hiveview.entity.Entity;

/**
 * Title：新boss开通订单数据存储
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-3-22下午5:29:43
 */
public class BossOrderNew extends Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderId;//CUSTOMER_ORDER_ID
	private String customerId;//宽带证号
	private String customerName;//客户名称
	private String productId;//套餐标识,与product表ID没有硬性关系
	private String productName;//套餐名称
	private String amount;//金额
	private String ctime;//创建时间
	private String cuser;//创建操作员
	private String cgroup;//创建组织
	private String startTime;//套餐生效时间
	private String endTime;//套餐失效时间
	private String cityName;//地市名称
	private String cityId;//地市标识
	private String phone;//联系电话
	private String mac;
	private String sn;
	private String orderType;//操作类型
	private String orderTypeId;//业务类型标识
	private String serviceFlag;//服务标识
	private String activityCode;
	private Integer status;//1初始状态2产码3激活4退订5错误数据
	private String msg;//状态说明
	private Integer branchId;//分公司ID
	private Integer bossType;//1new 2old
	private Integer excelId;//excelFileId
	private Integer activityId;//关联活动包ID
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
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
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the ctime
	 */
	public String getCtime() {
		return ctime;
	}
	/**
	 * @param ctime the ctime to set
	 */
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	/**
	 * @return the cuser
	 */
	public String getCuser() {
		return cuser;
	}
	/**
	 * @param cuser the cuser to set
	 */
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	/**
	 * @return the cgroup
	 */
	public String getCgroup() {
		return cgroup;
	}
	/**
	 * @param cgroup the cgroup to set
	 */
	public void setCgroup(String cgroup) {
		this.cgroup = cgroup;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
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
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the orderTypeId
	 */
	public String getOrderTypeId() {
		return orderTypeId;
	}
	/**
	 * @param orderTypeId the orderTypeId to set
	 */
	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	/**
	 * @return the serviceFlag
	 */
	public String getServiceFlag() {
		return serviceFlag;
	}
	/**
	 * @param serviceFlag the serviceFlag to set
	 */
	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}
	/**
	 * @return the activityCode
	 */
	public String getActivityCode() {
		return activityCode;
	}
	/**
	 * @param activityCode the activityCode to set
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the bossType
	 */
	public Integer getBossType() {
		return bossType;
	}
	/**
	 * @param bossType the bossType to set
	 */
	public void setBossType(Integer bossType) {
		this.bossType = bossType;
	}
	/**
	 * @return the excelId
	 */
	public Integer getExcelId() {
		return excelId;
	}
	/**
	 * @param excelId the excelId to set
	 */
	public void setExcelId(Integer excelId) {
		this.excelId = excelId;
	}
	/**
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}
