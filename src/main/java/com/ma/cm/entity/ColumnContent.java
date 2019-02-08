package com.ma.cm.entity;

import java.io.Serializable;

public class ColumnContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8141415217474675717L;

	private long productId;
	
	private long columnId;
	
	private long contentId;
	
	private long position;
	
	private Content content;
	
	public ColumnContent() {
		
	}
	
	public ColumnContent(long productId, long columnId, long contentId, long position) {
		this.productId = productId;
		this.columnId = columnId;
		this.contentId = contentId;
		this.position = position;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getColumnId() {
		return columnId;
	}

	public void setColumnId(long columnId) {
		this.columnId = columnId;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
}
