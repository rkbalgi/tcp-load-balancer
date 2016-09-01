package com.github.rkbalgi.ldbl.ob;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.proxy.utils.ByteUtilities;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.conf.ObConfig;
import com.github.rkbalgi.ldbl.conf.Properties;

public class Outbounds {

	private static final Outbounds instance = new Outbounds();

	private Logger log = LoggerFactory.getLogger(getClass());
	private NioSocketConnector connector;
	private List<ObConfig> configList = Properties.getObConfigs();

	private Queue<IoSession> queue = new LinkedList<IoSession>();

	private Outbounds() {

		connector = new NioSocketConnector(8);
		connector.getFilterChain().addLast("logging-filter",
				new LoggingFilter());
		connector.setHandler(new ObIoHandler());

	}

	public synchronized void openSessions() {

		log.info("Opening Outbound connections");
		for (ObConfig config : configList) {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					config.getObIp(), config.getObPort()));
			try {
				future.await(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IoSession session = future.getSession();
			log.info("Opened connection to " + config.toString());
			queue.add(session);

		}

	}

	public void sendOutbound(byte[] commandData) {

		IoBuffer buf = IoBuffer.allocate(2 + commandData.length);
		buf.putShort((short) commandData.length);
		buf.put(commandData);
		IoSession session = null;

		synchronized (queue) {
			session = queue.remove();
			queue.add(session);
		}

		buf.rewind();
		if (session != null && session.isConnected()) {
			log.debug(String.format("writing to [%s] -",
					session.getRemoteAddress(),
					ByteUtilities.asHex(buf.array())));
			session.write(buf);

		} else {
			// session closed!
			openSessions();
			// try again
			sendOutbound(commandData);
		}

	}

	public static Outbounds getInstance() {
		return (instance);
	}

}
