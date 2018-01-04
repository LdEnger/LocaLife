package com.hiveview.entity.card;

import java.io.Serializable;
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
	private String activationCode; //激活码
	private Integer status; //卡状态，1：未激活 2：已激活 3:已注销  4：已退订 5：已过期
	private Date createTime; //卡生成时间
	private Date activationTime; //激活时间
	private Date cancelTime; //注销时间
	private String terminalMac; //终端MAC
	private String terminalSn; //终端SN
	private Integer uid;//用户ID
	private String devicecode; //设备码
	private Integer effectiveTimeLength; //有效时长 天
	private Date updateTime; //更新时间
	private Integer delStatus; //软删除状态
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
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
	public Date getActivationTime() {
		return activationTime;
	}
	public void setActivationTime(Date activationTime) {
		this.activationTime = activationTime;
	}
	public Date getCancelTime() {
		return cancelTime;
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
	
}
