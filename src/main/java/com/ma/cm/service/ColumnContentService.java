package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.ColumnContent;
import com.ma.cm.exception.ForbiddenException;

@Service
public class ColumnContentService extends AService{
	
	@Transactional
	public List<ColumnContent> getByProductByColumn(long productId, long columnId) {
		checkColumnExist(productId, columnId);
		return columnContentMapper.getByProductByColumn(productId, columnId);
	}
	
	@Transactional
	public List<ColumnContent> getDetailsByProductByColumn(long productId, long columnId) {
		checkColumnExist(productId, columnId);
		List<ColumnContent> contents = columnContentMapper.getDetailContentsByProductByColumn(productId, columnId);
		for (ColumnContent columnContent : contents) {
			columnContent.setChildColumn(null);
		}
		List<ColumnContent> columns = columnContentMapper.getDetailColumnsByProductByColumn(productId, columnId);
		for (ColumnContent columnContent : columns) {
			columnContent.setContent(null);
		}
		contents.addAll(columns);
		contents.sort(new ColumnContent.ColumnContentPositionComparator());
		return contents;
	}

	@Transactional
	public ColumnContent save(ColumnContent columnContent) {
		checkColumnExist(columnContent.getProductId(), columnContent.getColumnId());
		if (columnContent.getType() == 0) {
			checkContentExist(columnContent.getProductId(), columnContent.getContentId());
		} else if (columnContent.getType() == 1){
			checkColumnExist(columnContent.getProductId(), columnContent.getContentId());
		} else {
			throw new ForbiddenException(String.format("type %d is invalid for ColumnContent", columnContent.getType()));
		}
		columnContentMapper.insert(columnContent);
		ColumnContent result = columnContentMapper.getOne(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getType(), columnContent.getContentId());
		return result;
	}

	@Transactional
	public ColumnContent get(long productId, long columnId, int type, long contentId) {
		checkColumnContentExist(productId, columnId, type, contentId);
		ColumnContent result = columnContentMapper.getOne(productId, columnId, type, contentId);
		return result;
	}

	@Transactional
	public ColumnContent update(ColumnContent columnContent) {
		checkColumnContentExist(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getType(), columnContent.getContentId());
		columnContentMapper.update(columnContent);
		ColumnContent result = columnContentMapper.getOne(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getType(), columnContent.getContentId());
		return result;
	}

	@Transactional
	public void delete(long productId, long columnId, int type, long contentId) {
		checkColumnContentExist(productId, columnId, type, contentId);
		columnContentMapper.delete(productId, columnId, type, contentId);
	}
}
