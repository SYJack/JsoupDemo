package com.jack.Jsoupdemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.core.http.HttpClientUtil2;

/**
 * Hello world!
 *
 */
public class App {
	// https://www.zhihu.com/collection/38123480
	public static void main(String[] args) throws Exception {
		// Document numDoc =
		// JsoupUtil.getUrlConnect("https://www.zhihu.com/collection/38123480");
		@SuppressWarnings("unchecked")
		// Set<HashMap<String, Integer>> proxyLists = (HashSet<HashMap<String,
		// Integer>>) HttpClientUtil2
		// .deserializeObject("src/main/resources/proxies");
		// for (HashMap<String, Integer> m : proxyLists) {
		// for (String k : m.keySet()) {
		// System.out.println(k + " : " + m.get(k));
		// }
		// }

		String pageContent = HttpClientUtil2.getWebPage("https://www.zhihu.com/collection/102112319", "106.46.136.27",
				808);
		Document numDoc = Jsoup.parse(pageContent);
		int collectionNum = 0;
		// 获取页数
		Elements pages = numDoc.select("[class=zm-invite-pager]");
		for (Element page : pages) {
			Elements a = page.getElementsByTag("a");
			collectionNum = Integer.parseInt(a.get(2).text());
		}
		// 获取url
		for (int i = 1; i < collectionNum; i++) {
			String urlContent = HttpClientUtil2.getWebPage("https://www.zhihu.com/collection/102112319?page=" + i,
					"106.46.136.27", 808);
			Document urlDoc = Jsoup.parse(urlContent);
			Elements urls = urlDoc.select("[class=zm-item-rich-text expandable js-collapse-body]");
			for (Element url : urls) {
				// System.out.println("http://www.zhihu.com" +
				// url.attr("data-entry-url"));
				ZhiHuPicBean picBean = new ZhiHuPicBean("http://www.zhihu.com" + url.attr("data-entry-url"));
				FileUtils.downLoadPics(picBean, "D:/知乎_编辑推荐/" + picBean.getQuestion());
			}
		}
	}
}
