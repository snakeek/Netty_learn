package com.mylearn.netty.guide.chapter6.first;

import java.io.Serializable;

public class SubscribeResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int subReqID;
	private int resqCode;
	private String resqDesc;

	public String getResqDesc() {
		return resqDesc;
	}
	public void setResqDesc(String resqDesc) {
		this.resqDesc = resqDesc;
	}
	public int getSubReqID() {
		return subReqID;
	}
	public void setSubReqID(int subReqID) {
		this.subReqID = subReqID;
	}
	public int getResqCode() {
		return resqCode;
	}
	public void setResqCode(int resqCode) {
		this.resqCode = resqCode;
	}
	@Override
	public String toString() {
		return "SubscribeResp [subReqID=" + subReqID + ", resqCode=" + resqCode
				+ ", resqDesc=" + resqDesc + "]";
	}
}
