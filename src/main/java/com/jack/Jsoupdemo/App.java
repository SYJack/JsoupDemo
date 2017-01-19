package com.jack.Jsoupdemo;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App {
	// https://www.zhihu.com/collection/38123480
	public static void main(String[] args) throws IOException {
		// Document doc =
		// Jsoup.connect("http://www.zhihu.com/explore/recommendations").get();
		// Elements e = doc.getElementsByClass("question_link");
		// Zhihu zhihu = null;
		// for (Element element : e) {
		// zhihu = new Zhihu("http://www.zhihu.com" + element.attr("href"));
		// FileUtils.writeIntoFile(zhihu.writeString(), "D:/知乎_编辑推荐/" +
		// zhihu.getQuestion() + ".txt");
		// }
		Document numDoc = JsoupUtil.getUrlConnect("https://www.zhihu.com/collection/38123480");

		int collectionNum = 0;
		// 获取页数
		Elements pages = numDoc.select("[class=zm-invite-pager]");
		for (Element page : pages) {
			Elements a = page.getElementsByTag("a");
			collectionNum = Integer.parseInt(a.get(2).text());
		}
		// 获取url
		for (int i = 1; i < collectionNum; i++) {
			Document urlDoc = JsoupUtil.getUrlConnect("https://www.zhihu.com/collection/37506044?page=" + i);
			Elements urls = urlDoc.select("[class=zm-item-rich-text expandable js-collapse-body]");
			for (Element url : urls) {
				System.out.println(url.attr("data-entry-url"));
			}
		}
	}
}
