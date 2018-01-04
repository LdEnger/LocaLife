package com.hiveview.entity.vo.api;

import java.util.Date;

public class AwardDetailVo {
	private Integer awardProperty;// int
	private String discountType;
	private String userId;// String
	private Integer detailId;// int
	private String awardCode;// varchar
	private String awardRemark;
	private String awardType;// varchar
	private String awardName;// varchar
	private String awardDesc;
	private Date playTime;
	private Date availableBeginTime;// varchar
	private Date availableEndTime;// varchar
	private String awardPicUrl;
	private Integer orderStatus;// int
	private String userPhone;// varchar
	private String userAddr;// varchar
	private String realName;// varchar
	private String qrCodeUrl;// varchar
	private Integer awardDiscount;
	private String awardVideoName;
	private Float awardPrice;

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Date getAvailableBeginTime() {
		return availableBeginTime;
	}

	public void setAvailableBeginTime(Date availableBeginTime) {
		this.availableBeginTime = availableBeginTime;
	}

	public Date getAvailableEndTime() {
		return availableEndTime;
	}

	public void setAvailableEndTime(Date availableEndTime) {
		this.availableEndTime = availableEndTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

	public String getAwardDesc() {
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}

	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}

	public String getAwardPicUrl() {
		return awardPicUrl;
	}

	public void setAwardPicUrl(String awardPicUrl) {
		this.awardPicUrl = awardPicUrl;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Integer getAwardDiscount() {
		return awardDiscount;
	}

	public void setAwardDiscount(Integer awardDiscount) {
		this.awardDiscount = awardDiscount;
	}

	public String getAwardVideoName() {
		return awardVideoName;
	}

	public void setAwardVideoName(String awardVideoName) {
		this.awardVideoName = awardVideoName;
	}

	public Float getAwardPrice() {
		return awardPrice;
	}

	public void setAwardPrice(Float awardPrice) {
		this.awardPrice = awardPrice;
	}

}
