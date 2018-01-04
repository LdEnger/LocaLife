package com.hiveview.entity.po.award;

import java.io.Serializable;

import com.hiveview.entity.Entity;

public class AwardCode extends Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4788518694321777618L;
	private Integer id;
	private Integer detailId;
	private String awardCode;
	/**
	 * 已领取-1,未分配-2;
	 */
	private Integer acceptFlag;
	private Integer awardCodeType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAcceptFlag() {
		return acceptFlag;
	}

	public void setAcceptFlag(Integer acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	public Integer getAwardCodeType() {
		return awardCodeType;
	}

	public void setAwardCodeType(Integer awardCodeType) {
		this.awardCodeType = awardCodeType;
	}
}
