package main.java.com.hiveview.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Title：HTTP 工具类
 * Description：
 * Company：hiveview.com
 * Author：徐浩波
 * Email：xuhaobo@hiveview.com 
 * 2014-5-28下午12:32:00
 */
public class Http {
	final static int timeout =19000;
	/**
	 * 概述：get请求获取数据
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2014-6-12上午9:18:58
	 */
	public static String get(String url){
		String charset ="UTF-8";
		return get(url,charset);
	}
	/**
	 * 概述：get请求获取数据
	 * 返回值：String
	 * 入参：请求地址，编码集
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2014-6-12上午9:18:30
	 */
	public static String get(String url,String charset) {
		try {
			URL httpURL = new URL(url);
			HttpURLConnection http = (HttpURLConnection) httpURL.openConnection();
			http.setConnectTimeout(timeout);
			http.setReadTimeout(timeout);
			BufferedReader br = new BufferedReader(new InputStreamReader(
						http.getInputStream(),charset));
				StringBuilder sb = new StringBuilder();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
					sb.append("\n");
				}
				br.close();
				return sb.toString();
		}catch(Exception e){ e.printStackTrace(); } 
		return null;
	}
	/**
	 * 概述：post调用数据
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2014-6-12上午9:18:06
	 */
	public static String post(String url,String data,String charset) {
		try {
			URL httpURL = new URL(url);
			HttpURLConnection http = (HttpURLConnection) httpURL.openConnection();
			http.setConnectTimeout(timeout);
			http.setReadTimeout(timeout);
			http.setDoOutput(true);
			http.setDoInput(true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(http.getOutputStream(),charset));
			bw.write(data);
			bw.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
						http.getInputStream(),charset));
				StringBuilder sb = new StringBuilder();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
					sb.append("\n");
				}
				br.close();
				return sb.toString();
		}catch(Exception e){ e.printStackTrace(); } 
		return null;
	}
	
	/****以下内容暂时没用********/

	public static final String IPhone = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
	public static final String Android = "Mozilla/5.0 (Linux;U;Android 2.2.1;zh-cn;I7500 Build FRG83) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1";
	public static final String IPad = "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10";
	public static final String Navigator = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080219 Firefox/2.0.0.12 Navigator/9.0.0.6";
	public static final String Maxthon = "Mozilla/5.0 (compatible; U) AppleWebKit/533.1 (KHTML, like Gecko) Maxthon/3.0.8.2 Safari/533.1";
	public static final String Opera = "Opera/9.80 (compatible; U) Presto/2.7.39 Version/11.00";
	public static final String Safari = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7) AppleWebKit/534.16+ (KHTML, like Gecko) Version/5.0.3 Safari/533.19.4";
	public static final String Firefox = "Mozilla/5.0 (compatible; rv:2.0) Gecko/20110101 Firefox/4.0";
	public static final String Chrome = "Mozilla/5.0 (compatible) AppleWebKit/534.21 (KHTML, like Gecko) Chrome/11.0.682.0 Safari/534.21";
	public static final String IE9 = "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1)";
	public static final String IE8 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
	public static final String IE7 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)";
	public static final String IE6 = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)";
	/**
	 * 取文本之间
	 * @param value 字符串
	 * @param left 左边
	 * @param right 右边
	 * @return 中间子串
	 */
	public static String mid(String value, String left, String right) {
		try {
			int index = value.indexOf(left) + left.length();
			return value.substring(index, value.indexOf(right, index));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		String data="actionType=0000&mac=10000000&sn=haobo56";
		String url ="http://211.103.138.120:8040/data_collect/data/LogForV2.json";
//		String url ="http://deviceapi.pthv.gitv.tv/device/uploadDeviceInfo.json";
//		String result =post(url, data, "utf-8");
//		System.out.println(result);
		
//		String url ="http://211.103.138.111/log.json";
//		String data= "actionType=0000&cc=dd";
		String result =post(url, data, "utf-8");
		System.out.println(result);
	}
}
