package main.java.com.hiveview.entity.po.award;

import com.hiveview.entity.Entity;

public class AwardCode extends Entity{
	
	private Integer id;
	private Integer detailId;
	private Integer awardCodeType;
	private String awardCode;
	private Integer acceptFlag;
	
	private String awardType;
	private String awardName;
	private String availableEndTime;
	private Integer activityId;
	private String mac;
	private String playTime;
	private String realName;
	private String userPhone;
	private String userAddress;
	private String awardDesc;
	private String postcode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getAwardCodeType() {
		return awardCodeType;
	}
	public void setAwardCodeType(Integer awardCodeType) {
		this.awardCodeType = awardCodeType;
	}
	public String getAwardCode() {
		return awardCode;
	}
	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}
	public Integer getAcceptFlag() {
		return acceptFlag;
	}
	public void setAcceptFlag(Integer acceptFlag) {
		this.acceptFlag = acceptFlag;
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
	public String getAvailableEndTime() {
		return availableEndTime;
	}
	public void setAvailableEndTime(String availableEndTime) {
		this.availableEndTime = availableEndTime;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getAwardDesc() {
		return awardDesc;
	}
	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
}
