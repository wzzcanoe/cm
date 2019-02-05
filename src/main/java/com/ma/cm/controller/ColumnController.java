package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.Column;
import com.ma.cm.service.ColumnService;

@RestController
@RequestMapping("/v1/products")
public class ColumnController {


	@Autowired
	private ColumnService columnService;
	
	@RequestMapping(value = "/{productId}/columns", method = RequestMethod.GET)
	public List<Column> getByProduct(@PathVariable long productId) {
		return columnService.getByProduct(productId);
	}

	@RequestMapping(value = "/{productId}/columns", method = RequestMethod.POST)
	public Column save(@RequestBody Column column) {
		return columnService.save(column);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.GET)
	public Column get(@PathVariable long productId, @PathVariable long columnId) {
		return columnService.get(productId, columnId);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.PUT)
	public Column update(@RequestBody Column column) {
		return columnService.update(column);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long productId, @PathVariable long columnId) {
		columnService.delete(productId, columnId);
	}
}
