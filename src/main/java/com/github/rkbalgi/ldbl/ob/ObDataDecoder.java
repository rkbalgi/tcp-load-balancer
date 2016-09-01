package com.github.rkbalgi.ldbl.ob;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ObDataDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer buf,
			ProtocolDecoderOutput arg2) throws Exception {

		if (buf.prefixedDataAvailable(2)) {
			short size = buf.getShort();
			byte[] tmp = new byte[size];
			buf.get(tmp);
			arg2.write(tmp);
			// Utils.submit(new HandleHsmRequest(arg0, tmp));
			return (true);
		} 
		return false;
	}

}
