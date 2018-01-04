package com.hiveview.common;

import java.util.ResourceBundle;

public class ParamConstants extends EnvConstants{

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_param");
	
	public static final String HIVE_PARTNER_ID = R.getString("hive.partnerId");
	public static final String HIVE_PARTNER_KEY = R.getString("hive.partnerKey");
	
	public static final String VIP_PARTNER_KEY = R.getString("vip.partnerKey");
	public static final String LIVE_TOKEN = R.getString("live.token");
	
	//活动鉴权key
	public static final String ACT_KEY = R.getString("activity.key");
	//活动支付DES加密key
	public static final String ACT_PAY_KEY = R.getString("activity.pay.key");
	public static final String SUC = "success";
	public static final String ERR = "error";
	
	public static final Integer ELAPSE_TIME = Integer.parseInt(R.getString("elapse.time"));
	
	// 大麦活动redis缓存key
	public static final String RED_KY_ACT_HOME_PAGE_LIST="getActivityHomePageList";
	// 产品类型-极清首映
	public static final String PRD_TYPE_BULE = "2006";
	// 产品类型-国内VIP点播计费
	public static final String PRD_TYPE_VIP = "2009";
	
	public static void main(String[] args) {
		System.out.println("VIP_PARTNER_KEY==========>" + VIP_PARTNER_KEY);
		System.out.println("LIVE_TOKEN=========>"+LIVE_TOKEN);
		System.out.println("ELAPSE_TIME=========>"+ELAPSE_TIME);
	}
}
