package com.github.rkbalgi.ldbl.conf;

public class ObConfig {

	private String obIp;
	private int obPort;

	public ObConfig(String hsmIp, int hsmPort) {
		this.obIp = hsmIp;
		this.obPort = hsmPort;
	}

	public String getObIp() {
		return obIp;
	}

	public void setObIp(String obIp) {
		this.obIp = obIp;
	}

	public int getObPort() {
		return obPort;
	}

	public void setObPort(int obPort) {
		this.obPort = obPort;
	}

	public String toString() {
		return (obIp);
	}

}
