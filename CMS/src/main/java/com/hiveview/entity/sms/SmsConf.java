package main.java.com.hiveview.entity.sms;

import java.io.Serializable;

import com.hiveview.entity.Entity;

/**
 * Title：短信发送配置
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-6下午8:48:36
 */
public class SmsConf extends Entity implements Serializable{
	private static final long serialVersionUID = 4509526811788865165L;
	private Integer id;
	private Integer branchId;
	private Integer state;
	private String text1;
	private String args1;
	private String text2;
	private String args2;
	private String text3;
	private Integer updateBy;
	
	public String toString(){
		return this.text1+args1+text2+args2+text3;
	}
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
	 * @return the text1
	 */
	public String getText1() {
		return text1;
	}
	/**
	 * @param text1 the text1 to set
	 */
	public void setText1(String text1) {
		this.text1 = text1;
	}
	/**
	 * @return the args1
	 */
	public String getArgs1() {
		return args1;
	}
	/**
	 * @param args1 the args1 to set
	 */
	public void setArgs1(String args1) {
		this.args1 = args1;
	}
	/**
	 * @return the text2
	 */
	public String getText2() {
		return text2;
	}
	/**
	 * @param text2 the text2 to set
	 */
	public void setText2(String text2) {
		this.text2 = text2;
	}
	/**
	 * @return the args2
	 */
	public String getArgs2() {
		return args2;
	}
	/**
	 * @param args2 the args2 to set
	 */
	public void setArgs2(String args2) {
		this.args2 = args2;
	}
	/**
	 * @return the text3
	 */
	public String getText3() {
		return text3;
	}
	/**
	 * @param text3 the text3 to set
	 */
	public void setText3(String text3) {
		this.text3 = text3;
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
}
