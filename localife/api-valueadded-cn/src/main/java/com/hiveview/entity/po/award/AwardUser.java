package com.hiveview.entity.po.award;

import java.io.Serializable;

import com.hiveview.entity.Entity;

public class AwardUser extends Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -901368419952755830L;
	private Integer id;
	private String userId;
	private String userPhone;
	private String userAddr;
	private String realName;
	private String postcode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
