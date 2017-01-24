package com.core.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;

import com.core.http.HttpClientUtil2;
import com.core.zhihu.model.Page;

public class ProxyTest {

	public void testProxyServer(String url, String proxyIp, int proxyPort) {

	}

	public static HttpClient getHttpClient(String proxyIp, int proxyPort) throws IOException {

		return null;
	}

	public static void main(String[] args) throws IOException {
		for (int i = 1; i < 8; i++) {
			System.out.println("解析第" + i + "页");
			Page page = ProxyHttpClient.getInstance().getWebPage("http://www.xicidaili.com/nn/" + i + ".html",
					"gb2312");
			List<Proxy> urlList = new XicidailiProxyListPageParser().parse(page.getHtml());
			Map<String, Integer> proxyMap = new HashMap<String, Integer>();
			Set<Map<String, Integer>> sets = new HashSet<Map<String, Integer>>();
			for (Proxy proxy : urlList) {
				int code = HttpClientUtil2.getStatusCode("http://www.zhihu.com", proxy.getIp(), proxy.getPort());
				if (code == HttpStatus.SC_OK) {
					System.out.println(proxy.getIp() + ":" + proxy.getPort());
					proxyMap.put(proxy.getIp(), proxy.getPort());
					sets.add(proxyMap);
				} else if (code == HttpStatus.SC_FORBIDDEN) {

				}
			}
			HttpClientUtil2.serializeObject(sets, "src/main/resources/proxies");
		}
	}
}
