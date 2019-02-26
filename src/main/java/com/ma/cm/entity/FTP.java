package com.ma.cm.entity;

import java.io.Serializable;

public class FTP implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -117641512264337436L;
	
	public static long FTP_ID = 1;
	
	private long id;
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	public FTP() {
		
	}
	
	public FTP(long id, String host, int port, String username, String password) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
