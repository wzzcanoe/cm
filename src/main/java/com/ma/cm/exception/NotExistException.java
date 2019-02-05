package com.ma.cm.exception;

public abstract class NotExistException extends RuntimeException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778921562404140643L;

	private String id;

	public NotExistException(Class<?> cls, String id) {
		super(String.format("%s %s is not exist!", cls.getSimpleName(), id));
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
