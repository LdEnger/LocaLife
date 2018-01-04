package com.hiveview.entity;

import java.io.Serializable;

public class Hall implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;//营业厅ID
	private String hallName;//营业厅名称
	private Integer branchId;//所属分公司ID
	private boolean containUser;//该营业厅有下属人员 true:有，false:没有
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHallName() {
		return hallName;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public boolean isContainUser() {
		return containUser;
	}
	public void setContainUser(boolean containUser) {
		this.containUser = containUser;
	}
	
}
