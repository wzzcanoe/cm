package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.Product;

@Service
public class ProductService extends AService {

	@Transactional
	public List<Product> gets() {
		List<Product> products = productMapper.getAll();
		return products;
	}

	@Transactional
	public Product save(Product product) {
		productMapper.insert(product);
		return product;
	}

	@Transactional
	public Product get(long id) {
		checkProductExist(id);
		Product product = productMapper.getOne(id);
		return product;
	}

	@Transactional
	public Product update(Product product) {
		checkProductExist(product.getId());
		productMapper.update(product);
		Product updatedProduct = productMapper.getOne(product.getId());
		return updatedProduct;
	}

	@Transactional
	public void delete(long id) {
		checkProductExist(id);
		columnContentMapper.deleteByProduct(id);
		contentMapper.deleteByProduct(id);
		columnMapper.deleteByProduct(id);
		productMapper.delete(id);
		
	}
}
