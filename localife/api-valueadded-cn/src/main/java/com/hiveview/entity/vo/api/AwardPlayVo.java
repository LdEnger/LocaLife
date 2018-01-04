package com.hiveview.entity.vo.api;

import java.util.Date;

public class AwardPlayVo {
	private Integer awardProperty; // int
	private Integer activityId; // int
	private String awardCode;
	private String title; // varchar
	private Integer detailId; // int
	private String awardName; // varchar
	private Date playTime; // long
	private String awardPicUrl;
	private Date availableBeginTime; // long
	private Date availableEndTime; // long
	private Integer orderStatus; // int
	private Integer videoId;
	private Integer awardDiscount;
	private String awardVideoName;
	private Float awardPrice;
	private String discountType;
	private String awardRemark;

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
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

	public String getAwardPicUrl() {
		return awardPicUrl;
	}

	public void setAwardPicUrl(String awardPicUrl) {
		this.awardPicUrl = awardPicUrl;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

}
