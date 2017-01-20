package com.core.proxy;

import com.core.http.AbstractHttpClient;

public class ProxyHttpClient extends AbstractHttpClient {
	private volatile static ProxyHttpClient instance;

	public static ProxyHttpClient getInstance() {
		if (instance == null) {
			synchronized (ProxyHttpClient.class) {
				if (instance == null) {
					instance = new ProxyHttpClient();
				}
			}
		}
		return instance;
	}

	public ProxyHttpClient() {
		initThreadPool();
		initProxy();
	}

	private void initThreadPool() {

	}

	private void initProxy() {

	}
}
