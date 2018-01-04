package main.java.com.hiveview.download;

import java.security.MessageDigest;

/**
 * MD5通用类
 * 
 * @author gaozhenhai
 * @since 2013.01.15
 * @version 1.0.0_1
 * 
 */
public class MD5 {
    /**
     * MD5方法
     * 
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
	public static String md5(String text, String key) throws Exception {
		byte[] bytes = (text + key).getBytes();
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		bytes = messageDigest.digest();
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i ++)
		{
			if((bytes[i] & 0xff) < 0x10)
			{
				sb.append("0");
			}

			sb.append(Long.toString(bytes[i] & 0xff, 16));
		}
		
		return sb.toString().toLowerCase();
	}
	
	/**
	 * MD5验证方法
	 * 
	 * @param text 明文
	 * @param key 密钥
	 * @param md5 密文
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String key, String md5) throws Exception {
		String md5Text = md5(text, key);
		if(md5Text.equalsIgnoreCase(md5))
		{
			return true;
		}

			return false;
	}
	/**
	 * 概述：md5 32位
	 * 返回值：String
	 * 作者：徐浩波  xuhaobo@hiveview.com 
	 * 创建日期：2016-12-20上午10:21:23
	 */
		public static String md5(String inStr) {
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
				return "";
			}
			char[] charArray = inStr.toCharArray();
			byte[] byteArray = new byte[charArray.length];

			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);

			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		}
		public static void main(String[] args) {
//			System.out.println(md5("rP7s8747"));//北分
			System.out.println(md5("ty95079!"));//山西分公司
			System.out.println(md5("mQwSO11O"));//东莞
			System.out.println(md5("tpqkkqFh"));//大连
		}
}