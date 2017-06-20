/**
 * 
 */
package com.zfxc.entity;

/**
 * @author dell
 *
 */
public class MissDetailed {

	private String missStatus;
	private String missContent;
	private String missContentfile;
	private String missDate;
	private String missUser;
	private String missType;
	
	
	public MissDetailed() {
		super();
	}
	public MissDetailed(String missStatus, String missContent,
			String missContentfile, String missDate, String missUser,
			String missType) {
		super();
		this.missStatus = missStatus;
		this.missContent = missContent;
		this.missContentfile = missContentfile;
		this.missDate = missDate;
		this.missUser = missUser;
		this.missType = missType;
	}
	public String getMissStatus() {
		return missStatus;
	}
	public void setMissStatus(String missStatus) {
		this.missStatus = missStatus;
	}
	public String getMissContent() {
		return missContent;
	}
	public void setMissContent(String missContent) {
		this.missContent = missContent;
	}
	public String getMissContentfile() {
		return missContentfile;
	}
	public void setMissContentfile(String missContentfile) {
		this.missContentfile = missContentfile;
	}
	public String getMissDate() {
		return missDate;
	}
	public void setMissDate(String missDate) {
		this.missDate = missDate;
	}
	public String getMissUser() {
		return missUser;
	}
	public void setMissUser(String missUser) {
		this.missUser = missUser;
	}
	public String getMissType() {
		return missType;
	}
	public void setMissType(String missType) {
		this.missType = missType;
	}
	
}
