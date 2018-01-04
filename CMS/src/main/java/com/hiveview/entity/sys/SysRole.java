package main.java.com.hiveview.entity.sys;

import java.util.Date;

import com.hiveview.entity.Entity;

public class SysRole extends Entity {

	private Integer roleId;
	private String roleName;
	private Date updatedTime;
	private Date createdTime;
	private Integer createdBy;
	private Integer updatedBy;
	private Integer isEffective;
	private Integer isBatch;//是否允许批量操作，0：禁止，1：允许

	public String getRoleName() {
		return roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public Integer getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Integer isBatch) {
		this.isBatch = isBatch;
	}
}
