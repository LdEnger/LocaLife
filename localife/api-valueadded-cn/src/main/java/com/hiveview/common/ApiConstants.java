package com.hiveview.common;

import java.util.ResourceBundle;

public class ApiConstants extends EnvConstants {

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");

	public static String UPLOAD_PATH = R.getString("upload.path");
	// 域名常量
	public static String BLUE_URL = R.getString("service.blue");
	public static String BILL_URL = R.getString("service.bill");
	public static String VIP_URL = R.getString("service.vip");// vip系统
	public static String LIVE_URL = R.getString("service.live");// vip系统
	public static String PASSPORT_URL = R.getString("service.passport");// 用户系统
	public static String API_UTIL = R.getString("service.util");// api.util地区信息
    public static String API_UTIL_US = R.getString("service.util.us");// api.util.us地区信息
    public static String API_UTIL_US_COUNTRYID = R.getString("service.util.us.countryId");// 默认国家
    public static String API_UTIL_US_PROVINCEID = R.getString("service.util.us.provinceId");// 默认省
	public static String API_UTIL_US_CITYID = R.getString("service.util.us.cityId");// 默认城市

	// 接口常量
	public static String PRODUCT_API = BLUE_URL + "blue-ray/api/r/cp/.json";
	public static String CHARGE_API = BILL_URL + "api/ibs/pricePkg/getPriceList/2005/1.json";
//	public static String CHARGE_LIST_API = VIP_URL + "api/open/vip/vipPackage/getVipPackageList/1.0.json";
	public static String CHARGE_LIST_API  = VIP_URL+ "api/open/vip/vipPackage/getVipPackageList_V2/-1.0.json";//第二版，TV端显示传空
	// public static String VIP_SUB_API = VIP_URL + "api/open/vip/order/saveOrder/orderType-userId-mac-sn-devicecode-duration-1.0.json";
	public static String VIP_SUB_API = VIP_URL + "api/open/vip/order/saveOrder_V2/{0}-{1}-{2}-{3}-{4}-{5}-{6}-1.0.json";
	public static String VIP_ACTIVATION_API = VIP_URL + "api/open/vip/order/updateOrder.json";
	public static String LIVE_OPEN_API = LIVE_URL + "api/open/live/userInfo/applyLiveOpen.json";
	public static String USER_API = PASSPORT_URL + "api/user/getUserInfo.json";
	public static String VIP_CANCEL_API = VIP_URL + "api/open/vip/order/refund.json";
	public static String API_UTIL_AREA = API_UTIL + "api/ip/getAreaCodeFormIpDb/{0}.json";
	public static String LIVE_QUERY_STATUS_API = LIVE_URL + "api/open/live/userInfo/selectOrder.json";
	public static String VIP_SUB_API_V3 = VIP_URL + "api/open/vip/order/saveOrder_V3.json";
	//抽奖等活动常量
	public static String ACT_PAY_URL = R.getString("service.pay");
	public static String ACT_CONSUME_API = ACT_PAY_URL + "give/open/consume.json";
	public static String ACT_AWARD_API = ACT_PAY_URL + "/give/open/award.json";
	public static String ACT_URL = R.getString("service.activity");
	
	public static String ACT_PROD_SERIAL = BLUE_URL + "/blue-ray/api/j/getProductSerial/contentId-contentType.json";
	public static String ACT_PAY_ORDER = BLUE_URL + "blue-ray/api/p/payOrder.json";
	
	//重定向常量
	public static String CMS_ACTIVITY_URL = R.getString("cms.activity.url");

	public static void main(String[] args) {
		System.out.println("VIP_URL=>" + VIP_URL);
		System.out.println("UPLOAD_PATH=>" + UPLOAD_PATH);
		System.out.println("***************************");
		System.out.println("BLUE_URL=>" + BLUE_URL);
		System.out.println("BILL_URL=>" + BILL_URL);
		System.out.println("USER_API=>" + USER_API);
		System.out.println("***************************");
		System.out.println("PRODUCT_API=>" + PRODUCT_API);
		System.out.println("CHARGE_API=>" + CHARGE_API);
		System.out.println("VIP_SUB_API=>" + VIP_SUB_API);
		System.out.println("VIP_ACTIVATION_API=>" + VIP_ACTIVATION_API);
		System.out.println("************************");
		System.out.println("API_UTIL_AREA=>" + API_UTIL_AREA);
		System.out.println("LIVE_OPEN_API=>" + LIVE_OPEN_API);
		System.out.println("LIVE_QUERY_STATUS_API=>" + LIVE_QUERY_STATUS_API);
		System.out.println("CMS_ACTIVITY_URL=>" + CMS_ACTIVITY_URL);
		System.out.println("VIP_SUB_API_V3=>" + VIP_SUB_API_V3);
		System.out.println("API_UTIL_US_COUNTRYID=>" + API_UTIL_US_COUNTRYID);
		System.out.println("API_UTIL_US_PROVINCEID=>" + API_UTIL_US_PROVINCEID);
		System.out.println("API_UTIL_US_CITYID=>" + API_UTIL_US_CITYID);
	}
}
