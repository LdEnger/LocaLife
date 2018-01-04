package main.java.com.hiveview.entity.po.award;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardPlay extends Entity implements Serializable {
	private static final long serialVersionUID = -238039215387724276L;
	private Integer id;
	private Integer activityId;
	private Integer detailId;
	private String userId;
	private String userName;
	private String mac;
	private String sn;
	private Date playTime;
	private String awardName;
	private String awardType;
	private String awardCode;
	private Integer acceptFlag;
	
	private String userPhone;
	private String realName;
	

	public Integer getId() {
		return id;
	}
	
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
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

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
}
