package test.java.com.hiveview.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.hiveview.common.ParamConstants;
import com.hiveview.entity.bo.OpResult;
import com.hiveview.entity.card.Card;
import com.hiveview.pay.entity.OpResultTypeEnum;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpGet;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.service.card.CardService;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.DMPayHelper;
import com.hiveview.util.HttpUtils;

public class CardServiceTest extends BaseTest{

	@Autowired
	CardService cardService;
	
	@Test
	public void paramTest(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardPwd", "2016 958 1903 3881 9277");
		map.put("mac", "14:3d:f2:2c:16:03");
		map.put("sn", "DMA30204150174334");
		String link = DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		System.out.println(link);
	}
	
	@Test
	public void testCanvip(){
		String url ="http://api.vip.domybox.com/api/open/vip/order/refund.json";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "1326715");
		map.put("orderId", "743161228112514160929");
		String link = DMPayHelper.toLinkForNotify(map, "6f798dd8c028110d94dd156858e57ea3");
		HiveHttpResponse response = HiveHttpPost.postString(url, link,
				HiveHttpEntityType.STRING);
		System.out.println(response.statusCode+"-->"+response.entityString);
	}
	@Test
	public void cardActityTest(){
		String url = "http://api.activity.pthv.gitv.tv/card/activation.json";
		//String url = "http://localhost:8080/activity_20151125_init/card/activation.json";
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardPwd", "2016 153 8149 8816 9284");//2016 753 8474 9022 9917     //2016 841 3167 7872 7834
		map.put("mac", "143DF2160CDD");
		map.put("sn", "DMA30101140784107");
		String link =  DMPayHelper.toLinkForNotify(map, ParamConstants.VIP_PARTNER_KEY);
		System.out.println(link);
		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode);
		System.out.println(response.entityString);
	}
	
	@Test
	public void activationCardTest(){
		Card card = new Card();
		card.setActivationCode("2016 471 1767 5583 6042");
		OpResult op = cardService.useCard("14:3d:f2:2c:16:03", "DMA30204150174334", card);
		System.out.println(op.toString());
	}
	
	public static void main(String[] args) {
//		//验证接口是否验证mac sn
//		
//		// 战区信息从城市和战区关联表里查询
//		String url ="http://211.103.138.111:8082/public/pkgInnerBuy.json";//ApiConstants.AGIO_URL_DELAGIO;
//		Map<String, String> map =new HashMap<String, String>();
//		map.put("pkgId", "agio-6");//活动ID
//		map.put("branchId", "230");
//		map.put("hallId", "-1");
//		map.put("opUserInfo", "xuhaobo@sp.com");
//		map.put("userNum", "11");
//		map.put("orderNum", "11");
//		map.put("mac", "");
//		map.put("sn", "DMA30101140784107");
//		map.put("remark", System.currentTimeMillis()+230+"");
//		String json =HttpUtils.httpPostString(url, map);
//		System.out.println(json);
//		OpResult op =new OpResult(OpResultTypeEnum.BUNSHORT,"调用接口失败");
//		if(!"".equals(json)){
//			op =JSONObject.parseObject(json,OpResult.class);
//		}
//		System.out.println(op.getCode());
		
		//------------------------3.2vip激活接口---------
//		String url = "http://api.activity.pthv.gitv.tv/card/activation.json";
//		String url = "http://211.103.138.111:8082/card/activation.json";
		String url = "http://api.activity.domybox.com/card/activation.json";
		//String url = "http://localhost:8080/activity_20151125_init/card/activation.json";
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("cardPwd", "2016 512 9719 4950 2412");//2016 753 8474 9022 9917     //2016 841 3167 7872 7834
//		map.put("mac", "143DF25DBB53");
//		map.put("sn", "DMD3330E170268720");
//		String link =  DMPayHelper.toLinkForNotify(map, "6f798dd8c028110d94dd156858e57ea3");
//		System.out.println(link);
////		url ="http://211.103.138.111:8082/card/activation.json";
////		link ="cardPwd=2016 261 1762 1984 8808&mac=143DF2160CDD&sn=143DF2160CDD&sign=db0320b45048c5ebf267ebccf0fdcf41";
//		HiveHttpResponse response = HiveHttpPost.postString(url, link, HiveHttpEntityType.STRING);
//		System.out.println(response.statusCode);
//		System.out.println(response.entityString);
		
		//----------------------3.2vip注销------------------------
//		String vip32 ="http://60.206.137.176:18130/";
       String vip32 ="http://124.192.140.228:18130/"; //线上ip
	//	String vip32 ="http://api.bc.pthv.gitv.tv/";
//		String tempurl =vip32+"api/open/special/templet/getTemplet/DM1001-143DF2160CDD-DMA30101140784107-1.0.json";
		String exiturl =vip32+"api/open/special/vipOrder/refundFreeOrder.json";
//		HiveHttpResponse res = HiveHttpGet.getEntity(tempurl, HiveHttpEntityType.STRING);
		String templetId = "1";
//		if (res != null) {
//			String entityString = res.entityString;
//			System.out.println(entityString);
//			if (entityString != null) {
//				JSONObject jo = JSONObject.parseObject(entityString);
//				templetId = jo.getJSONObject("data").getJSONObject("result").getString("templetId");
//			}
//		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("templateId", templetId);//2016 753 8474 9022 9917     //2016 841 3167 7872 7834
		//map.put("orderId", "31706231306398187");//订单号
	    //map.put("userId", "5063523"); //用户ID
		//map.put("versionNum", "3.1");//退单版本号(空值默认请求3.2退单，传入字符串"3.1"请求3.1退单)
		map.put("orderId", "903171213123749202700");//订单号
		map.put("userId", "2078872"); //用户ID
		map.put("versionNum", "3.2");//退单版本号(空值默认请求3.2退单，传入字符串"3.1"请求3.1退单)
		String link =  DMPayHelper.toLinkForNotifyWithKey(map, "863b4ec37d93eb96276ca74d04edf66f");
		System.out.println(link);
		HiveHttpResponse response = HiveHttpPost.postString(exiturl, link, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode);
		System.out.println(response.entityString);
	}
}
