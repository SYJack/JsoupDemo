package com.core.zhihu.model;

import java.sql.Connection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.core.jdbc.DBStatement;
import com.core.jdbc.UsersDAOImpl;
import com.core.utils.BloomFilter;
import com.core.utils.HttpUtils;

public class ZhiHuSpriderService {

	private static BloomFilter filter = new BloomFilter();

	private static BlockingDeque<String> urlQueue = new LinkedBlockingDeque<String>();

	private static Executor executor = Executors.newFixedThreadPool(10);

	// 数据库连接
	private static UsersDAOImpl daoImpl = new UsersDAOImpl();

	public static void main(String[] args) throws InterruptedException {
		urlQueue.put("https://www.zhihu.com/people/chibaole");
		System.out.println("初始队列大小:" + urlQueue.size());
		System.out.println("开始爬虫.........................................");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					while (true) {
						String zhiHuUserUrl = getUrl();
						if (!filter.contains(zhiHuUserUrl)) {
							filter.add(zhiHuUserUrl);
							if (zhiHuUserUrl != null) {
								getUserInfo(zhiHuUserUrl);
							}
						} else {
							System.out.println("此url存在，不爬了." + zhiHuUserUrl);
						}
					}
				}
			});
			executor.execute(thread);

			// 线程池监听
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							if (((ThreadPoolExecutor) executor).getActiveCount() < 5) {
								System.out.println("---重启线程---");
								Thread t = new Thread(new Runnable() {
									public void run() {
										while (true) {
											String zhiHuUserUrl = getUrl();
											if (!filter.contains(zhiHuUserUrl)) {
												filter.add(zhiHuUserUrl);
												if (zhiHuUserUrl != null) {
													getUserInfo(zhiHuUserUrl);
												}
											} else {
												System.out.println("此url存在，不爬了." + zhiHuUserUrl);
											}
										}
									}
								});
								executor.execute(t);
								if (urlQueue.size() == 0) {
									System.out.println("队列为0了！！！！！！！！！！1");
								}
							}
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	public static String getUrl() {
		try {
			return urlQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static void getUserInfo(String zhiHuUserUrl) {
		ZhihuUserInfo userInfo = new ZhihuUserInfo();
		String content = HttpUtils.getContent(zhiHuUserUrl);
		if (content != null) {
			Element element = Jsoup.parse(content);

			String userName = element.select(".ProfileHeader-name").first().text();

			userInfo.setUserName(userName);

			int size = element.select(".ProfileHeader-infoItem").size();
			if (size == 2) {
				String careerExperience = element.select(".ProfileHeader-infoItem").first().text();
				if (careerExperience != null && careerExperience != "") {
					String[] strs = careerExperience.split(" ");
					for (int i = 0; i < strs.length; i++) {
						if (strs.length > 0) {
							userInfo.setBusiness(strs[0]);// 设置行业
						}
						if (strs.length > 1) {
							userInfo.setCompany(strs[1]);// 设置公司
						}
						if (strs.length > 2) {
							userInfo.setPosition(strs[2]);// 设置职位
						}
					}
				}
			}

			try {
				String educationalExperience = element.select(".ProfileHeader-infoItem").get(1).text();
				if (educationalExperience != null && educationalExperience != "") {
					String[] strs = educationalExperience.split(" ");
					for (int i = 0; i < strs.length; i++) {
						if (strs.length > 0) {
							userInfo.setEducation(strs[0]);// 设置行业
						}
						if (strs.length > 1) {
							userInfo.setMajor(strs[1]);// 设置公司
						}
					}
				}

			} catch (Exception e) {
				System.out.println("---没有找到相关信息---");
			}

			// 看‘关注他’中有无关键字，判断性别
			try {
				String gender = element.select(".ProfileHeader-contentFooter button[icon]").first().text();
				if (gender.contains("他")) {
					userInfo.setGender(0);
				} else if (gender.contains("她")) {
					userInfo.setGender(1);
				} else {
					userInfo.setGender(2);
				}
			} catch (Exception e) {
				System.out.println("---没有找到相关信息---");
			}

			String approvalSumInfo = element.select(".Profile-sideColumnItem").text();

			try {
				// 赞同数
				String approvalSum = approvalSumInfo.substring(approvalSumInfo.indexOf("获得") + 3,
						approvalSumInfo.indexOf("次赞同") - 1);
				userInfo.setStarsNum(approvalSum);
				// 感谢数
				String thankNum = approvalSumInfo.substring(approvalSumInfo.lastIndexOf("获得") + 3,
						approvalSumInfo.indexOf("次感谢") - 1);
				userInfo.setThxNum(thankNum);
			} catch (Exception e) {
				System.out.println("---没有找到相关信息---");
			}

			// 回答数
			String replySum = element.select(".ProfileMain-header").first().select("li").get(1).text().substring(2);
			userInfo.setAnswersNum(replySum);

			// 关注人数
			String followingNum = element.select(".NumberBoard-value").first().text();

			userInfo.setFollowingNum(followingNum);
			// 被关注人数
			String followersNum = element.select(".NumberBoard-value").get(1).text();
			userInfo.setFollowersNum(followersNum);

			// 用户网址
			userInfo.setUrl(zhiHuUserUrl);

			// 打印用户信息
			System.out.println("爬取成功：" + userInfo);

			// 将用户关注的人放入阻塞队列
			addUserUrlIntoQueue(zhiHuUserUrl);
		}

	}

	private static void addUserUrlIntoQueue(String zhiHuUserUrl) {
		String userFollowingContent = HttpUtils.getContent(zhiHuUserUrl + "/following?page=1");
		if (userFollowingContent != null) {
			Element element = Jsoup.parse(userFollowingContent);
			Elements userUrlElements = element.select(".List-item");
			if (userUrlElements.size() != 0) {
				for (Element userUrlElement : userUrlElements) {
					String newUserUrl = userUrlElement.select("a[href]").get(0).attr("href");
					try {
						if (!newUserUrl.contains("org")) {
							urlQueue.put("https://www.zhihu.com" + newUserUrl);
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
