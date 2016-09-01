package com.github.rkbalgi.ldbl.ob;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.proxy.utils.ByteUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rkbalgi.ldbl.util.Utils;

public class HandleObRequest implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	private byte[] data;
	private IoSession session;

	public HandleObRequest(IoSession arg0, byte[] tmp) {
		this.session = arg0;
		this.data = tmp;
	}

	@Override
	public void run() {

		byte[] newBuf = Arrays.copyOf(data, data.length);
		byte[] currentId = Arrays.copyOfRange(data, 0, 12);
		String header = String.format("%012d", Utils.nextId());
		byte[] nextId = header.getBytes();
		System.arraycopy(nextId, 0, newBuf, 0, 12);

		log.debug(String.format("replacing header [%s] with header [%s].",
				new String(currentId), header));

		byte[] responseData = Ob.send(header, newBuf);
		if (responseData != null) {
			System.arraycopy(currentId, 0, responseData, 0, 12);
		}

		// replace new id with old id

		byte[] responseWithMli = new byte[2 + responseData.length];
		ByteBuffer buf = ByteBuffer.wrap(responseWithMli);
		buf.putShort((short) responseData.length);
		buf.put(responseData);
		buf.rewind();
		log.debug(String.format("sending out response for [%s] [%s]",
				new String(currentId), ByteUtilities.asHex(responseData)));

		session.write(IoBuffer.wrap(buf));

	}

}
