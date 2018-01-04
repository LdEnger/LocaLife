package main.java.com.hiveview.entity.sms;

import java.io.Serializable;

import com.hiveview.entity.Entity;
import com.hiveview.entity.card.Card;

/**
 * Title：短信发送记录
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2016-12-6下午8:48:36
 */
public class SmsRecord extends Entity implements Serializable{
	private static final long serialVersionUID = 6447090526799344381L;
	private Integer id;
	private Integer branchId;
	private Integer activityId;
	private Integer state;
	private Integer smsTemplet;
	private String sender;
	private String phone;
	private String branchName;
	private String activityName;
	private String activityCode;
	
	
	public SmsRecord(Integer id, Integer branchId, Integer activityId, Integer state, Integer smsTemplet,
			String sender, String phone, String branchName, String activityName, String activityCode) {
		super();
		this.id = id;
		this.branchId = branchId;
		this.activityId = activityId;
		this.state = state;
		this.smsTemplet = smsTemplet;
		this.sender = sender;
		this.phone = phone;
		this.branchName = branchName;
		this.activityName = activityName;
		this.activityCode = activityCode;
	}
	
	public SmsRecord(){
		super();
	}
	
	public SmsRecord(Card card,int state,int smsTemplet){
		this.branchId =card.getCardFromBranch();
		this.activityId =card.getActivityId();
		this.state =state;
		this.smsTemplet =smsTemplet;
		this.sender =card.getCreatorName();
		this.phone =card.getPhone();
		this.branchName =card.getBranchName();
		this.activityName =card.getActivityName();
		this.activityCode =card.getActivationCode();
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
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	 * @return the smsTemplet
	 */
	public Integer getSmsTemplet() {
		return smsTemplet;
	}
	/**
	 * @param smsTemplet the smsTemplet to set
	 */
	public void setSmsTemplet(Integer smsTemplet) {
		this.smsTemplet = smsTemplet;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * @return the activityCode
	 */
	public String getActivityCode() {
		return activityCode;
	}
	/**
	 * @param activityCode the activityCode to set
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
