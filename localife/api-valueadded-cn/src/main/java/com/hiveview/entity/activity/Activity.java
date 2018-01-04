package com.hiveview.entity.activity;

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
public class Activity extends Entity implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public Integer id;//主键ID
		public String activityName;//活动名称
		public Integer duration;//活动时长：该活动时长为在活动时长的时间内，用户享有优惠信息（或者免费观看影片）。并非活动结束时间 -  活动开始时间得出的时间
		public Integer productId;//vip产品包id
		public String productName;//vip产品包名称
		public Integer price;//价格
		public Integer effectiveTime;//有效期：活动的有效期时间，最高管理员根据实际的需求设定活动的有效时间，卡激活时间+活动有效期内可以使这张卡
		public Integer status;//状态 0：下线，1：上线
		public Integer operatorId;//操作者用户ID
		public String operatorName;//创建者名称
		public Integer operatorRoleId;//操作者角色ID
		public String operatorRole;//创建者角色
		public String remark;//备注
		public Timestamp insertTime;//更新时间
		public Timestamp updateTime;//更新时间
		public String chargingName;//计费包名称
		public Integer chargingId;//计费包ID
		public String chargingPic;//计费包图片
		public Integer isEffective;//是否过期
		
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
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getActivityName() {
			return activityName;
		}
		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}
		public Integer getDuration() {
			return duration;
		}
		public void setDuration(Integer duration) {
			this.duration = duration;
		}
		public Integer getPrice() {
			return price;
		}
		public void setPrice(Integer price) {
			this.price = price;
		}
		public Integer getEffectiveTime() {
			return effectiveTime;
		}
		public void setEffectiveTime(Integer effectiveTime) {
			this.effectiveTime = effectiveTime;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getOperatorId() {
			return operatorId;
		}
		public void setOperatorId(Integer operatorId) {
			this.operatorId = operatorId;
		}
		public String getOperatorName() {
			return operatorName;
		}
		public void setOperatorName(String operatorName) {
			this.operatorName = operatorName;
		}
		public Integer getOperatorRoleId() {
			return operatorRoleId;
		}
		public void setOperatorRoleId(Integer operatorRoleId) {
			this.operatorRoleId = operatorRoleId;
		}
		public String getOperatorRole() {
			return operatorRole;
		}
		public void setOperatorRole(String operatorRole) {
			this.operatorRole = operatorRole;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Timestamp getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Timestamp updateTime) {
			this.updateTime = updateTime;
		}
		public Timestamp getInsertTime() {
			return insertTime;
		}
		public void setInsertTime(Timestamp insertTime) {
			this.insertTime = insertTime;
		}
		public String getChargingName() {
			return chargingName;
		}
		public void setChargingName(String chargingName) {
			this.chargingName = chargingName;
		}
		public Integer getChargingId() {
			return chargingId;
		}
		public void setChargingId(Integer chargingId) {
			this.chargingId = chargingId;
		}
		public String getChargingPic() {
			return chargingPic;
		}
		public void setChargingPic(String chargingPic) {
			this.chargingPic = chargingPic;
		}
		public Integer getIsEffective() {
			return isEffective;
		}
		public void setIsEffective(Integer isEffective) {
			this.isEffective = isEffective;
		}
		
}
