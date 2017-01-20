package com.core.proxy;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Proxy implements Delayed, Serializable {

	private static final long serialVersionUID = 1L;
	private long timeInterval;// 任务间隔时间,单位ms
	private String ip;
	private int port;
	private boolean availableFlag;
	private boolean anonymousFlag;
	private long lastUseTime;
	private int delay;
	private int failureTimes;// 请求失败次数
	private int successfulTimes;// 请求成功次数

	public Proxy(String ip, int port, long timeInterval) {
		this.timeInterval = TimeUnit.NANOSECONDS.convert(timeInterval, TimeUnit.MILLISECONDS) + System.nanoTime();
		this.ip = ip;
		this.port = port;
	}

	public static void main(String[] args) {
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = TimeUnit.NANOSECONDS.convert(timeInterval, TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(boolean availableFlag) {
		this.availableFlag = availableFlag;
	}

	public boolean isAnonymousFlag() {
		return anonymousFlag;
	}

	public void setAnonymousFlag(boolean anonymousFlag) {
		this.anonymousFlag = anonymousFlag;
	}

	public long getLastUseTime() {
		return lastUseTime;
	}

	public void setLastUseTime(long lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getFailureTimes() {
		return failureTimes;
	}

	public void setFailureTimes(int failureTimes) {
		this.failureTimes = failureTimes;
	}

	public int getSuccessfulTimes() {
		return successfulTimes;
	}

	public void setSuccessfulTimes(int successfulTimes) {
		this.successfulTimes = successfulTimes;
	}

	public int compareTo(Delayed o) {
		Proxy element = (Proxy) o;
		return timeInterval > element.timeInterval ? 1 : (timeInterval < element.timeInterval ? -1 : 0);
	}

	public long getDelay(TimeUnit timeUnit) {
		return timeUnit.convert(timeInterval - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}
