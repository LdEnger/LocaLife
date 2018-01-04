package main.java.com.hiveview.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("deprecation")
public class HttpUtils {

	public static String httpGetString(String url) {
		try {
			byte[] resp = httpGet(url);
			if (resp != null) {
				return new String(resp, HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String httpPostString(String url, String entity) {
		try {
			byte[] resp = httpPost(url, entity);
			if (resp != null) {
				return new String(resp, HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String httpPostString(String url, Map<String, String> map) {
		String charset = HTTP.UTF_8;
		int timeout = 60000;
		try {
			byte[] resp = httpPost(url, map, charset, timeout);
			if (resp != null) {
				return new String(resp, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] httpGet(String url) {
		String charset = HTTP.UTF_8;
		int timeout = 60000;
		return HttpUtils.httpGet(url, charset, timeout);
	}

	public static byte[] httpGet(String url, String charset, int timeout) {
		if (url == null || url.length() == 0) {
			System.out.print("httpGet fail, url is null");
			return null;
		}
		try {
			HttpClient httpClient = getNewHttpClient(charset, timeout);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse resp = httpClient.execute(httpGet);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.print("httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			System.out.print("httpGet exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] httpPost(String url, String entity) {
		return HttpUtils.httpPost(url, entity, HTTP.UTF_8, 60000);
	}

	/*
	 * 以下两行代码用于实现发送JSON字符串（JSON请求）
	 * httpPost.setHeader("Accept", "application/json");
	 * httpPost.setHeader("Content-type", "application/json");
	 * */

	public static byte[] httpPost(String url, String entity, String charset, int timeout) {
		if (url == null || url.length() == 0) {
			System.out.print("httpPost fail, url is null");
			return null;
		}
		try {
			HttpClient httpClient = getNewHttpClient(charset, timeout);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(entity));
			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.print("httpPost fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			System.out.print("httpPost exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] httpPost(String url, Map<String, String> params) {
		return HttpUtils.httpPost(url, params, HTTP.UTF_8, 60000);
	}

	public static byte[] httpPost(String url, Map<String, String> params, String charset, int timeout) {
		if (url == null || url.length() == 0) {
			System.out.print("httpPost fail, url is null");
			return null;
		}
		if (params == null) {
			System.out.print("httpPost fail, params is null");
			return null;
		}
		HttpClient httpClient = getNewHttpClient(charset, timeout);
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> _params = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			String val = params.get(key);
			_params.add(new BasicNameValuePair(key, val));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(_params, charset));
			HttpResponse resp = httpClient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.print("httpPost fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			System.out.print("httpPost exception, e = " + e.getMessage());
			return null;
		}
	}

	private static HttpClient getNewHttpClient(String charset, int timeout) {
		DefaultHttpClient defaultHttpClient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, charset);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			defaultHttpClient = new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			defaultHttpClient = new DefaultHttpClient();
		}
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
		return defaultHttpClient;
	}

	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	/**
	 * 取得nginx代理之后的IP地址
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ipFromNginx = request.getHeader("X-Real-IP");
		if (ipFromNginx == null || "".equals(ipFromNginx)) {
			return request.getRemoteAddr();
		} else {
			return ipFromNginx;
		}
	}

	@Deprecated
	public static List<String> stringsToList(String[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		for (int i = 0; i < src.length; i++) {
			result.add(src[i]);
		}
		return result;
	}
	/**
	 * 转成键值对字符串
	 * 
	 * @param params
	 * @return
	 */
	public static String toQueryString(Map<String, Object> params) {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object val = params.get(key);
			buffer.append("&").append(key).append("=").append(val);
		}
		return buffer.substring(1);
	}
	
	public static void main(String[] args) {
		String url ="http://60.206.137.176:18070/public/pkgInnerBuy.json";
		Map<String, String> map =new HashMap<String, String>();
		map.put("pkgId", "agio-14");//活动ID
		map.put("branchId", "231");
		map.put("hallId", "-1");
		map.put("opUserInfo", "xuhaobo@sp.com");
		map.put("userNum", "12321");
		map.put("orderNum", "12321aasdaa");
		map.put("mac", "143DF1F10441");
		map.put("sn", "DMA11111150801090");
		map.put("remark", System.currentTimeMillis()+"");
		String json =HttpUtils.httpPostString(url, map);
		System.out.println(json);
	}
}
