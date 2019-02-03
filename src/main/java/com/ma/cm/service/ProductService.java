package com.ma.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.Product;
import com.ma.cm.exception.ProductNotExistException;
import com.ma.cm.exception.UserNotExistException;
import com.ma.cm.mapper.ProductMapper;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

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
		Product product = productMapper.getOne(id);
		if (null == product) {
			throw new ProductNotExistException(id);
		}
		return product;
	}

	@Transactional
	public Product update(Product product) {
		productMapper.update(product);
		Product updatedProduct = productMapper.getOne(product.getId());
		if (null == updatedProduct) {
			throw new ProductNotExistException(product.getId());
		}
		return updatedProduct;
	}

	@Transactional
	public void delete(long id) {
		productMapper.delete(id);
	}
}
