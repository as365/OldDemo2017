/**
 * 
 */
package com.zfxc.entity;

import java.io.Serializable;

/**
 * @author dell
 *
 */
public class MissTongJi  implements Serializable {

	private String misName;
	private String misDate;
	private String misType;
	private String misStatu;
	private String misId;
	public String getMisId() {
		return misId;
	}
	public void setMisId(String misId) {
		this.misId = misId;
	}
	public String getMisName() {
		return misName;
	}
	public void setMisName(String misName) {
		this.misName = misName;
	}
	public String getMisDate() {
		return misDate;
	}
	public void setMisDate(String misDate) {
		this.misDate = misDate;
	}
	public String getMisType() {
		return misType;
	}
	public void setMisType(String misType) {
		this.misType = misType;
	}
	public String getMisStatu() {
		return misStatu;
	}
	public void setMisStatu(String misStatu) {
		this.misStatu = misStatu;
	}
	
}
