package main.java.com.hiveview.entity.agio;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

/**
 * Title：分公司充值麦币记录
 * Description：分公司充值麦币记录
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-1下午4:30:11
 */
public class AgioPackagePool extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderId;
	private Integer amount;
	private Integer zondId;
	private Integer branchId;
	private Integer packageConfId;
	private Integer createBy;
	private Date createTime;
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
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * @return the zondId
	 */
	public Integer getZondId() {
		return zondId;
	}
	/**
	 * @param zondId the zondId to set
	 */
	public void setZondId(Integer zondId) {
		this.zondId = zondId;
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
}
