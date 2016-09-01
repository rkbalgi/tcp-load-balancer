package com.github.rkbalgi.ldbl.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.conf.Properties;
import com.github.rkbalgi.ldbl.in.Inflights;
import com.github.rkbalgi.ldbl.ob.HandleObRequest;
import com.github.rkbalgi.ldbl.ob.ObRequestObj;

public class Utils {

	private static Logger log = LoggerFactory.getLogger(Utils.class);
	private static AtomicInteger counter = new AtomicInteger(0);
	private static ExecutorService service = Executors.newFixedThreadPool(4);
	private static ScheduledExecutorService sService = Executors
			.newScheduledThreadPool(2);

	private static final int hsmTimeout = Properties.getObTimeout();

	public static int nextId() {

		int nextVal = counter.incrementAndGet();
		if (nextVal > 99999) {
			counter.set(0);
		}
		return (nextVal);

	}

	public static void handleHsmResponse(final byte[] hsmResponse) {

		service.submit(new Runnable() {

			@Override
			public void run() {

				byte[] headerBytes = Arrays.copyOfRange(hsmResponse, 0, 12);
				try {
					String header = new String(headerBytes, "US-ASCII");
					ObRequestObj command = Inflights.remove(header);
					if (command == null) {
						log.warn(String.format(
								"Message with header [%s] timed out.", header));
					} else {
						command.setResponseData(hsmResponse);
						command.signal();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
		});

	}

	public static void startTimer(final ObRequestObj command) {
		sService.schedule(new Runnable() {

			@Override
			public void run() {
				ObRequestObj inCommand = Inflights.remove(command.getHeader());
				if (inCommand != null) {
					inCommand.signal();
				}

			}
		}, hsmTimeout, TimeUnit.SECONDS);

	}

	public static void submit(HandleObRequest hsmRequest) {
		service.submit(hsmRequest);

	}
}
