package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.Column;

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
		columnContentMapper.deleteByProductByColumn(productId, columnId);
		columnMapper.delete(productId, columnId);
	}
}
