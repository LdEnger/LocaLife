package main.java.com.hiveview.entity.sys;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hiveview.entity.Entity;

public class SysAuth extends Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer authId;
	private String authName;
	private String authAction;
	private Integer authSeq;
	private Integer pid;
	private Timestamp createdTime;
	private Timestamp updatedTime;
	private Integer isEffective;

	public String toString(){
		return authId+":"+authName+":"+pid;
	} 
	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthAction() {
		return authAction;
	}

	public void setAuthAction(String authAction) {
		this.authAction = authAction;
	}

	public Integer getAuthSeq() {
		return authSeq;
	}

	public void setAuthSeq(Integer authSeq) {
		this.authSeq = authSeq;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}
}
