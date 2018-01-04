package com.hiveview.entity.sys;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.entity.Entity;

public class SysUser extends Entity {

	//	`sys_user_id` bigint(64) NOT NULL AUTO_INCREMENT,
	private Integer userId;

	//	`user_name` varchar(45) DEFAULT NULL COMMENT '用户名',
	private String userName;

	//	`user_pwd` varchar(65) DEFAULT NULL COMMENT '密码',
	private String userPwd;

	//	`user_mail` varchar(45) DEFAULT NULL COMMENT '账号（邮箱）',
	private String userMail;

	//	`role_id` bigint(64) DEFAULT NULL COMMENT '角色id',
	private Integer roleId;

	//	`created_time` datetime DEFAULT NULL COMMENT '创建时间',
	private Date createdTime;

	//	`created_by` bigint(64) DEFAULT '0' COMMENT '创建人id',
	private Integer createdBy;

	//	`updated_time` datetime DEFAULT NULL COMMENT '更新时间',
	private Date updatedTime;

	//	`updated_by` bigint(64) DEFAULT '0' COMMENT '更新人id',
	private Integer updatedBy;

	//	`is_effective` bigint(1) DEFAULT '1' COMMENT '0:无效    1：有效',
	private Integer isEffective;
	
	//	`province_code` varchar(4) DEFAULT NULL COMMENT '省编码',
	private String provinceCode;
	
	//	`province_name` varchar(64) DEFAULT NULL COMMENT '省名称',
	private String provinceName;
	
	//	`city_code` varchar(4) DEFAULT NULL COMMENT '市编码',
	private String cityCode;
	
	//	`city_name` varchar(64) DEFAULT NULL COMMENT '市名称',
	private String cityName;
	
	private Integer branchId;  //分公司ID
	
	private String branchName;   //分公司名称
	
	private Integer hallId;  //营业厅ID
	
	private String hallName;   //营业厅名称
	
	private String phoneNumber;//电话
	
	private String roleName;//角色名称
	
	private Integer zoneId;  //战区ID
	
	private String zoneName;   //战区名称

	private String zoneTreeIds;   //战区名称


	public String toString(){
		return JSONObject.toJSON(this).toString();
	}

    public String getZoneTreeIds() {
        return zoneTreeIds;
    }

    public void setZoneTreeIds(String zoneTreeIds) {
        this.zoneTreeIds = zoneTreeIds;
    }

    public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPwd() {
		return userPwd;
	}


	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}


	public String getUserMail() {
		return userMail;
	}


	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Integer getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


	public Date getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}


	public Integer getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Integer getIsEffective() {
		return isEffective;
	}


	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
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


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	
}
