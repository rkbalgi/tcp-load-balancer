package com.github.rkbalgi.ldbl.ob;

import com.github.rkbalgi.ldbl.in.Inflights;
import com.github.rkbalgi.ldbl.util.Utils;

public class Ob {

	public static byte[] send(String header, byte[] data) {

		ObRequestObj command = new ObRequestObj(header, data);
		Inflights.add(command);
		
		//start time for timeouts
		Utils.startTimer(command);
		// write to hsm
		
		Outbounds.getInstance().sendOutbound(command.getData());
		command.waitForResponse();
		return (command.getResponseData());
	}

}
