package com.ma.cm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ma.cm.entity.Column;
import com.ma.cm.entity.ColumnContent;
import com.ma.cm.entity.Content;
import com.ma.cm.entity.Product;
import com.ma.cm.exception.ColumnContentNotExistException;
import com.ma.cm.exception.ColumnNotExistException;
import com.ma.cm.exception.ContentNotExistException;
import com.ma.cm.exception.ProductNotExistException;
import com.ma.cm.mapper.ColumnContentMapper;
import com.ma.cm.mapper.ColumnMapper;
import com.ma.cm.mapper.ContentMapper;
import com.ma.cm.mapper.ProductMapper;

public abstract class AService {

	@Autowired
	protected ProductMapper productMapper;
	
	@Autowired
	protected ColumnMapper columnMapper;

	@Autowired
	protected ContentMapper contentMapper;

	@Autowired
	protected ColumnContentMapper columnContentMapper;
	
	protected void checkProductExist(long productId) {
		Product product = productMapper.getOne(productId);
		if (null == product) {
			throw new ProductNotExistException(productId);
		}
	}
	
	protected void checkColumnExist(long productId, long columnId) {
		checkProductExist(productId);
		Column column = columnMapper.getOne(productId, columnId);
		if (null == column) {
			throw new ColumnNotExistException(productId, columnId);
		}
	}
	
	protected void checkContentExist(long productId, long contentId) {
		checkProductExist(productId);
		Content content = contentMapper.getOne(productId, contentId);
		if (null == content) {
			throw new ContentNotExistException(productId, contentId);
		}
	}

	protected void checkColumnContentExist(long productId, long columnId, long contentId) {
		checkColumnExist(productId, columnId);
		checkContentExist(productId, contentId);
		ColumnContent columnContent = columnContentMapper.getOne(productId, columnId, contentId);
		if (null == columnContent) {
			throw new ColumnContentNotExistException(productId, columnId, contentId);
		}
	}
}
