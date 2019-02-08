package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.Content;
import com.ma.cm.service.ContentService;

@RestController
@RequestMapping("/v1/products")
public class ContentController {


	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value = "/{productId}/contents", method = RequestMethod.GET)
	public List<Content> getByProduct(@PathVariable long productId) {
		return contentService.getByProduct(productId);
	}

	@RequestMapping(value = "/{productId}/contents", method = RequestMethod.POST)
	public Content save(@RequestBody Content content) {
		return contentService.save(content);
	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.GET)
	public Content get(@PathVariable long productId, @PathVariable long contentId) {
		return contentService.get(productId, contentId);
	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.PUT)
	public Content update(@RequestBody Content content) {
		return contentService.update(content);
	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long productId, @PathVariable long contentId) {
		contentService.delete(productId, contentId);
	}
}
