package com.github.rkbalgi.ldbl.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Properties {

	private static final Pattern P = Pattern.compile("ldbl\\.([\\d]+)\\.ip");

	private static final java.util.Properties p = new java.util.Properties();

	static {
		try {
			p.load(ClassLoader.getSystemResourceAsStream("tcpldbl.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getLdblPort() {
		return (p.getProperty("ldbl.vip.port"));
	}

	public static List<ObConfig> getObConfigs() {

		Enumeration e = p.propertyNames();
		List<ObConfig> configList = new ArrayList<ObConfig>();

		while (e.hasMoreElements()) {
			String tmp = (String) e.nextElement();
			Matcher m = P.matcher(tmp);
			if (m.matches()) {
				int hsmIndex = Integer.parseInt(m.group(1));
				String hsmIp = p.getProperty(tmp);
				int hsmPort = Integer.parseInt(p.getProperty("ldbl." + hsmIndex
						+ ".port"));
				configList.add(new ObConfig(hsmIp, hsmPort));
			}

		}

		if (configList.size() == 0) {
			throw new RuntimeException("no hsm's defined or improper config.");
		}

		return (configList);
	}

	public static int getObTimeout() {
		String tmp = p.getProperty("ldbl.timeout");
		int timeout = 5;
		if (tmp != null) {
			try {
				timeout = Integer.parseInt(tmp);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return (timeout);
	}

}
