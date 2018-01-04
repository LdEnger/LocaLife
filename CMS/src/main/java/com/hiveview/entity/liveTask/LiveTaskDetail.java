package main.java.com.hiveview.entity.liveTask;

import com.hiveview.entity.Entity;

public class LiveTaskDetail extends Entity {

	private Integer id;
	private String taskId;
	private String mac;
	private String sn;
	private String orderId;
	private Integer openResultType;
	private String openResultName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOpenResultType() {
		return openResultType;
	}

	public void setOpenResultType(Integer openResultType) {
		this.openResultType = openResultType;
	}

	public String getOpenResultName() {
		return openResultName;
	}

	public void setOpenResultName(String openResultName) {
		this.openResultName = openResultName;
	}

}
