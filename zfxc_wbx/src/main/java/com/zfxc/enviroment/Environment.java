package com.zfxc.enviroment;



public class Environment {
	private String ddate;
	private String ip;
	private String kqwd;
	private String kqsd;
	private String gz;
	private String trwd;
	private String trsd;

	public Environment() {
		super();
	}

	public Environment(String ddate, String ip, String kqwd, String kqsd,
			String gz, String trwd, String trsd) {
		super();
		this.ddate = ddate;
		this.ip = ip;
		this.kqwd = kqwd;
		this.kqsd = kqsd;
		this.gz = gz;
		this.trwd = trwd;
		this.trsd = trsd;
	}

	public String getddate() {
		return ddate;
	}

	public void setddate(String ddate) {
		this.ddate = ddate;
	}

	public String getip() {
		return ip;
	}

	public void setip(String ip) {
		this.ip = ip;
	}

	public String getkqwd() {
		return kqwd;
	}

	public void setkqwd(String kqwd) {
		this.kqwd = kqwd;
	}

	public String getkqsd() {
		return kqsd;
	}

	public void setkqsd(String kqsd) {
		this.kqsd = kqsd;
	}

	public String getgz() {
		return gz;
	}

	public void setgz(String gz) {
		this.gz = gz;
	}

	public String gettrwd() {
		return trwd;
	}

	public void settrwd(String trwd) {
		this.trwd = trwd;
	}

	public String gettrsd() {
		return trsd;
	}

	public void settrsd(String trsd) {
		this.trsd = trsd;
	}

}
