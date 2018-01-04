package com.hiveview.util;

import java.security.MessageDigest;

public class SHA1Utils {

	public static String SHA1(String input) {
		if (input == null || input.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(input.getBytes("UTF-8"));
			byte[] md = digest.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String input = "abcde12345";
		String sign = SHA1Utils.SHA1(input);
		String output = "61d6504733ca7757e259c644acd085c4dd471019";
		System.out.println("sign=>" + sign);
		System.out.println(output.equals(sign));
	}
}
