package main.java.com.hiveview.common;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ParamConstants extends EnvConstants{

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_param");
	
	public static final String HIVE_PARTNER_ID = R.getString("hive.partnerId");
	public static final String HIVE_PARTNER_KEY = R.getString("hive.partnerKey");
	
	public static final String VIP_PARTNER_KEY = R.getString("vip.partnerKey");
	public static final String VIP_PARTNER_ID = R.getString("vip.partnerId");
	public static final String LIVE_TOKEN = R.getString("live.token");
	
	public static final String SUB_SYSTEM_KEY = R.getString("subsystem.key");//子系统鉴权key
	
	public static final Integer BJ_BRANCH= Integer.parseInt(R.getString("bj.branch"));//北京分公司ID
	public static final Integer HORIZ_ZONE= Integer.parseInt(R.getString("horiz.zone"));//水平市场战区
	
	public static final Integer GROUP_ROLE = 1; //集团管理员角色
	public static final Integer BRANCH_ROLE = 2; //分公司角色
	public static final Integer HALL_ROLE = 3; //营业厅角色
	public static final Integer ZONE_ROLE = 4; //战区角色
	public static final Integer LOCALLIFE_ROLE = 5; //本地生活管理员
	public static final Integer SHBST_ROLE = 6; //上海百事通mac sn导入角色
	
	// 直播开通接口私钥
	public static final String LIVE_SHA1_KEY = R.getString("live.sha1.key");
	public static final String LIVE_DES_KEY = R.getString("live.des.key");
	
	public static final Map<String, String> authMap =new HashMap<String, String>();
	static{
		authMap.put("isGod", "神级用户");// 可以处理这些特殊权限
		authMap.put("activity/show.html", "活动包生成管理");
		authMap.put("agiopackage/agioShow.html", "麦币包制作");
		authMap.put("sms/confShow.html", "短信配置");
		authMap.put("agiopackage/show.html", "折扣名称管理");
		authMap.put("agiopackage/poolShow.html", "分公司充值");
		authMap.put("boss/showBossOrder.html", "详细订单管理");
	}
	
	public static void main(String[] args) {
		System.out.println("VIP_PARTNER_KEY==========>" + VIP_PARTNER_KEY);
		System.out.println("VIP_PARTNER_ID==========>" + VIP_PARTNER_ID);
		System.out.println("LIVE_TOKEN=========>"+LIVE_TOKEN);
		System.out.println("SUB_SYSTEM_KEY=========>"+SUB_SYSTEM_KEY);
	}
}
