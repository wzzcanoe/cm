package com.ma.cm.exception;

import com.ma.cm.entity.Product;

public class ProductNotExistException extends NotExistException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1993341944440921672L;

	public ProductNotExistException(long id) {
		super(Product.class, String.valueOf(id));
	}
}
