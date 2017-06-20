package com.zfxc.entity;

public class NewsInfo {

	private int iconID;
	private String title;
	private String detail;
	private String ddate;
	private String realid;
	private String missId;
	private String Status;
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMissId() {
		return missId;
	}
	public void setMissId(String missId) {
		this.missId = missId;
	}
	public String getRealid() {
		return realid;
	}
	public void setRealid(String realid) {
		this.realid = realid;
	}
	public int getIconID() {
		return iconID;
	}
	@Override
	public String toString() {
		return "NewsInfo [iconID=" + iconID + ", realid=" + realid + ", title=" + title + ", detail="
				+ detail +  ", missId=" + missId+"]";
	}
	public NewsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsInfo(int iconID, String title, String detail, String ddate,String realid,String missId) {
		super();
		this.iconID = iconID;
		this.title = title;
		this.detail = detail;
		this.ddate = ddate;
		this.realid=realid;
		this.missId=missId;
	}
	public NewsInfo(int iconID, String title, String detail, String ddate,String realid,String missId,String Status) {
		super();
		this.iconID = iconID;
		this.title = title;
		this.detail = detail;
		this.ddate = ddate;
		this.realid=realid;
		this.missId=missId;
		this.Status=Status;
	}
	public NewsInfo(int iconID, String title, String detail, String ddate,String realid) {
		super();
		this.iconID = iconID;
		this.title = title;
		this.detail = detail;
		this.ddate = ddate;
		this.realid=realid;
	}
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public void setIconID(int iconID) {
		this.iconID = iconID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
