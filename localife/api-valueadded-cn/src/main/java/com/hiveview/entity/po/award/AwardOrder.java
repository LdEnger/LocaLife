package com.hiveview.entity.po.award;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

public class AwardOrder extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6757982866043470112L;
	private Integer id;
	private String orderId;
	private Integer orderStatus;
	private Date orderTime;
	private String awardCode;
	private Integer awardPlayId;
	private Integer awardDetailId;
	private String awardUserId;
	private Integer awardProperty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

	public Integer getAwardPlayId() {
		return awardPlayId;
	}

	public void setAwardPlayId(Integer awardPlayId) {
		this.awardPlayId = awardPlayId;
	}

	public Integer getAwardDetailId() {
		return awardDetailId;
	}

	public void setAwardDetailId(Integer awardDetailId) {
		this.awardDetailId = awardDetailId;
	}

	public String getAwardUserId() {
		return awardUserId;
	}

	public void setAwardUserId(String awardUserId) {
		this.awardUserId = awardUserId;
	}

	public Integer getAwardProperty() {
		return awardProperty;
	}

	public void setAwardProperty(Integer awardProperty) {
		this.awardProperty = awardProperty;
	}

}
