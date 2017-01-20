package com.jack.Jsoupdemo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.core.proxy.Ip181ProxyListPageParser;
import com.core.proxy.Proxy;
import com.core.proxy.ProxyHttpClient;
import com.core.zhihu.model.Page;

public class Ip181ProxyListPageParserTest {

	public static void main(String[] args) throws IOException {
		System.out.println(Charset.defaultCharset().toString());
		Page page = ProxyHttpClient.getInstance().getWebPage("http://www.ip181.com/daili/1.html", "gb2312");
		List<Proxy> urlList = new Ip181ProxyListPageParser().parse(page.getHtml());
		for (Proxy proxy : urlList) {
			System.out.println(proxy.getIp());
			System.out.println(proxy.getPort());
		}
		System.out.println(urlList.size());
	}

}
