package main.java.com.hiveview.entity.activity;

import java.io.Serializable;
import java.util.Map;

import com.hiveview.entity.Entity;
import com.hiveview.entity.card.Card;

public class ActivityOrder extends Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String activityOrderId;//活动订单id
	public Integer activityOrderType;//活动订单类型 0：免费vip 2：促销活动卡
	public String activityOrderTypeName;//活动订单类型名称
	public Integer activityOrderStatus;//订单状态 1：已下单 2：已激活 3：激活失败 4：已退订
	public String activityOrderStatusName;//订单状态名称
	public Integer recordStatus;//活动记录状态  1：可查询  2：不可查询
	public Integer chargingId;//计费id
	public String chargingName;//计费名称
	public Integer chargingPrice;//计费价格
	public Integer chargingDuration;//计费时长
	public String chargingImg;//图片路径
	public String submitTime;//下单时间
	public String activityTime;//更改时间
	public Integer uid;//用户id
	public String uname;//用户name
	public String mac;//mac
	public String sn;//sn
	public String devicecode;//设备码
	
	public ActivityOrder(){
		
	}
	
	/**
	 * @param activity
	 * @param card
	 * @param activityType
	 * @param activityStatus
	 * @param handle 1:下单动作，2:激活动作
	 * @param handleTime
	 */
//	public ActivityOrder(Activity activity,Card card,int activityType,int activityStatus,String handleTime){
//		this.activityOrderType = activityType;
//		switch (activityType) {
//			case 0:
//				this.activityOrderTypeName="免费vip";break;
//			case 1:
//				this.activityOrderTypeName="vip计费包";break;
//			case 2:
//				this.activityOrderTypeName="促销活动卡";break;
//		}
//		this.activityOrderStatus = activityStatus;
//		switch (activityStatus) {
//		case 1:
//			this.activityOrderStatusName="已下单";break;
//		case 2:
//			this.activityOrderStatusName="已激活";break;
//		case 3:
//			this.activityOrderStatusName="激活失败";break;
//		case 4:
//			this.activityOrderStatusName="已退订";break;
//		}
//		this.chargingId = activity.chargingId;
//		this.chargingPrice = activity.price;
//		this.chargingName = activity.chargingName;
//		this.chargingDuration = activity.duration;
//		this.chargingImg = activity.chargingPic;
//		switch (activityStatus) {
//		case 1:
//			this.submitTime=handleTime;break;
//		case 2:
//			this.activityTime=handleTime;break;
//		}
//		this.activityOrderId = card.getCardOrderId();
//		this.uid = card.getUid();
//		this.uname = card.getUserName();
//		this.mac = card.getTerminalMac();
//		this.sn = card.getTerminalSn();
//		this.devicecode = card.getDevicecode();
//	}
	
	
	
	public ActivityOrder(Map<String, String> parametersMap, String activityOrderId, int activityType, int activityStatus, String handleTime) {
		this.activityOrderId = activityOrderId;
		this.activityOrderType = activityType;
		switch (activityType) {
		case 0:
			this.activityOrderTypeName = "免费vip";
			break;
		case 1:
			this.activityOrderTypeName = "vip计费包";
			break;
		case 2:
			this.activityOrderTypeName = "促销活动卡";
			break;
		}
		this.activityOrderStatus = activityStatus;
		switch (activityStatus) {
		// case 1:
		// this.activityOrderStatusName = "已下单";
		// this.submitTime = handleTime;
		// break;
		case 2:
			this.activityOrderStatusName = "已激活";
			this.submitTime = handleTime;
			this.activityTime = handleTime;
			break;
		case 3:
			this.activityOrderStatusName = "激活失败";
			break;
		case 4:
			this.activityOrderStatusName = "已退订";
			break;
		}
		this.chargingId = Integer.parseInt(parametersMap.get("chargingId"));
		this.chargingPrice = Integer.parseInt(parametersMap.get("chargingPrice"));
		this.chargingName = parametersMap.get("chargingName");
		this.chargingDuration = Integer.parseInt(parametersMap.get("chargingDuration"));
		this.chargingImg = parametersMap.get("chargingImg");
		this.uid = Integer.parseInt(parametersMap.get("uid"));
		this.uname = parametersMap.get("uname");
		this.mac = parametersMap.get("mac");
		this.sn = parametersMap.get("sn");
		this.devicecode = parametersMap.get("devicecode");
	}
	
	
	public ActivityOrder(Activity activity,Card card,int activityType,int activityStatus,String handleTime){
		this.activityOrderType = activityType;
		this.activityOrderTypeName="促销活动卡";
		this.activityOrderStatus = activityStatus;
		this.activityOrderStatusName="已激活";
		this.chargingId = activity.chargingId;
		this.chargingPrice = activity.price;
		this.chargingName = activity.chargingName;
		this.chargingDuration = activity.duration;
		this.chargingImg = activity.chargingPic;
		this.activityTime=handleTime;
		this.activityOrderId = card.getCardOrderId();
		this.uid = card.getUid();
		this.uname = card.getUserName();
		this.mac = card.getTerminalMac();
		this.sn = card.getTerminalSn();
		this.devicecode = card.getDevicecode();
	}
	
}
