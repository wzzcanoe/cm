package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.Column;
import com.ma.cm.entity.ColumnContent;
import com.ma.cm.exception.ForbiddenException;

@Service
public class ColumnService extends AService {

	@Transactional
	public List<Column> getByProduct(long productId) {
		checkProductExist(productId);
		List<Column> columns = columnMapper.getByProduct(productId);
		return columns;
	}

	@Transactional
	public Column save(Column column) {
		checkProductExist(column.getProductId());
		columnMapper.insert(column);
		Column result = columnMapper.getOne(column.getProductId(), column.getColumnId());
		return result;
	}

	@Transactional
	public Column get(long productId, long columnId) {
		checkColumnExist(productId, columnId);
		Column Column = columnMapper.getOne(productId, columnId);
		return Column;
	}

	@Transactional
	public Column update(Column column) {
		checkColumnExist(column.getProductId(), column.getColumnId());
		columnMapper.update(column);
		Column result = columnMapper.getOne(column.getProductId(), column.getColumnId());
		return result;
	}

	@Transactional
	public void delete(long productId, long columnId) {
		checkColumnExist(productId, columnId);
		List<ColumnContent> columnContents = columnContentMapper.getByProductByContentByType1(productId, columnId);
		if (!columnContents.isEmpty()) {
			String message = String.format("should not delete the column %d:%d because it is used by column %d:%d",
					productId, columnId, productId, columnContents.get(0).getColumnId());
			throw new ForbiddenException(message);
		}
		columnContentMapper.deleteByProductByColumn(productId, columnId);
		columnMapper.delete(productId, columnId);
	}
}
