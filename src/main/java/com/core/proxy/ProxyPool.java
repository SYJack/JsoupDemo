package com.core.proxy;

import java.util.HashMap;
import java.util.Map;

public class ProxyPool {
	public final static Map<String, Class<?>> proxyMap = new HashMap<String, Class<?>>();
	static {
		int page = 10;
		for (int i = 0; i < page; i++) {
//			proxyMap.put("http://www.xicidaili.com/wt/" + i + ".html", XicidailiProxyListPageParser.class);
//			proxyMap.put("http://www.xicidaili.com/nn/" + i + ".html", XicidailiProxyListPageParser.class);
//			proxyMap.put("http://www.xicidaili.com/wn/" + i + ".html", XicidailiProxyListPageParser.class);
//			proxyMap.put("http://www.xicidaili.com/nt/" + i + ".html", XicidailiProxyListPageParser.class);
//			proxyMap.put("http://www.ip181.com/daili/" + i + ".html", Ip181ProxyListPageParser.class);
//			proxyMap.put("http://www.66ip.cn/" + i + ".html", Ip66ProxyListPageParser.class);
			proxyMap.put("http://www.kxdaili.com/dailiip/1/" + i + ".html", IpkxdailiProxyListPageParser.class);
//			for (int j = 1; j < 34; j++) {
//				proxyMap.put("http://www.66ip.cn/areaindex_" + j + "/" + i + ".html", Ip66ProxyListPageParser.class);
//			}
		}
	}
}
