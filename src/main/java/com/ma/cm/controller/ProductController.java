package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.Product;
import com.ma.cm.service.ProductService;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Product> gets() {
		return productService.gets();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Product save(Product product) {
		return productService.save(product);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Product gets(@PathVariable long id) {
		return productService.get(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Product update(Product product) {
		return productService.update(product);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) {
		productService.delete(id);
	}
}
