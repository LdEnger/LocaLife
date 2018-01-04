package main.java.com.hiveview.entity.agio;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

/**
 * Title：麦币包
 * Description：麦币包
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-1下午4:30:11
 */
public class AgioPackage extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer branchId;
	private Integer zoneId;
	private Integer packAmount;
	private Integer agioPackageId;
	private Integer state;//状态0 下线 1上线 3删除
	private Integer createBy;
	private Integer updateBy;
	private String packageName;
	private Date createTime;
	private Date updateTime;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the packAmount
	 */
	public Integer getPackAmount() {
		return packAmount;
	}
	/**
	 * @param packAmount the packAmount to set
	 */
	public void setPackAmount(Integer packAmount) {
		this.packAmount = packAmount;
	}
	/**
	 * @return the agioPackageId
	 */
	public Integer getAgioPackageId() {
		return agioPackageId;
	}
	/**
	 * @param agioPackageId the agioPackageId to set
	 */
	public void setAgioPackageId(Integer agioPackageId) {
		this.agioPackageId = agioPackageId;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the createBy
	 */
	public Integer getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the updateBy
	 */
	public Integer getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the zoneId
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
}
