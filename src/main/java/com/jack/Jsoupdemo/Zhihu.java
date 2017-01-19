package com.jack.Jsoupdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Zhihu {
	private String question;// 问题
	private String questionDescription;// 问题描述
	private String zhihuUrl;
	private ArrayList<String> answers;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getZhihuUrl() {
		return zhihuUrl;
	}

	public void setZhihuUrl(String zhihuUrl) {
		this.zhihuUrl = zhihuUrl;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	public Zhihu(String url) throws IOException {
		this.question = "";
		this.questionDescription = "";
		this.zhihuUrl = "";
		this.answers = new ArrayList<String>();

		if (getRealUrl(url)) {

			System.out.println("正在抓取:" + zhihuUrl);
			Document doc = Jsoup.connect(zhihuUrl).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate,br")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Referer", "https://www.zhihu.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					.timeout(3000).get();

			// 问题
			Element title = doc.getElementById("zh-question-title");
			question = title.text();

			// 问题详情
			Element detail = doc.getElementById("zh-question-detail");
			questionDescription = detail.text();

			// 答案
			Elements elementsAnswers = doc.getElementsByClass("zm-editable-content clearfix");
			for (Element answer : elementsAnswers) {
				answers.add(answer.text());
			}
			zhihuUrl = url;
		}

	}

	// 处理url
	public boolean getRealUrl(String url) {
		// 将http://www.zhihu.com/question/22355264/answer/21102139
		// 转化成http://www.zhihu.com/question/22355264
		// 否则不变
		Pattern pattern = Pattern.compile("question/(.*?)/");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			zhihuUrl = "http://www.zhihu.com/question/" + matcher.group(1);
		} else {
			return false;
		}
		return true;
	}

	public String writeString() {
		String result = "";
		result += "问题：" + question + "\r\n";
		result += "描述：" + questionDescription + "\r\n";
		result += "链接：" + zhihuUrl + "\r\n";
		for (int i = 0; i < answers.size(); i++) {
			result += "回答" + i + "：" + answers.get(i) + "\r\n";
		}
		result += "\r\n\r\n";
		return result;
	}

}
