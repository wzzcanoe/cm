package com.ma.cm.exception;

import com.ma.cm.entity.Content;

public class ContentNotExistException extends NotExistException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1995431217362211681L;

	public ContentNotExistException(long productId, long contentId) {
		super(Content.class, String.format("%d:%d", productId, contentId));
	}

}
