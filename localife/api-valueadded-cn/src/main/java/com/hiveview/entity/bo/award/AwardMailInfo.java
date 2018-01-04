package com.hiveview.entity.bo.award;

/**
 * 客户邮寄信息
 * 
 * @author wengjingchang
 *
 */
public class AwardMailInfo {
	private String userId;
	private String awardCode;
	private Integer detailId;
	private String realName;
	private String userPhone;
	private String postcode;
	private String userAddr;
	private Integer awardOrderId;
	private Integer awardPlayId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public Integer getAwardOrderId() {
		return awardOrderId;
	}

	public void setAwardOrderId(Integer awardOrderId) {
		this.awardOrderId = awardOrderId;
	}

	public Integer getAwardPlayId() {
		return awardPlayId;
	}

	public void setAwardPlayId(Integer awardPlayId) {
		this.awardPlayId = awardPlayId;
	}
}
