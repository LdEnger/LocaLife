package main.java.com.hiveview.entity.agio;

import java.io.Serializable;

import com.hiveview.entity.Entity;

/**
 * Title：折扣配置
 * Description：方便按折扣进行余额显示
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-1下午4:30:11
 */
public class AgioPackageConf extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer agioValue;
	private String agioName;
	private String state;// 是否提交状态。如果提交，则不许修改 。只有提交状态的折扣才能使用
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
	 * @return the agioValue
	 */
	public Integer getAgioValue() {
		return agioValue;
	}
	/**
	 * @param agioValue the agioValue to set
	 */
	public void setAgioValue(Integer agioValue) {
		this.agioValue = agioValue;
	}
	/**
	 * @return the agioName
	 */
	public String getAgioName() {
		return agioName;
	}
	/**
	 * @param agioName the agioName to set
	 */
	public void setAgioName(String agioName) {
		this.agioName = agioName;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
