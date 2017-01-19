package com.jack.Jsoupdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public static void main(String[] args) throws IOException {
		Document doc = JsoupUtils.getUrlConnect("http://www.zhihu.com/explore/recommendations");
		Elements e = doc.getElementsByClass("question_link");
		for (Element element : e) {
			String url = JsoupUtils.getRealUrl("http://www.zhihu.com" + element.attr("href"));
			if (url != null) {
				// System.out.println(url);
				Document answerDoc = JsoupUtils.getUrlConnect(url);
				Elements answer_nums = answerDoc.select("[class=zm-editable-content]");
				// Element element2 = answer_nums.first();
				// String answer_num = element2.text().split(" ")[0];
				// System.out.println(answer_num);
				for (Element element2 : answer_nums) {
					System.out.println(element2.getElementsByTag("img"));
				}
			}
		}
	}

}
