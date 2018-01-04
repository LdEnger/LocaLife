package main.java.com.hiveview.entity.agio;

import java.io.Serializable;
import java.util.Date;

import com.hiveview.entity.Entity;

/**
 * Title：批量充值任务
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2017-01-03 15:36
 */
public class AgioPackageBatchTask extends Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer state;//0:未处理;1:成功；2失败3接口失败
	private Integer bacthId;//主任务ID
	private String userNum;
	private String orderNum;
	private String mac;
	private String sn;
	private String msg;
	private Date otime;
	private String phone;
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
	 * @return the userNum
	 */
	public String getUserNum() {
		return userNum;
	}
	/**
	 * @param userNum the userNum to set
	 */
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	/**
	 * @return the orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the otime
	 */
	public Date getOtime() {
		return otime;
	}
	/**
	 * @param otime the otime to set
	 */
	public void setOtime(Date otime) {
		this.otime = otime;
	}
	/**
	 * @return the bacthId
	 */
	public Integer getBacthId() {
		return bacthId;
	}
	/**
	 * @param bacthId the bacthId to set
	 */
	public void setBacthId(Integer bacthId) {
		this.bacthId = bacthId;
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
