package com.github.rkbalgi.ldbl.ob;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ObRequestObj {

	private String header;
	private byte[] data;

	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private byte[] responseData;

	public ObRequestObj(String header, byte[] data) {
		this.header = header;
		this.data = data;
	}

	public void waitForResponse() {
		lock.lock();
		try {
			condition.awaitUninterruptibly();
		} finally {
			lock.unlock();
		}
	}

	public void signal() {
		lock.lock();
		try {
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public String getHeader() {
		return header;
	}

	public byte[] getData() {
		return data;
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setResponseData(byte[] hsmResponse) {
		this.responseData = hsmResponse;

	}

	public byte[] getResponseData() {
		return (responseData);
	}

}
