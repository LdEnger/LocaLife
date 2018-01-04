package com.hiveview.entity.sys;

import java.io.Serializable;
import java.util.List;

import com.hiveview.entity.Entity;

/**
 * Title：ZoneCity.java
 * Description：战区和城市的关系
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年5月17日 下午3:25:18
 */
public class ZoneCity extends Entity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer zoneId; //战区ID
	private String zoneName; //战区名称
	private Integer cityId; //城市ID
	private String cityName; //城市名称
	private Integer provinceId; //省份ID
	private String provinceName; //省份名称
	private String updateTime; //更新时间
	private boolean containBranch;//该城市是否包含分公司，true:是，false:否


	private Integer code;
	private String name;
	private Integer type;
	private String message;
	private String stringTime;
	private Integer longTime;
	private Boolean cached;
	private String result;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStringTime() {
		return stringTime;
	}

	public void setStringTime(String stringTime) {
		this.stringTime = stringTime;
	}

	public Integer getLongTime() {
		return longTime;
	}

	public void setLongTime(Integer longTime) {
		this.longTime = longTime;
	}

	public Boolean getCached() {
		return cached;
	}

	public void setCached(Boolean cached) {
		this.cached = cached;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public boolean isContainBranch() {
		return containBranch;
	}
	public void setContainBranch(boolean containBranch) {
		this.containBranch = containBranch;
	}
}
