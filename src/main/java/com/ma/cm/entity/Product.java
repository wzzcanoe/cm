package com.ma.cm.entity;

import java.io.Serializable;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6876888616887744373L;

	private long id;

	private String name;
	
	private String options;

	public Product() {

	}

	public Product(long id, String name, String options) {
		this.id = id;
		this.name = name;
		this.options = options;
	}

	public Product(String name, String options) {
		this.name = name;
		this.options = options;
	}

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

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

}
