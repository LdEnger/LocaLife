package main.java.com.hiveview.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.hiveview.pay.http.HiveHttpQueryString;

public class DMPayHelper {

	public static String toLinkForNotify(Map<String, String> orderMap, String partnerKey) {
		//生成通知参数
		String _params = HiveHttpQueryString.buildQuery(orderMap);
		//生成通知签名
		String data = _params + "&partnerKey=" + partnerKey;
		String sign = DigestUtils.md5Hex(data);
		//拼接业务参数和签名
		_params = _params + "&sign=" + sign;
		return _params;
	}
	/**
	 * 概述：vip3.2退订要用
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2017-3-27下午2:51:54
	 */
	public static String toLinkForNotifyWithKey(Map<String, String> orderMap, String partnerKey) {
		//生成通知参数
		String _params = HiveHttpQueryString.buildQuery(orderMap);
		//生成通知签名
		String data = _params + "&key=" + partnerKey;
		String sign = DigestUtils.md5Hex(data);
		//拼接业务参数和签名
		_params = _params + "&sign=" + sign;
		return _params;
	}
	
	public static String toLinkForNotifyToBase(Map<String, String> orderMap, String partnerKey) {
		//生成通知参数
		String _params = HiveHttpQueryString.buildQuery(orderMap);
		//生成通知签名
		String data = _params + "&partnerKey=" + partnerKey;
		String sign = "";
		try {
			sign = DigestUtils.md5Hex(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return sign;
		}
		//拼接业务参数和签名
		_params = _params + "&sign=" + sign;
		return _params;
	}
	
	/**
	* 生成sign
	* @param orderMap
	* @param partnerKey
	* @return
	*/ 
	public static String toSignForNotify(Map<String, String> orderMap, String partnerKey) {
		//生成通知参数
				String _params = HiveHttpQueryString.buildQuery(orderMap);
				//生成通知签名
				String data = _params + "&partnerKey=" + partnerKey;
				return DigestUtils.md5Hex(data);
	}
	
	public static String toLinkForNotify(Map<String, String> orderMap) {
		//生成通知参数
		return HiveHttpQueryString.buildQuery(orderMap);
	}
	
	public static String toSubSystemKey(Map<String, String> orderMap, String key) {
		//生成通知参数
		String _params = HiveHttpQueryString.buildQuery(orderMap);
		//生成通知签名
		String data = _params + "&key=" + key;
		String sign = DigestUtils.md5Hex(data);
		//拼接业务参数和签名
		_params = _params + "&sign=" + sign;
		return _params;
	}
}
