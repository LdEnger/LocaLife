package main.java.com.hiveview.entity.count;

/**
 * Title：CountRecord.java
 * Description：开通记录
 * Company：hiveview.com
 * Author：hanhepeng
 * Email：hanhepeng@btte.net 
 * 2016年7月6日 下午4:51:23
 */
public class CountRecord {
	private Integer activityId;//活动ID
	private String activityName;//活动名称
	private Integer duration;//活动时长
	private Integer branchId;//分公司ID
	private String branchName;//分公司名称
	private Integer total;//开通个数
	private Integer productId;//产品包ID
	private String productName;//产品包名称
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
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
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
