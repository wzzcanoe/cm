package com.ma.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.Column;
import com.ma.cm.exception.ColumnNotExistException;
import com.ma.cm.mapper.ColumnMapper;

@Service
public class ColumnService {

	@Autowired
	private ColumnMapper columnMapper;

	@Transactional
	public List<Column> getByProduct(long productId) {
		List<Column> columns = columnMapper.getByProduct(productId);
		return columns;
	}

	@Transactional
	public Column save(Column column) {
		columnMapper.insert(column);
		return column;
	}

	@Transactional
	public Column get(long productId, long columnId) {
		Column Column = columnMapper.getOne(productId, columnId);
		if (null == Column) {
			throw new ColumnNotExistException(productId, columnId);
		}
		return Column;
	}

	@Transactional
	public Column update(Column column) {
		columnMapper.update(column);
		Column updatedColumn = columnMapper.getOne(column.getProductId(), column.getColumnId());
		if (null == updatedColumn) {
			throw new ColumnNotExistException(column.getProductId(), column.getColumnId());
		}
		return updatedColumn;
	}

	@Transactional
	public void delete(long productId, long columnId) {
		Column Column = columnMapper.getOne(productId, columnId);
		if (null == Column) {
			throw new ColumnNotExistException(productId, columnId);
		}
		columnMapper.delete(productId, columnId);
	}
}
