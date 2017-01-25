package com.core.utils;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<String> lists = new ArrayList<String>();
		lists.add("http://www.csdn.net/");
		lists.add("http://www.baidu.com/");
		lists.add("http://www.google.com.hk");
		lists.add("http://www.cnblogs.com/");
		lists.add("http://www.zhihu.com/");
		lists.add("https://www.shiyanlou.com/");
		lists.add("http://www.google.com.hk");
		lists.add("http://www.google.com.hk");
		lists.add("http://www.csdn.net/");
		BloomFilter filter = new BloomFilter();
		for (int i = 0; i < lists.size(); i++) {
			if (filter.contains(lists.get(i))) {
				System.out.println("contain: " + lists.get(i));
				continue;
			}
			filter.add(lists.get(i));
			System.out.println("add:" + lists.get(i));
		}
	}

}
