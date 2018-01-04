package main.java.com.hiveview.entity.agio;

import java.io.Serializable;

import com.hiveview.entity.Entity;

/**
 * Title：分公司帐目信息余额展现
 * Description：分公司帐目信息余额展现
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-1下午4:30:11
 */
public class AgioPackageView extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String totalAmount;
	private Integer useAmount;
	private Integer leftAmount;
	private Integer branchId;
	private Integer packageConfId;
	private String queryBranchIds;
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
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the useAmount
	 */
	public Integer getUseAmount() {
		return useAmount;
	}
	/**
	 * @param useAmount the useAmount to set
	 */
	public void setUseAmount(Integer useAmount) {
		this.useAmount = useAmount;
	}
	/**
	 * @return the leftAmount
	 */
	public Integer getLeftAmount() {
		return leftAmount;
	}
	/**
	 * @param leftAmount the leftAmount to set
	 */
	public void setLeftAmount(Integer leftAmount) {
		this.leftAmount = leftAmount;
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
	 * @return the packageConfId
	 */
	public Integer getPackageConfId() {
		return packageConfId;
	}
	/**
	 * @param packageConfId the packageConfId to set
	 */
	public void setPackageConfId(Integer packageConfId) {
		this.packageConfId = packageConfId;
	}
	/**
	 * @return the queryBranchIds
	 */
	public String getQueryBranchIds() {
		return queryBranchIds;
	}
	/**
	 * @param queryBranchIds the queryBranchIds to set
	 */
	public void setQueryBranchIds(String queryBranchIds) {
		this.queryBranchIds = queryBranchIds;
	}
}
