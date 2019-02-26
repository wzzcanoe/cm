package com.ma.cm.exception;

import com.ma.cm.entity.Column;

public class ColumnNotExistException extends NotExistException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7087413810730015483L;

	public ColumnNotExistException(long productId, long columnId) {
		super(Column.class, String.format("%d:%d", productId, columnId));
	}

}
