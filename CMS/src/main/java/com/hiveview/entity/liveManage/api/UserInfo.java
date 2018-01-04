package main.java.com.hiveview.entity.liveManage.api;

import java.io.Serializable;

/**
 * @author by wengjingchang on 2016/9/19.
 */
public class UserInfo implements Serializable {
	private Integer openUid;
	private String openName;
	private Integer openRole;
	private Integer zoneId;
	private String zoneName;
	private Integer branchId;
	private String branchName;
	private Integer hallId;
	private String hallName;
	private String proCode;
	private String proName;
	private String cityCode;
	private String cityName;

	public Integer getOpenUid() {
		return openUid;
	}

	public void setOpenUid(Integer openUid) {
		this.openUid = openUid;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public Integer getOpenRole() {
		return openRole;
	}

	public void setOpenRole(Integer openRole) {
		this.openRole = openRole;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getHallId() {
		return hallId;
	}

	public void setHallId(Integer hallId) {
		this.hallId = hallId;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}