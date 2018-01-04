package main.java.com.hiveview.entity.vo;

import java.io.Serializable;

public class CityVo extends ProvinceVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String cityCode;
	private String cityName;
	private Integer cityType;
	
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
	public Integer getCityType() {
		return cityType;
	}
	public void setCityType(Integer cityType) {
		this.cityType = cityType;
	}
	
}
