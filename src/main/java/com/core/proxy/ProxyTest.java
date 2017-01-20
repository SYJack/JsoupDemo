package com.core.proxy;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.core.http.HttpClientUtil;

public class ProxyTest {

	public void testProxyServer(String url, String proxyIp, int proxyPort) {

	}

	public static HttpClient getHttpClient(String proxyIp, int proxyPort) throws IOException {
		// CloseableHttpResponse response =
		// HttpClientUtil.getResponse("http://www.zhihu.com");

		return null;
	}
}
