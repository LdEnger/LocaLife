package main.java.com.hiveview.entity.discount;

import com.hiveview.entity.Entity;

/**
 * Title：Recharge.java
 * Description：充值信息实体类
 * Company：hiveview.com
 * Author：韩贺鹏
 * Email：hanhepeng@btte.net 
 * 2016年3月31日 下午3:33:29
 */
public class Recharge extends Entity{
	
	private Integer id;
	private Integer rechargeAmount;//充值数量
	private String rechargeName; //名称
	private String thumbnailImg; //缩略图
	private String bigImg;//大图
	private String description;//描述
	private Integer status;//状态
	private String updateTime;//更新时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Integer rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getRechargeName() {
		return rechargeName;
	}
	public void setRechargeName(String rechargeName) {
		this.rechargeName = rechargeName;
	}
	public String getThumbnailImg() {
		return thumbnailImg;
	}
	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}
	public String getBigImg() {
		return bigImg;
	}
	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
