package com.ma.cm.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ma.cm.entity.Column;
import com.ma.cm.exception.FTPException;
import com.ma.cm.service.ColumnService;
import com.ma.cm.util.FTPClientHelper;

@RestController
@RequestMapping("/v1/products")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	@Autowired
	private FTPClientHelper ftpClient;

	@RequestMapping(value = "/{productId}/columns", method = RequestMethod.GET)
	public List<Column> getByProduct(@PathVariable long productId) {
		return columnService.getByProduct(productId);
	}
//
//	@RequestMapping(value = "/{productId}/columns", method = RequestMethod.POST)
//	public Column save(@RequestBody Column column) {
//		return columnService.save(column);
//	}

	@RequestMapping(value = "/{productId}/columns", method = RequestMethod.POST)
	public Column save(Column column, @RequestParam(value = "posterFile", required = false) MultipartFile file) {
		if (file != null) {
			String originName = file.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filepath = String.format("/product-%d/", column.getProductId());
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, file.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			column.setPoster(filepath + filename);
		} else {
			column.setPoster(null);
		}
		return columnService.save(column);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.GET)
	public Column get(@PathVariable long productId, @PathVariable long columnId) {
		return columnService.get(productId, columnId);
	}

//	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.PUT)
//	public Column update(@RequestBody Column column) {
//		return columnService.update(column);
//	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.PUT)
	public Column update(Column column, @RequestParam(value = "posterFile", required = false) MultipartFile file) {
		if (file != null) {
			String originName = file.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filepath = String.format("/product-%d/", column.getProductId());
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, file.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			column.setPoster(filepath + filename);
		} else {
			column.setPoster(null);
		}
		return columnService.update(column);
	}

	@RequestMapping(value = "/{productId}/columns/{columnId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long productId, @PathVariable long columnId) {
		columnService.delete(productId, columnId);
	}
}
