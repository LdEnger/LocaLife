package main.java.com.hiveview.common;

import java.util.ResourceBundle;

public class ApiConstants extends EnvConstants {

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_api");
	public static String API_DEVICE = R.getString("service.device");// 获取设备信息

	public static String DEVICE_GET_API = API_DEVICE + "device/get/{0}-{1}.json"; // 获取设备信息


	public static String UPLOAD_PATH = R.getString("upload.path");
	public static String WEBPATH = R.getString("file.path");
	//域名常量
	public static String BLUE_URL = R.getString("service.blue");
	public static String BILL_URL = R.getString("service.bill");
	public static String VIP_URL = R.getString("service.vip");//vip系统
	public static String LIVE_URL = R.getString("service.live");//vip系统
	public static String PASSPORT_URL = R.getString("service.passport");//用户系统
	public static String API_UTIL=R.getString("service.util");//api.util地区信息
	public static String LOCAL_LIFE_DOMAIN=R.getString("service.localLife");//本地生活域名
	//接口常量
	public static String PRODUCT_API = BLUE_URL + "blue-ray/api/r/cp/.json";
	public static String CHARGE_API = BILL_URL + "api/ibs/pricePkg/getPriceList/2005/1.json";
//	public static String CHARGE_LIST_API  = VIP_URL+ "api/open/vip/vipPackage/getVipPackageList/1.0.json";
	public static String CHARGE_LIST_API  = VIP_URL+ "api/open/vip/vipPackage/getVipPackageList_V2/0-1.0.json";//第三版，非TV端的显示在增值后台
	//public static String VIP_SUB_API = VIP_URL + "api/open/vip/order/saveOrder/orderType-userId-mac-sn-devicecode-duration-1.0.json";
	public static String VIP_SUB_API = VIP_URL + "api/open/vip/order/saveOrder_V2/{0}-{1}-{2}-{3}-{4}-{5}-{6}-1.0.json";
//	public static String VIP_ACTIVATION_API = VIP_URL + "api/open/vip/order/updateOrder.json";
	public static String VIP_ACTIVATION_API = VIP_URL + "api/open/vip/order/updateOrder_v2.json";//因需要订单生效时间 结束时间，暂时改用这个接口
	public static String VIP_INFO=VIP_URL+"api/open/vip/vipUser/getUserInfo/USERID.json";
	public static String VIP_CANCEL_API = VIP_URL + "api/open/vip/order/refund.json";
	public static String USER_API = PASSPORT_URL + "api/user/getUserInfo.json";
	public static String API_UTIL_AREA=API_UTIL+"api/ip/getAreaCodeFormIpDb/{0}.json";
	public static String LIVE_OPEN_API = LIVE_URL + "api/open/live/userInfo/applyLiveOpen.json";
	public static String LIVE_RENEW_API = LIVE_URL + "api/open/live/userInfo/productRenewal.json";
	public static String LIVE_QUERY_STATUS_API = LIVE_URL + "api/open/live/userInfo/selectOrder.json";
	public static String LIVE_BACK_ORDER_API = LIVE_URL + "api/open/live/userInfo/backOrder.json";
	public static String LIVE_PRODUCT_PACKS_API = LIVE_URL + "api/open/vip/productPack/getProductPacks/{0}/{1}.json";
	public static String PRODUCTPACK_API = LIVE_URL + "api/open/productPack/show.html";
	public static String VIP_SUB_API_V3 = VIP_URL + "api/open/vip/order/saveOrder_V3.json";
	
	public static String REDIS_KEY_API_LIVE_OPEN = "liveOpen_";

	//播控直播开通页面请求地址
	public static String LIVE_NEW_URL = R.getString("live.url");
	public static String LIVE_NEW_VIEW_URL = LIVE_NEW_URL + "userInfo/show/liveView.html?data={0}";
	public static String LIVE_NEW_BATCHVIEW_URL = LIVE_NEW_URL + "userInfo/show/batchLiveView.html?data={0}";

	//播控本地生活
	public static String LOCAL_LIFE_URL = R.getString("localLife.url");
	public static String LOCAL_LIFE_CONTENT_URL = LOCAL_LIFE_URL + "localLifeContent/show.html?data={0}";
	public static String LOCAL_LIFE_RECOMMEND_URL = LOCAL_LIFE_URL + "localLifeRecommend/show.html?data={0}";
	public static String LOCAL_LIFE_LABEL_URL = LOCAL_LIFE_URL + "localLifeLabel/show.html?data={0}";
	public static String LOCAL_LIFE_LOGO_URL = LOCAL_LIFE_URL + "localLifeLogo/show.html?data={0}";

	//增值API，激活麦币调用
	public static String AGIO_URL =R.getString("activity.url");
	
	public static String VIP32_URL = R.getString("service.vip32");// 3.2vip系统
	/**
	 * ?branchId=215&totalAmmount=100000&packageConfId=1
	 */
	public static String AGIO_URL_ADDAGIO =AGIO_URL+"public/branchAddAgioPoolView.json";
	//pkgId=1&branchId=215&hallId=123&opUserInfo=300(银巧)&userNum=&orderNum=&mac=&sn=&remark=&cardPwd=激活码
	public static String AGIO_URL_DELAGIO =AGIO_URL+"public/pkgInnerBuy.json";
	public static void main(String[] args) {
		System.out.println("VIP_URL=>"+VIP_URL);
		System.out.println("UPLOAD_PATH=>" + UPLOAD_PATH);
		System.out.println("***************************");
		System.out.println("BLUE_URL=>" + BLUE_URL);
		System.out.println("BILL_URL=>" + BILL_URL);
		System.out.println("USER_API=>"+USER_API);
		System.out.println("***************************");
		System.out.println("PRODUCT_API=>" + PRODUCT_API);
		System.out.println("CHARGE_API=>" + CHARGE_API);
		System.out.println("VIP_SUB_API=>"+VIP_SUB_API);
		System.out.println("VIP_ACTIVATION_API=>"+VIP_ACTIVATION_API);
		System.out.println("************************");
		System.out.println("API_UTIL_AREA=>"+API_UTIL_AREA);
		System.out.println("LIVE_OPEN_API=>"+LIVE_OPEN_API);
		System.out.println("LIVE_QUERY_STATUS_API=>" + LIVE_QUERY_STATUS_API);
		System.out.println("LIVE_RENEW_API=>" + LIVE_RENEW_API);
		System.out.println("VIP_INFO=>" + VIP_INFO);
		System.out.println("PRODUCTPACK_API=>" + PRODUCTPACK_API);
	}
}
