package com.hiveview.entity.discount;

import com.hiveview.entity.Entity;

/**
 * Title：Discount.java
 * Description：充送实体类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月30日 下午3:09:53
 */
public class Discount  extends Entity{
	
	private Integer id; //
	private String discountName; //优惠名称
	private Integer rechargeAmount; //充值金额
	private Integer discountAmount; //赠送金额
	private String imgUrl; //图片地址
	private Integer isEffective; //是否有效。0：无效，1：有效
	private Integer rechargeId; //此优惠关联的充值信息
	private String updateTime; //更新时间
	private String message; //自定义状态信息
	private String code; //状态码  “success”：成功，“fail”:失败
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
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
	public Integer getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}
	public Integer getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Integer rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
