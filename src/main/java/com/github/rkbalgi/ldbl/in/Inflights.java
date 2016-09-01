package com.github.rkbalgi.ldbl.in;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.rkbalgi.ldbl.ob.ObRequestObj;

public class Inflights {

	private static final Map<String, ObRequestObj> inFlightsMap = new ConcurrentHashMap<String, ObRequestObj>();

	public static void add(ObRequestObj command) {
		inFlightsMap.put(command.getHeader(), command);
	}

	public static ObRequestObj remove(String header) {
		ObRequestObj command = inFlightsMap.remove(header);
		return (command);
	}

}
