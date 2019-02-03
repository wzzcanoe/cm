package com.ma.cm.exception;

public abstract class NotExistException extends RuntimeException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778921562404140643L;

	private long id;

	public NotExistException(Class<?> cls, long id) {
		super(String.format("%s %d is not exist!", cls.getSimpleName(), id));
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
