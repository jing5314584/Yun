package com.yun.wx.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.util.Args;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author wangkai
 * @2016年5月31日 下午8:39:32
 * @desc:通用工具类
 */
public class HttpUtil {


	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return 返回微信服务器响应的信息
	 * @throws Exception
	 */
	public static String httpsRequest(String requestUrl, String requestMethod,
			String outputStr) throws Exception {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new FsTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			System.out.println("连接超时：{}");
			throw new RuntimeException("链接异常" + ce);
		} catch (Exception e) {
			System.out.println("https请求异常：{}");
			throw new RuntimeException("https请求异常" + e);
		}
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static Token getToken(String appid, String appsecret) {
		Args.notNull(appid, "appid ");
		Args.notNull(appsecret, "appsecre ");
		Token token = null;
		String requestUrl = ConfigUtil.TOKEN_URL.replace("APPID", appid)
				.replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		String httpsRequest = null;
		try {
			httpsRequest = httpsRequest(requestUrl, "GET", null);
		} catch (Exception e) {
			System.out.println("http 请求异常 cause " + e.getMessage());
		}
		// JSONObject jsonObject =
		// JSONObject.fromObject(httpsRequest(requestUrl, "GET", null));
		JSONObject jsonObject = JSONObject.parseObject(httpsRequest);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				token = null;
				System.out.println("获取token失败 errcode:{} errmsg:{}");
				// 获取token失败
//				System.out.println("获取token失败 errcode:{} errmsg:{}",
//						jsonObject.getInteger("errcode"),
//						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}

	public static String urlEncodeUTF8(String source) {
		Args.notNull(source, "source");
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}