package main.java.com.hiveview.entity.vo;

import java.io.Serializable;

public class BatchOpenLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public int userId;
	public String mac;
	public String sn;
	public String orderStartTime;
	public String cpChannelPnumber;
	public String cpChannelPname;
	public String cpChannelCnumber;
	public String cpChannelCname;
	public String type;
	public Integer sysUserId;
	public String sysUserName;
	public Integer branchId;// 分公司ID
	public String branchName;// 分公司名称
	public Integer hallId; // 营业厅ID
	public String hallName;// 营业厅名称
	public int productId;//产品包ID
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getCpChannelPnumber() {
		return cpChannelPnumber;
	}

	public void setCpChannelPnumber(String cpChannelPnumber) {
		this.cpChannelPnumber = cpChannelPnumber;
	}

	public String getCpChannelPname() {
		return cpChannelPname;
	}

	public void setCpChannelPname(String cpChannelPname) {
		this.cpChannelPname = cpChannelPname;
	}

	public String getCpChannelCnumber() {
		return cpChannelCnumber;
	}

	public void setCpChannelCnumber(String cpChannelCnumber) {
		this.cpChannelCnumber = cpChannelCnumber;
	}

	public String getCpChannelCname() {
		return cpChannelCname;
	}

	public void setCpChannelCname(String cpChannelCname) {
		this.cpChannelCname = cpChannelCname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}
