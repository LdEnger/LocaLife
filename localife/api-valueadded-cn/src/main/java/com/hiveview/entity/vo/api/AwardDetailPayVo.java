package com.hiveview.entity.vo.api;

import java.util.Date;

public class AwardDetailPayVo {
	private Integer acceptFlag;// tinyint
	private Integer awardProperty;
	private Integer awardMlAmount;// int
	private Integer awardVipDuration;// int
	private Integer awardDiscount;// int
	private Integer awardPrice;// Int
	private Integer awardVideoId;// Int
	private Date availableBeginTime;// Long
	private Date availableEndTime;// Long

	public Integer getAcceptFlag() {
		return acceptFlag;
	}

	public void setAcceptFlag(Integer acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	public Integer getAwardMlAmount() {
		return awardMlAmount;
	}

	public void setAwardMlAmount(Integer awardMlAmount) {
		this.awardMlAmount = awardMlAmount;
	}

	public Integer getAwardVipDuration() {
		return awardVipDuration;
	}

	public void setAwardVipDuration(Integer awardVipDuration) {
		this.awardVipDuration = awardVipDuration;
	}

	public Integer getAwardDiscount() {
		return awardDiscount;
	}

	public void setAwardDiscount(Integer awardDiscount) {
		this.awardDiscount = awardDiscount;
	}

	public Integer getAwardPrice() {
		return awardPrice;
	}

	public void setAwardPrice(Integer awardPrice) {
		this.awardPrice = awardPrice;
	}

	public Integer getAwardVideoId() {
		return awardVideoId;
	}

	public void setAwardVideoId(Integer awardVideoId) {
		this.awardVideoId = awardVideoId;
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

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

}
