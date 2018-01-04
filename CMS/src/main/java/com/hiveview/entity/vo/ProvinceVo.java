package main.java.com.hiveview.entity.vo;

import java.io.Serializable;


public class ProvinceVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String provinceCode;//省编码
	private String provinceName;//省名称
	private Integer provinceType;//类型
	//private List<CityVo> cityVolist;//城市列表
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getProvinceType() {
		return provinceType;
	}
	public void setProvinceType(Integer provinceType) {
		this.provinceType = provinceType;
	}
	/*public List<CityVo> getCityVolist() {
		return cityVolist;
	}
	public void setCityVolist(List<CityVo> cityVolist) {
		this.cityVolist = cityVolist;
	}*/
}
