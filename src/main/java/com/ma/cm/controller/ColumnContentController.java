package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.ColumnContent;
import com.ma.cm.service.ColumnContentService;

@RestController
@RequestMapping("/v1/products")
public class ColumnContentController {

	@Autowired
	private ColumnContentService columnContentService;

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents", method = RequestMethod.GET)
	public List<ColumnContent> getByProductByColumn(@PathVariable long productId, @PathVariable long columnId) {
		List<ColumnContent> results = columnContentService.getByProductByColumn(productId, columnId);
		return results;
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents", method = RequestMethod.GET, params = {"detail"})
	public List<ColumnContent> getDetailsByProductByColumn(@PathVariable long productId, @PathVariable long columnId) {
		List<ColumnContent> results = columnContentService.getDetailsByProductByColumn(productId, columnId);
		return results;
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents", method = RequestMethod.POST)
	public ColumnContent save(@RequestBody ColumnContent columnContent) {
		return columnContentService.save(columnContent);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.GET)
	public ColumnContent get(@PathVariable long productId, @PathVariable long columnId, @PathVariable long contentId) {
		return columnContentService.get(productId, columnId, contentId);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.PUT)
	public ColumnContent update(@RequestBody ColumnContent columnContent) {
		return columnContentService.update(columnContent);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long productId, @PathVariable long columnId, @PathVariable long contentId) {
		columnContentService.delete(productId, columnId, contentId);
	}

}
