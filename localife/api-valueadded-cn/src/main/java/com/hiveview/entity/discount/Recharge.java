package com.hiveview.entity.discount;


/**
 * Title：Recharge.java
 * Description：充值信息实体类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月31日 下午3:33:29
 */
public class Recharge{
	
	private Integer rechargeAmount;//充值数量
	private Integer discountAmount;//优惠金额
	private String imgUrl;//图片地址
	
	public Integer getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Integer rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public Integer getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
