package com.wunmest.domain;

public class XException extends Exception {
	private int code;
	private String msg;
	
	public XException(int code, String msg, Exception e) {
		super(e);
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String Msg) {
		this.msg = msg;
	}
	
	
}
