package com.ma.cm.exception;

import com.ma.cm.entity.ColumnContent;

public class ColumnContentNotExistException extends NotExistException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8018332672446484600L;

	public ColumnContentNotExistException(long productId, long columnId, int type, long contentId) {
		super(ColumnContent.class, String.format("%d:%d:%d:%d", productId, columnId, type, contentId));
	}

}
