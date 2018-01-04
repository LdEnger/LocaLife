package main.java.com.hiveview.entity.po.award;

import com.hiveview.entity.Entity;

public class AwardVideo extends Entity{
	private Integer awardVideoId; 
	private String awardVideoName;
	private Integer awardPrice;
	private Integer awardDiscount;
	private String partnerId;
	private Integer expiryTime;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public Integer getAwardVideoId() {
		return awardVideoId;
	}
	public void setAwardVideoId(Integer awardVideoId) {
		this.awardVideoId = awardVideoId;
	}
	public String getAwardVideoName() {
		return awardVideoName;
	}
	public void setAwardVideoName(String awardVideoName) {
		this.awardVideoName = awardVideoName;
	}
	public Integer getAwardPrice() {
		return awardPrice;
	}
	public void setAwardPrice(Integer awardPrice) {
		this.awardPrice = awardPrice;
	}
	public int getAwardDiscount() {
		return awardDiscount;
	}
	public void setAwardDiscount(Integer awardDiscount) {
		this.awardDiscount = awardDiscount;
	}
	public Integer getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Integer expiryTime) {
		this.expiryTime = expiryTime;
	}
	
}
