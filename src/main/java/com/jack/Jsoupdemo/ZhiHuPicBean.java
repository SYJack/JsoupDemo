package com.jack.Jsoupdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public ZhiHuPicBean(String url) {
		zhihuPicUrl = new ArrayList<String>();
		if (getRealUrl(url) != null) {
			zhihuUrl = getRealUrl(url);
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
