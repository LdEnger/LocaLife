package test.java.com.hiveview.test.api;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hiveview.common.ApiConstants;
import org.testng.annotations.Test;

import com.hiveview.common.ParamConstants;
import com.hiveview.pay.encryption.DES;
import com.hiveview.pay.http.HiveHttpEntityType;
import com.hiveview.pay.http.HiveHttpPost;
import com.hiveview.pay.http.HiveHttpResponse;
import com.hiveview.test.base.BaseTest;
import com.hiveview.util.SHA1Utils;

public class LiveApiTest extends BaseTest {

	public static void main(String[] args) {
		String viewdata = "viewdata";
		String viewdataurl = ApiConstants.LIVE_NEW_VIEW_URL;
		viewdataurl = MessageFormat.format(viewdataurl, viewdata);
		System.out.println(viewdataurl);


		String batchviewdata = "batchviewdata";
		String batchviewdataurl = ApiConstants.LIVE_NEW_BATCHVIEW_URL;
		batchviewdataurl = MessageFormat.format(batchviewdataurl, batchviewdata);
		System.out.println(batchviewdataurl);
	}

	//@Test
	public void getProductListTest() throws Exception {
		String url = "http://127.0.0.1:8080/cms-activity-cn/api/live/getProductList.json";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userMail", "jp@gwbnsd.com");
		
		String pwd = DES.encrypt("04430971", ParamConstants.LIVE_DES_KEY);
		System.out.println("加密后"+pwd);
		String encoderPwd = URLEncoder.encode(pwd, "UTF-8");
		System.out.println("转码后"+encoderPwd);
		map.put("userPwd", pwd);
		
		map.put("key", ParamConstants.LIVE_SHA1_KEY);
		map.put("timestamp", "1466154141");
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		StringBuffer buf = new StringBuffer();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			buf.append(map.get(list.get(i)));
		}
		System.out.println(buf);
		String output = SHA1Utils.SHA1(buf.toString());
		System.out.println("sha1加密："+output);
		map.put("sign", output);
		map.remove("key");
		HiveHttpResponse response = HiveHttpPost.postMap(url, map, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode + "       " + response.entityString);
	}

    @Test
	public void openTest() {
		String url = "http://127.0.0.1:8080/api/live/open.json";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userMail", "yuanshiming@btte.net");
		System.out.println(DES.encrypt("111111", ParamConstants.LIVE_DES_KEY));
		map.put("userPwd", DES.encrypt("111111", ParamConstants.LIVE_DES_KEY));
		map.put("mac", "143DF1F100BD");
		map.put("sn", "DMA11111150800190");
		map.put("closeTime", "2017-08-01");
		map.put("productId", "14");
		map.put("productName", "山东省-青岛-默认包");
		map.put("key", ParamConstants.LIVE_SHA1_KEY);
		map.put("timestamp", "1465974376");
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		StringBuffer buf = new StringBuffer();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			buf.append(map.get(list.get(i)));
		}
		System.out.println("加密前：" + buf);
		String output = SHA1Utils.SHA1(buf.toString());
		System.out.println("加密后：" + output);
		map.put("sign", output);
		map.remove("key");
		HiveHttpResponse response = HiveHttpPost.postMap(url, map, HiveHttpEntityType.STRING);
		System.out.println(response.statusCode + "       " + response.entityString);
		System.out.println();
	}

	//@Test
	public void signTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userMail", "ljp@gwbnsd.com");
		String pw = DES.encrypt("04430971", ParamConstants.LIVE_DES_KEY);
		System.out.println("加密密码:" + pw);
		map.put("userPwd", pw);
		map.put("key", ParamConstants.LIVE_SHA1_KEY);

		String tm = "1466149134";
		map.put("timestamp", tm);
		System.out.println("timestamp::" + tm);

		List<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		StringBuffer buf = new StringBuffer();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			buf.append(map.get(list.get(i)));
		}
		System.out.println(buf);
		String output = SHA1Utils.SHA1(buf.toString());
		System.out.println("sha1:::" + output);
	}

//	public static void main(String[] args) throws Exception {
//
////		long a = System.currentTimeMillis();
////		long b = a / 1000;
////
////		System.out.println(b);
////
////		Date c = new Date(b * 1000);
////
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
////		System.out.println(sdf.format(c));
////		SysUser a = new SysUser();
////		System.out.println(a==null);
////		System.out.println(a.getUserId()==null);
////		System.out.println(a.toString());
////		Map<String, String> m = new HashMap<String, String>();
////		m.put("a","1");
////		m.put("b","2");
////		System.out.println(m);
////
////		Map<String, String> map1 =m;
////		map1.remove("a");
////
////		System.out.println(m);
////
////		long time = System.currentTimeMillis();
////		System.out.println("北京"+System.currentTimeMillis());
////
////		System.out.println(TimeZone.getTimeZone("Asia/Chungking").getDisplayName());
////		System.out.println("-----------");
////		for (String ID : TimeZone.getAvailableIDs()) {
////			System.out.println(ID + "\t" + TimeZone.getTimeZone(ID).getDisplayName());
////		}
//		Integer a = new Integer(300);
//		Integer b = new Integer(400);
//
//		System.out.println(a.compareTo(b));
//		System.out.println(a.compareTo(0));
//
//		for (OpResultTypeEnum s : OpResultTypeEnum.values())
//            System.out.println(s.getCode() + "-------" + s.getTypeName());
//	}

}
