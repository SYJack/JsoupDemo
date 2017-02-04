package com.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.http.ParseException;

import com.core.http.HttpClientUtil2;

public class HttpUtils {
	/**
	 * 设置代理ip
	 * 
	 * @throws IOException
	 */
	public static HashMap<String, Integer> setProxyIp() {
		try {
			// 设置代理ip
			List<String> ipList = new ArrayList<String>();
			HashMap<String, Integer> ipMap = new HashMap<String, Integer>();
			BufferedReader proxyIpReader = new BufferedReader(
					new InputStreamReader(HttpUtils.class.getResourceAsStream("/proxyip.txt")));

			String ip = "";
			while ((ip = proxyIpReader.readLine()) != null) {
				ipList.add(ip);
			}

			Random random = new Random();
			int randomInt = random.nextInt(ipList.size());
			String ipport = ipList.get(randomInt);
			String proxyIp = ipport.substring(0, ipport.lastIndexOf(":"));
			String proxyPort = ipport.substring(ipport.lastIndexOf(":") + 1, ipport.length());
			System.out.println("设置代理ip为：" + proxyIp + "端口号为：" + Integer.parseInt(proxyPort));
			ipMap.put(proxyIp, Integer.parseInt(proxyPort));
			return ipMap;
		} catch (Exception e) {
			System.out.println("重新设置代理ip");
			setProxyIp();
		}
		return setProxyIp();
	}

	public static String getContent(String url) {
		HashMap<String, Integer> ip = setProxyIp();
		for (Entry<String, Integer> entry : ip.entrySet()) {
			try {
				String content = HttpClientUtil2.getWebPage(url, entry.getKey(), entry.getValue());
				if (content == null || content.toString().trim().equals("")) {//
					// 表示ip被拦截或者其他情况
					System.out.println("出现ip被拦截或者其他情况");
					HttpUtils.setProxyIp();
					getContent(url);
				}
				return content;
			} catch (Exception e) {
				// 链接超时等其他情况
				System.out.println("出现链接超时等其他情况");
				getContent(url);// 继续爬取网页
			}
		}
		return getContent(url);
	}

	// public static String getContent(String url) {
	// try {
	// return HttpClientUtil2.getWebPage(url);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	public static void main(String[] args) throws IOException {
		String url = "https://www.zhihu.com/people/chibaole/answers";
		String content = getContent(url);
		System.out.println(content);
	}
}
