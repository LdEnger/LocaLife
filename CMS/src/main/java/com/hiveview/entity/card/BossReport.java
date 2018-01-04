package main.java.com.hiveview.entity.card;

import java.util.HashMap;
import java.util.Map;

/**
 * Title：
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-4-12上午10:24:20
 */
public class BossReport {
	private Integer branchId;
	private String branchName;
	private Integer productId;
	private Integer num;
	private String productName;
	private Map<Integer, BossReport> map =new HashMap<Integer, BossReport>();
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
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * @return the map
	 */
	public Map<Integer, BossReport> getMap() {
		return map;
	}
	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, BossReport> map) {
		this.map = map;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
