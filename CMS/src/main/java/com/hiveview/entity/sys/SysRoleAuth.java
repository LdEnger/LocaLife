package main.java.com.hiveview.entity.sys;

import com.hiveview.entity.Entity;

public class SysRoleAuth extends Entity {

	private Integer roleAuth;
	private Integer authId;
	private Integer roleId;

	public Integer getRoleAuth() {
		return roleAuth;
	}

	public void setRoleAuth(Integer roleAuth) {
		this.roleAuth = roleAuth;
	}

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
