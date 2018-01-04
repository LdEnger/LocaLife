package main.java.com.hiveview.entity.liveTask;

import com.hiveview.entity.Entity;

public class LiveTask extends Entity {

	private Integer id;
	private String taskId;
	private String taskName;
	private String taskDesc;
	private String branchId;
	private String branchName;
	private String hallId;
	private String hallName;
	private String productId;
	private String productName;
	private String executeResult;
	private String openId;
	private String openName;
	private String openProvince;
	private String openProvincename;
	private String openCity;
	private String openCityname;
	private String openTime;
	private String closeTime;
	private String submitTime;
	/**
	 * 1-开通任务 2-续费任务 3-退订任务
	 */
	private Integer taskType;
	private Integer openZoneId;
	private String openZoneName;

	public Integer getOpenZoneId() {
		return openZoneId;
	}

	public void setOpenZoneId(Integer openZoneId) {
		this.openZoneId = openZoneId;
	}

	public String getOpenZoneName() {
		return openZoneName;
	}

	public void setOpenZoneName(String openZoneName) {
		this.openZoneName = openZoneName;
	}

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getHallId() {
		return hallId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public String getOpenProvince() {
		return openProvince;
	}

	public void setOpenProvince(String openProvince) {
		this.openProvince = openProvince;
	}

	public String getOpenProvincename() {
		return openProvincename;
	}

	public void setOpenProvincename(String openProvincename) {
		this.openProvincename = openProvincename;
	}

	public String getOpenCity() {
		return openCity;
	}

	public void setOpenCity(String openCity) {
		this.openCity = openCity;
	}

	public String getOpenCityname() {
		return openCityname;
	}

	public void setOpenCityname(String openCityname) {
		this.openCityname = openCityname;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
}
