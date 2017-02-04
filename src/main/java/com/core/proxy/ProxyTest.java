package com.core.proxy;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.HttpClient;

import com.core.utils.BloomFilter;
import com.core.zhihu.model.Page;

public class ProxyTest {

	public void testProxyServer(String url, String proxyIp, int proxyPort) {

	}

	public static HttpClient getHttpClient(String proxyIp, int proxyPort) throws IOException {

		return null;
	}

	public static void main(String[] args) throws IOException {
		// for (int i = 1; i < 8; i++) {
		// System.out.println("解析第" + i + "页");
		// Page page =
		// ProxyHttpClient.getInstance().getWebPage("http://www.xicidaili.com/nn/"
		// + i + ".html",
		// "gb2312");
		// List<Proxy> urlList = new
		// XicidailiProxyListPageParser().parse(page.getHtml());
		// Map<String, Integer> proxyMap = new HashMap<String, Integer>();
		// Set<Map<String, Integer>> sets = new HashSet<Map<String, Integer>>();
		// for (Proxy proxy : urlList) {
		// int code = HttpClientUtil2.getStatusCode("http://www.zhihu.com",
		// proxy.getIp(), proxy.getPort());
		// if (code == HttpStatus.SC_OK) {
		// System.out.println(proxy.getIp() + ":" + proxy.getPort());
		// proxyMap.put(proxy.getIp(), proxy.getPort());
		// sets.add(proxyMap);
		// } else if (code == HttpStatus.SC_FORBIDDEN) {
		//
		// }
		// }
		// HttpClientUtil2.serializeObject(sets, "src/main/resources/proxies");
		// }

		Map<String, Integer> proxyMap = null;
		for (String url : ProxyPool.proxyMap.keySet()) {
			System.out.println("解析" + url);
			ProxyListPageParser parser = ProxyListPageParserFactory.getProxyListPageParser(ProxyPool.proxyMap.get(url));
			Page page = ProxyHttpClient.getInstance().getWebPage(url, "gb2312");
			List<Proxy> proxyList = parser.parse(page.getHtml());

			proxyMap = new HashMap<String, Integer>();

			BloomFilter filter = new BloomFilter();
			System.out.println("写入set");
			if (proxyList != null) {
				for (Proxy proxy : proxyList) {
					if (filter.contains(proxy.getIp() + proxy.getPort())) {
						continue;
					}
					filter.add(proxy.getIp() + proxy.getPort());
					proxyMap.put(proxy.getIp(), proxy.getPort());
				}
			}
			writeTo("src/main/resources/proxyip.txt", proxyMap);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("序列化");
		// HttpClientUtil2.serializeObject(sets, "src/main/resources/proxies");

		// try {
		// Set<Map<String, Integer>> set = (Set<Map<String, Integer>>)
		// HttpClientUtil2
		// .deserializeObject("src/main/resources/proxies");
		// for (Map<String, Integer> map : set) {
		// for (Entry<String, Integer> entry : map.entrySet()) {
		// System.out.println("ip= " + entry.getKey() + " and port= " +
		// entry.getValue());
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static void writeTo(String fileName, Map<String, Integer> content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			for (Entry<String, Integer> entry : content.entrySet()) {
				System.out.println("ip= " + entry.getKey() + " and port= " + entry.getValue());
				randomFile.writeBytes(entry.getKey() + ":" + entry.getValue() + "\r\n");
			}
			randomFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
