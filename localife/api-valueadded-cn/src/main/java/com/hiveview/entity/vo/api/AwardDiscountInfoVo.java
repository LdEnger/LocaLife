package com.hiveview.entity.vo.api;

public class AwardDiscountInfoVo {
	private String tradeNo;
	private Float awardPrice;
	private Float payPrice;
	private Integer awardDiscount;
	private String awardVideoName;
	private String chargingName;
	private Integer chargingDuration;
	private String partnerId;
	private String partnerKey;
	private String notifyUrl;
	private String orderAppend;
	private String awardVideoType;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Float getAwardPrice() {
		return awardPrice;
	}

	public void setAwardPrice(Float awardPrice) {
		this.awardPrice = awardPrice;
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

	public String getChargingName() {
		return chargingName;
	}

	public void setChargingName(String chargingName) {
		this.chargingName = chargingName;
	}

	public Integer getChargingDuration() {
		return chargingDuration;
	}

	public void setChargingDuration(Integer chargingDuration) {
		this.chargingDuration = chargingDuration;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOrderAppend() {
		return orderAppend;
	}

	public void setOrderAppend(String orderAppend) {
		this.orderAppend = orderAppend;
	}

	public Float getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Float payPrice) {
		this.payPrice = payPrice;
	}

	public String getAwardVideoType() {
		return awardVideoType;
	}

	public void setAwardVideoType(String awardVideoType) {
		this.awardVideoType = awardVideoType;
	}
}
