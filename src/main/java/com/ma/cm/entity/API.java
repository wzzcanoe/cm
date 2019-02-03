package com.ma.cm.entity;

import java.io.Serializable;

public class API implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2589586023707668783L;
	
	private String pattern;
	
	private String method;
	
	public API(String pattern, String method) {
		this.pattern = pattern;
		this.method = method;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
