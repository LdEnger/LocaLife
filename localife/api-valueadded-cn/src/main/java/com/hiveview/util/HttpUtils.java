package com.hiveview.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
	
	public static String httpPost(String url, Map<String, String> parms) throws IOException {
		URL postUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(StringUtils.mapToLink(parms));
		out.flush();
		out.close();
		InputStreamReader in = new InputStreamReader(connection.getInputStream());
		BufferedReader reader = new BufferedReader(in);
		String line = reader.readLine();
		reader.close();
		connection.disconnect();
		return line;
	}

	public static String getParameters(HttpServletRequest request) throws IOException {
		StringBuffer buffer = new StringBuffer("");
		InputStream is = request.getInputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			buffer.append(new String(buf, 0, len, "utf-8"));
		}
		return buffer.toString();
	}
}
