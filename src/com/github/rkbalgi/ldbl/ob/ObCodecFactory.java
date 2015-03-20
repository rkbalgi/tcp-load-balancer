package com.github.rkbalgi.ldbl.ob;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ObCodecFactory implements ProtocolCodecFactory {

	private ProtocolDecoder decoder = new ObDataDecoder();
	private ProtocolEncoder encoder = new ObDataEncoder();

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return (decoder);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return (encoder);
	}

}
