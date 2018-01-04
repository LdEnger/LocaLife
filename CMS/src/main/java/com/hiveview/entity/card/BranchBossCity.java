package main.java.com.hiveview.entity.card;

import java.io.Serializable;

import com.hiveview.entity.Entity;


public class BranchBossCity extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; //
	private int bossType;//1newboss 2oldboss
	private int branchId;//
	private String cityName;//boss地市
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the bossType
	 */
	public int getBossType() {
		return bossType;
	}
	/**
	 * @param bossType the bossType to set
	 */
	public void setBossType(int bossType) {
		this.bossType = bossType;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
}
