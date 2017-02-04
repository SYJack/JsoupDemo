package com.core.proxy;

import java.util.HashMap;
import java.util.Map;

public class ProxyListPageParserFactory {
	private static Map<String, ProxyListPageParser> map = new HashMap<String, ProxyListPageParser>();

	public static ProxyListPageParser getProxyListPageParser(Class<?> cls) {
		String className = cls.getSimpleName();
		if (map.containsKey(className)) {
			return map.get(className);
		} else {
			try {
				ProxyListPageParser parser = (ProxyListPageParser) cls.newInstance();
				className = cls.getSimpleName();
				map.put(className, parser);
				return parser;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
