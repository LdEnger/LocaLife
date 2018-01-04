package com.hiveview.entity.po.award;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardDetail extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -137149340772718886L;
	private Integer id;
	private Integer activityId;
	private String awardType;
	private String awardName;
	private Integer awardAmount;
	/**
	 * 实物：1 ，卡券：2，VIP时长：3，电影票：4，麦粒：5，折扣券：6，代金券：7
	 */
	private Integer awardProperty;
	/**
	 * 是：1，否：2
	 */
	private Integer awardQrcodeFlag;
	private String userInfoType; // '1-name,2-addr,3-phone,4-postcode'
	private Date availableBeginTime;
	private Date availableEndTime;
	private String awardPicUrl;
	private String awardIconUrl;

	private Integer awardCurrentAmount;
	private Integer awardDiscount;
	private Integer awardPrice;
	private Integer awardVideoId;
	private String awardVideoName;
	private Integer awardCodeType;

	private Integer awardMlAmount;

	private String awardVideoPartnerId;
	private Integer awardVipDuration;

	private String awardDesc;

	private String awardVideoType;
	// 奖品每日抽取上限
	private int winLimitDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(Integer awardAmount) {
		this.awardAmount = awardAmount;
	}

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

	public Integer getAwardQrcodeFlag() {
		return awardQrcodeFlag;
	}

	public void setAwardQrcodeFlag(Integer awardQrcodeFlag) {
		this.awardQrcodeFlag = awardQrcodeFlag;
	}

	public String getUserInfoType() {
		return userInfoType;
	}

	public void setUserInfoType(String userInfoType) {
		this.userInfoType = userInfoType;
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

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getAwardPicUrl() {
		return awardPicUrl;
	}

	public void setAwardPicUrl(String awardPicUrl) {
		this.awardPicUrl = awardPicUrl;
	}

	public String getAwardIconUrl() {
		return awardIconUrl;
	}

	public void setAwardIconUrl(String awardIconUrl) {
		this.awardIconUrl = awardIconUrl;
	}

	public Integer getAwardCurrentAmount() {
		return awardCurrentAmount;
	}

	public void setAwardCurrentAmount(Integer awardCurrentAmount) {
		this.awardCurrentAmount = awardCurrentAmount;
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

	public String getAwardVideoName() {
		return awardVideoName;
	}

	public void setAwardVideoName(String awardVideoName) {
		this.awardVideoName = awardVideoName;
	}

	public Integer getAwardCodeType() {
		return awardCodeType;
	}

	public void setAwardCodeType(Integer awardCodeType) {
		this.awardCodeType = awardCodeType;
	}

	public Integer getAwardMlAmount() {
		return awardMlAmount;
	}

	public void setAwardMlAmount(Integer awardMlAmount) {
		this.awardMlAmount = awardMlAmount;
	}

	public String getAwardVideoPartnerId() {
		return awardVideoPartnerId;
	}

	public void setAwardVideoPartnerId(String awardVideoPartnerId) {
		this.awardVideoPartnerId = awardVideoPartnerId;
	}

	public Integer getAwardVipDuration() {
		return awardVipDuration;
	}

	public void setAwardVipDuration(Integer awardVipDuration) {
		this.awardVipDuration = awardVipDuration;
	}

	public String getAwardDesc() {
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}

	public String getAwardVideoType() {
		return awardVideoType;
	}

	public void setAwardVideoType(String awardVideoType) {
		this.awardVideoType = awardVideoType;
	}

	public int getWinLimitDay() {
		return winLimitDay;
	}

	public void setWinLimitDay(int winLimitDay) {
		this.winLimitDay = winLimitDay;
	}

}
