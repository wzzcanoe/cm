package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.GET, params = {"type=0"})
	public ColumnContent getContent(@PathVariable long productId, @PathVariable long columnId, @PathVariable long contentId, @RequestParam("type") int type) {
		return columnContentService.get(productId, columnId, type, contentId);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{childColumnId}", method = RequestMethod.GET, params = {"type=1"})
	public ColumnContent getColumn(@PathVariable long productId, @PathVariable long columnId, @PathVariable long childColumnId, @RequestParam("type") int type) {
		return columnContentService.get(productId, columnId, type, childColumnId);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.PUT)
	public ColumnContent update(@RequestBody ColumnContent columnContent) {
		return columnContentService.update(columnContent);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{contentId}", method = RequestMethod.DELETE, params = {"type=0"})
	public void deleteContent(@PathVariable long productId, @PathVariable long columnId, @PathVariable long contentId, @RequestParam("type") int type) {
		columnContentService.delete(productId, columnId, type, contentId);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}/contents/{childColumnId}", method = RequestMethod.DELETE, params = {"type=1"})
	public void deleteColumn(@PathVariable long productId, @PathVariable long columnId, @PathVariable long childColumnId, @RequestParam("type") int type) {
		columnContentService.delete(productId, columnId, type, childColumnId);
	}

}
