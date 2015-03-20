package com.github.rkbalgi.ldbl.in;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.conf.Properties;
import com.github.rkbalgi.ldbl.ob.ObCodecFactory;

public class InConnectionAcceptor {

	private Logger log = LoggerFactory.getLogger(getClass());
	private NioSocketAcceptor acceptor;

	public InConnectionAcceptor() {

		acceptor = new NioSocketAcceptor(8);
		acceptor.getFilterChain()
				.addLast("logging-filter", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec-filter",
				new ProtocolCodecFilter(new ObCodecFactory()));

		acceptor.setHandler(new IncomingIoHandler());

	}

	public void start() {
		int port = Integer.parseInt(Properties.getLdblPort());
		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			log.error("failed to open acceptor", e);
		}

	}

}
