package test.java.com.hiveview.test.api;

import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.test.base.BaseTest;

public class VipApiTest extends BaseTest{
	
	private static String url = "http://cms.activity.pthv.gitv.tv/api/vip/getCardList/TYPE-BEGINDATE-ENDDATE.json";
	private static String type="2"; //1：激活 2：统计进账 3：注销
	private static String beginDate="20160912"; 
	private static String endDate="20160913"; 
	
	public static void main(String[] args){
		String _url = url.replaceAll("TYPE", type).replaceAll("BEGINDATE",beginDate).replaceAll("ENDDATE",endDate);
		HiveHttpResponse result = HiveHttpGet.getEntity(_url, 1);
		System.out.println(result);
	}

}
