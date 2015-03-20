package com.github.rkbalgi.ldbl.ob;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.util.Utils;

public class ObIoHandler implements IoHandler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		log.error("error in session!", arg1);

	}

	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {

		log.debug("received hsm response ...");
		IoBuffer buf = (IoBuffer) arg1;
		buf.mark();
		if (buf.remaining() > 2) {
			short size = buf.getShort();
			if (buf.remaining() >= size) {

				// we have a proper request
				byte[] tmp = new byte[size];
				buf.get(tmp);
				Utils.handleHsmResponse(tmp);

			} else {
				buf.reset();
			}

		} else {
			buf.reset();
		}

	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		log.info("session closed - " + arg0.getRemoteAddress());

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
		// TODO Auto-generated method stub

	}

}
