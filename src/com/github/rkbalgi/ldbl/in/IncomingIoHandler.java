package com.github.rkbalgi.ldbl.in;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.ob.HandleObRequest;
import com.github.rkbalgi.ldbl.util.Utils;

public class IncomingIoHandler implements IoHandler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		log.error("exception in session", arg1);

	}

	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {

		byte[] data=(byte[]) arg1;
		Utils.submit(new HandleObRequest(arg0, data));
	
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		log.info("new connection from -" + arg0.getRemoteAddress());
		/*IoBuffer buf = IoBuffer.allocate(4096);
		buf.minimumCapacity(1024);
		buf.setAutoExpand(true);
		buf.setAutoShrink(true);
		arg0.setAttribute("__buf__", buf);*/

	}
}
