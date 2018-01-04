package main.java.com.hiveview.entity.vo;



public class VipUserVo{
	// 用户ID
	private String userId;
	// mac
	private String mac;
	// sn
	private String sn;
	// 设备码
	private String deviceCode;
	// 是否是vip用户
	private Integer isVip;
	// 是否领取了免费包
	private Integer hasFreePackage;
	// 到期时间
	private String expiredDate;
	// 创建时间
	private String createTime;
	// 修改时间
	private String updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Integer getHasFreePackage() {
		return hasFreePackage;
	}

	public void setHasFreePackage(Integer hasFreePackage) {
		this.hasFreePackage = hasFreePackage;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
