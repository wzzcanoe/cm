package com.ma.cm.entity;

import java.io.Serializable;
import java.util.Comparator;

public class ColumnContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8141415217474675717L;
	
	public static int TYPE_CONTENT = 0;
	
	public static int TYPE_CHILD_COLUMN = 1;
	
	private long productId;
	
	private long columnId;
	
	private int type;
	
	private long contentId;
	
	private long position;
	
	private Content content;
	
	private Column childColumn;
	
	public ColumnContent() {
		
	}
	
	public ColumnContent(long productId, long columnId, int type, long contentId, long position) {
		this.productId = productId;
		this.columnId = columnId;
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Column getChildColumn() {
		return childColumn;
	}

	public void setChildColumn(Column childColumn) {
		this.childColumn = childColumn;
	}

	public static class ColumnContentPositionComparator implements Comparator<ColumnContent> {

		@Override
		public int compare(ColumnContent o1, ColumnContent o2) {
			return (int) (o1.getPosition() - o2.getPosition());
		}
		
	}
	
}
