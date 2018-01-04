package main.java.com.hiveview.entity;

import java.io.Serializable;

public class Branch extends Entity implements Serializable{

	
		private static final long serialVersionUID = 1L;
		
		private Integer id;//分公司ID
		private String branchName;//分公司名称
		private String provinceCode;//省编码
		private String provinceName;//省名称
		private String cityCode;//市编码		
		private String cityName;//市名称
		private boolean containHall; //有下辖的营业厅，true:包含，false:不包含
		
		private String bossBranchOld;//老boss地市
		private String bossBranchNew;//新boss地市
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getBranchName() {
			return branchName;
		}
		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}
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
		public boolean isContainHall() {
			return containHall;
		}
		public void setContainHall(boolean containHall) {
			this.containHall = containHall;
		}
		/**
		 * @return the bossBranchOld
		 */
		public String getBossBranchOld() {
			return bossBranchOld;
		}
		/**
		 * @param bossBranchOld the bossBranchOld to set
		 */
		public void setBossBranchOld(String bossBranchOld) {
			this.bossBranchOld = bossBranchOld;
		}
		/**
		 * @return the bossBranchNew
		 */
		public String getBossBranchNew() {
			return bossBranchNew;
		}
		/**
		 * @param bossBranchNew the bossBranchNew to set
		 */
		public void setBossBranchNew(String bossBranchNew) {
			this.bossBranchNew = bossBranchNew;
		}
		
}
