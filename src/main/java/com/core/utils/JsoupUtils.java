package com.core.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

public class JsoupUtils {
	// jsoup get请求
	public static Document getUrlConnect(String url) {
		try {
			Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate,br")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Referer", "https://www.zhihu.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					.get();

			return doc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// jsoup get请求
	public static Response getUrlResponse(String url) {
		try {
			Response response = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate,br")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Referer", "https://www.zhihu.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					.execute();

			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	// public static Document postUrlConnect(String url,Map) {
	// try {
	// // 获取请求连接
	// Connection con = Jsoup.connect(url);
	// con.data("", "");
	// con.data(getHeardParams()).data()
	// post();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	public static String getRealUrl(String url) {
		// 将http://www.zhihu.com/question/22355264/answer/21102139
		// 转化成http://www.zhihu.com/question/22355264
		// 否则不变
		Pattern pattern = Pattern.compile("question/(.*?)/");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return "http://www.zhihu.com/question/" + matcher.group(1);
		}
		return null;
	}
}
