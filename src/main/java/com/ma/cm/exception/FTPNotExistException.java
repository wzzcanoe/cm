package com.ma.cm.exception;

import com.ma.cm.entity.FTP;

public class FTPNotExistException extends NotExistException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895793852324919852L;

	public FTPNotExistException(long id) {
		super(FTP.class, String.valueOf(id));
	}

}
