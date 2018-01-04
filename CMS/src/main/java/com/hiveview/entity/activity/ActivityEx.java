package main.java.com.hiveview.entity.activity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hiveview.entity.Entity;

/**
 * Title：Activity.java
 * Description：活动主体类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2015年11月27日 上午10:15:39
 */
public class ActivityEx extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	public int productId;// 活动id
	public String productName;// 活动名称
	public int chargingId;// vip计费包id
	public String chargingName;// vip计费包名称
	public int chargingDuration;// vip计费时长  单位秒， 
	public int chargingPrice;// vip计费价格
	public String chargingPic;// 计费包图片
	public String chargingStr;// 计费信息字符串 productId||productName||chargingId||chargingName||chargingPrice||chargingDuration||chargingPic
	public int productCycle;//计费周期 单位月 
	public int productFreeDay;//免费时长  单位天
	public int productDay;//实际时长  =计费周期×30  单位天
	public int flag ;//0.下线，1上线
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
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
	/**
	 * @return the chargingId
	 */
	public int getChargingId() {
		return chargingId;
	}
	/**
	 * @param chargingId the chargingId to set
	 */
	public void setChargingId(int chargingId) {
		this.chargingId = chargingId;
	}
	/**
	 * @return the chargingName
	 */
	public String getChargingName() {
		return chargingName;
	}
	/**
	 * @param chargingName the chargingName to set
	 */
	public void setChargingName(String chargingName) {
		this.chargingName = chargingName;
	}
	/**
	 * @return the chargingDuration
	 */
	public int getChargingDuration() {
		return chargingDuration;
	}
	/**
	 * @param chargingDuration the chargingDuration to set
	 */
	public void setChargingDuration(int chargingDuration) {
		this.chargingDuration = chargingDuration;
	}
	/**
	 * @return the chargingPrice
	 */
	public int getChargingPrice() {
		return chargingPrice;
	}
	/**
	 * @param chargingPrice the chargingPrice to set
	 */
	public void setChargingPrice(int chargingPrice) {
		this.chargingPrice = chargingPrice;
	}
	/**
	 * @return the chargingPic
	 */
	public String getChargingPic() {
		return chargingPic;
	}
	/**
	 * @param chargingPic the chargingPic to set
	 */
	public void setChargingPic(String chargingPic) {
		this.chargingPic = chargingPic;
	}
	/**
	 * @return the chargingStr
	 */
	public String getChargingStr() {
		return chargingStr;
	}
	/**
	 * @param chargingStr the chargingStr to set
	 */
	public void setChargingStr(String chargingStr) {
		this.chargingStr = chargingStr;
	}
	/**
	 * @return the productCycle
	 */
	public int getProductCycle() {
		return productCycle;
	}
	/**
	 * @param productCycle the productCycle to set
	 */
	public void setProductCycle(int productCycle) {
		this.productCycle = productCycle;
	}
	/**
	 * @return the productFreeDay
	 */
	public int getProductFreeDay() {
		return productFreeDay;
	}
	/**
	 * @param productFreeDay the productFreeDay to set
	 */
	public void setProductFreeDay(int productFreeDay) {
		this.productFreeDay = productFreeDay;
	}
	/**
	 * @return the productDay
	 */
	public int getProductDay() {
		return productDay;
	}
	/**
	 * @param productDay the productDay to set
	 */
	public void setProductDay(int productDay) {
		this.productDay = productDay;
	}
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
