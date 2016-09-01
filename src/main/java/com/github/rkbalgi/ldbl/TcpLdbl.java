package com.github.rkbalgi.ldbl;

import com.github.rkbalgi.ldbl.in.InConnectionAcceptor;
import com.github.rkbalgi.ldbl.ob.Outbounds;

public class TcpLdbl {

	public static void main(String[] args) {

		Outbounds obConnector = Outbounds.getInstance();
		obConnector.openSessions();
		new InConnectionAcceptor().start();

	}

}
