package com.core.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.core.constant.Constants;

public class HttpClientUtil2 {
	private static CloseableHttpClient httpClient; // 模拟客户端
	private static CloseableHttpResponse response; // 返回
	private static RequestConfig requestConfig;
	private static int statusCode;
	static {
		init();
	}

	private static void init() {
		try {
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslSFactory = new SSLConnectionSocketFactory(sslContext);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslSFactory).build();

			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);

			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(Constants.TIMEOUT).setTcpNoDelay(true)
					.build();
			connManager.setDefaultSocketConfig(socketConfig);

			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(500);
			connManager.setDefaultMaxPerRoute(300);
			HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

				public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
					if (executionCount > 2) {
						return false;
					}
					if (exception instanceof InterruptedIOException) {
						return true;
					}
					if (exception instanceof ConnectTimeoutException) {
						return true;
					}
					if (exception instanceof UnknownHostException) {
						return true;
					}
					if (exception instanceof SSLException) {
						return true;
					}
					HttpRequest request = HttpClientContext.adapt(context).getRequest();
					if (!(request instanceof HttpEntityEnclosingRequest)) {
						return true;
					}
					return false;
				}

			};
			HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connManager)
					.setRetryHandler(retryHandler).setDefaultCookieStore(new BasicCookieStore())
					.setUserAgent(Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);
			httpClient = httpClientBuilder.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getWebPage(String url, String hostname, int port) throws IOException {
		HttpGet request = new HttpGet(url);
		HttpHost host = new HttpHost(hostname, port);
		requestConfig = RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT).setConnectTimeout(Constants.TIMEOUT)
				.setConnectionRequestTimeout(Constants.TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).setProxy(host)
				.build();
		return getWebPage(request, "utf-8");
	}

	private static String getWebPage(HttpRequestBase request, String encoding) throws ParseException, IOException {
		CloseableHttpResponse response = null;
		response = getResponse(request);
		String content = EntityUtils.toString(response.getEntity(), encoding);
		request.releaseConnection();
		return content;
	}

	private static CloseableHttpResponse getResponse(HttpRequestBase request)
			throws ClientProtocolException, IOException {

		if (request.getConfig() == null) {
			request.setConfig(requestConfig);
		}
		request.setHeader("User-Agent",
				Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);

		HttpClientContext httpClientContext = HttpClientContext.create();
		response = httpClient.execute(request, httpClientContext);
		// if(statusCode != 200){
		// throw new IOException("status code is:" + statusCode);
		// }
		return response;
	}

	public static int getStatusCode(String url, String hostname, int port) {
		try {
			HttpHost proxy = new HttpHost(hostname, port, "http");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig config = RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT)
					.setConnectTimeout(Constants.TIMEOUT).setConnectionRequestTimeout(Constants.TIMEOUT).setProxy(proxy)
					.build();
			HttpGet request = new HttpGet(url);
			// HTTP请求设置
			request.setConfig(config);
			HttpClientContext httpClientContext = HttpClientContext.create();
			CloseableHttpResponse response = httpclient.execute(request, httpClientContext);
			return response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @throws Exception
	 */
	public static void serializeObject(Object object, String filePath) {
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.flush();
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 反序列化对象
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static Object deserializeObject(String path) throws Exception {
		// InputStream fis = HttpClientUtil.class.getResourceAsStream(name);
		File file = new File(path);
		InputStream fis = new FileInputStream(file);
		ObjectInputStream ois = null;
		Object object = null;
		ois = new ObjectInputStream(fis);
		object = ois.readObject();
		fis.close();
		ois.close();
		return object;
	}
}
