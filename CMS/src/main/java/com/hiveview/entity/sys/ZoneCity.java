package main.java.com.hiveview.entity.sys;

import java.io.Serializable;

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
