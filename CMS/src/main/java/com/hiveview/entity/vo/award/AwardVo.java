package main.java.com.hiveview.entity.vo.award;

import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardVo extends Entity{
	private int id; 
	private int awardType;
	private String awardName;
	private int awardAmout;
	private int awardProperty;
	private int awardQrcodeFlag;
	private String userInfoType;
	private Date availableBeginTime;
	private Date availableEndTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAwardType() {
		return awardType;
	}
	public void setAwardType(int awardType) {
		this.awardType = awardType;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public int getAwardAmout() {
		return awardAmout;
	}
	public void setAwardAmout(int awardAmout) {
		this.awardAmout = awardAmout;
	}
	public int getAwardProperty() {
		return awardProperty;
	}
	public void setAwardProperty(int awardProperty) {
		this.awardProperty = awardProperty;
	}
	public int getAwardQrcodeFlag() {
		return awardQrcodeFlag;
	}
	public void setAwardQrcodeFlag(int awardQrcodeFlag) {
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
	
	
}
