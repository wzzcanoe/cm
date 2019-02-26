package com.ma.cm.entity;

import java.io.Serializable;

public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4922572564479222339L;

	private long id;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
