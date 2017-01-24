package com.jack.Jsoupdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.core.http.HttpClientUtil2;

public class ZhiHuPicBean {
	String zhihuUrl;// 网页链接
	String question;// 问题;
	List<String> zhihuPicUrl;// 图片链接

	public String getZhihuUrl() {
		return zhihuUrl;
	}

	public void setZhihuUrl(String zhihuUrl) {
		this.zhihuUrl = zhihuUrl;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getZhihuPicUrl() {
		return zhihuPicUrl;
	}

	public void setZhihuPicUrl(List<String> zhihuPicUrl) {
		this.zhihuPicUrl = zhihuPicUrl;
	}

	public ZhiHuPicBean(String url) throws IOException {
		zhihuPicUrl = new ArrayList<String>();
		if (getRealUrl(url) != null) {
			zhihuUrl = getRealUrl(url);
			String urlContent = HttpClientUtil2.getWebPage(zhihuUrl, "120.37.52.38", 8118);
			Document doc = Jsoup.parse(urlContent);

			// 问题
			Element title = doc.getElementById("zh-question-title");
			question = title.text();

			Pattern pattern;
			Matcher matcher;
			// 图片链接
			pattern = Pattern.compile("</noscript><img.+?src=\"(https.+?)\".+?");
			matcher = pattern.matcher(urlContent);
			boolean isFind = matcher.find();
			while (isFind) {
				zhihuPicUrl.add(matcher.group(1));
				isFind = matcher.find();
			}

		}
	}

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
