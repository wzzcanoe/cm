package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.ColumnContent;

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
		return columnContentMapper.getDetailsByProductByColumn(productId, columnId);
	}

	@Transactional
	public ColumnContent save(ColumnContent columnContent) {
		// TODO havnt handle "treat the column as a content" case
		checkColumnExist(columnContent.getProductId(), columnContent.getColumnId());
		checkContentExist(columnContent.getProductId(), columnContent.getContentId());
		columnContentMapper.insert(columnContent);
		ColumnContent result = columnContentMapper.getOne(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getContentId());
		return result;
	}

	@Transactional
	public ColumnContent get(long productId, long columnId, long contentId) {
		// TODO havnt handle "treat the column as a content" case
		checkColumnContentExist(productId, columnId, contentId);
		ColumnContent result = columnContentMapper.getOne(productId, columnId, contentId);
		return result;
	}

	@Transactional
	public ColumnContent update(ColumnContent columnContent) {
		// TODO havnt handle "treat the column as a content" case
		checkColumnContentExist(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getContentId());
		columnContentMapper.update(columnContent);
		ColumnContent result = columnContentMapper.getOne(columnContent.getProductId(), columnContent.getColumnId(), columnContent.getContentId());
		return result;
	}

	@Transactional
	public void delete(long productId, long columnId, long contentId) {
		// TODO havnt handle "treat the column as a content" case
		checkColumnContentExist(productId, columnId, contentId);
		columnContentMapper.delete(productId, columnId, contentId);
	}
}
