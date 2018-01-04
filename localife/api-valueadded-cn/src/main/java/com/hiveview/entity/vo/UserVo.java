package com.hiveview.entity.vo;


public class UserVo{
	
	private Integer id;//用户id
	private String userName;//用户名称
	private String userAccount;//用户账户名称--用户中心账户名称等于用户名称
	private String mac;//用户mac
	private String sn;//用户sn
	private String devicecode;//用户设备码
	private Integer walletStatus;//钱包状态
	
	public UserVo(){
	}
	
	public UserVo(int id,String userName,String userAccount,String mac,String sn,String devicecode,int walletStatus){
		this.id = id;
		this.userName = userName;
		this.userAccount = userAccount;
		this.mac = mac;
		this.sn = sn; 
		this.devicecode = devicecode;
		this.walletStatus = walletStatus;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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
	public String getDevicecode() {
		return devicecode;
	}
	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}
	public Integer getWalletStatus() {
		return walletStatus;
	}
	public void setWalletStatus(Integer walletStatus) {
		this.walletStatus = walletStatus;
	}
	
}
