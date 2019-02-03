package com.ma.cm.exception;

import com.ma.cm.entity.User;

public class UserNotExistException extends NotExistException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504241530273689750L;

	public UserNotExistException(long id) {
		super(User.class, id);
	}

}
