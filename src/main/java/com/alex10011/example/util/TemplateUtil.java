package com.alex10011.example.util;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

//此处演示使用httpclient使用wechat服务发送模版消息
public class TemplateUtil {
	public final static String pushUrl = "http://127.0.0.1:8000/quickSearch_index";
	private final static String CONTENT_TYPE_TEXT_JSON = "application/json;charset=UTF-8";

	public static void main(String[] args) {
		// accountId
		for (int j = 0; j < 100; j++) {
			for (int i = 0; i < 100; i++) {
				postRequest(pushUrl + "?accountId=1" + i, null);
			}
		}
	}

	public static String postRequest(String url, String parameter) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", CONTENT_TYPE_TEXT_JSON);
			httpPost.setConfig(requestConfig);

			if (parameter != null) {
				StringEntity se = new StringEntity(parameter, Charset.forName("utf-8"));
				se.setContentEncoding("UTF-8");
				se.setContentType(CONTENT_TYPE_TEXT_JSON);
				httpPost.setEntity(se);
			}
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}

}
