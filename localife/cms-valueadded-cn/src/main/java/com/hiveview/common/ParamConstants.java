package com.hiveview.common;

import java.util.ResourceBundle;

public class ParamConstants extends EnvConstants{

	private static ResourceBundle R = ResourceBundle.getBundle(ENV_VER + "_param");
	
	public static final String HIVE_PARTNER_ID = R.getString("hive.partnerId");
	public static final String HIVE_PARTNER_KEY = R.getString("hive.partnerKey");

	public static final Integer GROUP_ROLE = 1; //集团管理员角色
	public static final Integer BRANCH_ROLE = 2; //分公司角色
	public static final Integer HALL_ROLE = 3; //营业厅角色
	public static final Integer ZONE_ROLE = 4; //战区角色
	
	
	public static void main(String[] args) {

	}
}
