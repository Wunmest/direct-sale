package com.wunmest.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XResponse {

	private int returnCode;
	private String returnMessage;
	private Object data;
	
	private static Log log = LogFactory.getLog(XResponse.class);
	
	public XResponse(int returnCode, String returnMessage) {
		super();
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;

		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> xResponse created.");
	}
	
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		
		return "{returnCode: " + returnCode + ", returnMessage: " + returnMessage + ", data: " + data + "}";
	}
}
