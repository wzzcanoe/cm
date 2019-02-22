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

import com.ma.cm.entity.Content;
import com.ma.cm.exception.FTPException;
import com.ma.cm.service.ContentService;
import com.ma.cm.util.FTPClientHelper;

@RestController
@RequestMapping("/v1/products")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@Autowired
	private FTPClientHelper ftpClient;

	@RequestMapping(value = "/{productId}/contents", method = RequestMethod.GET)
	public List<Content> getByProduct(@PathVariable long productId) {
		return contentService.getByProduct(productId);
	}

//	@RequestMapping(value = "/{productId}/contents", method = RequestMethod.POST)
//	public Content save(@RequestBody Content content) {
//		return contentService.save(content);
//	}

	@RequestMapping(value = "/{productId}/contents", method = RequestMethod.POST)
	public Content save(Content content, @RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
			@RequestParam(value = "iconFile", required = false) MultipartFile iconFile,
			@RequestParam(value = "screenShotFile", required = false) MultipartFile screenShotFile) {
		String filepath = String.format("/product-%d/", content.getProductId());
		if (posterFile != null) {
			String originName = posterFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, posterFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setPoster(filepath + filename);
		} else {
			content.setPoster(null);
		}
		if (iconFile != null) {
			String originName = iconFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, iconFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setIcon(filepath + filename);
		} else {
			content.setIcon(null);
		}
		if (screenShotFile != null) {
			String originName = screenShotFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, screenShotFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setScreenShot(filepath + filename);
		} else {
			content.setScreenShot(null);
		}
		return contentService.save(content);
	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.GET)
	public Content get(@PathVariable long productId, @PathVariable long contentId) {
		return contentService.get(productId, contentId);
	}
//
//	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.PUT)
//	public Content update(@RequestBody Content content) {
//		return contentService.update(content);
//	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.PUT)
	public Content update(Content content,
			@RequestParam(value = "posterFile", required = false) MultipartFile posterFile,
			@RequestParam(value = "iconFile", required = false) MultipartFile iconFile,
			@RequestParam(value = "screenShotFile", required = false) MultipartFile screenShotFile) {
		String filepath = String.format("/product-%d/", content.getProductId());
		if (posterFile != null) {
			String originName = posterFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, posterFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setPoster(filepath + filename);
		} else {
			content.setPoster(null);
		}
		if (iconFile != null) {
			String originName = iconFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, iconFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setIcon(filepath + filename);
		} else {
			content.setIcon(null);
		}
		if (screenShotFile != null) {
			String originName = screenShotFile.getOriginalFilename();
			int last_index_of_dot = originName.lastIndexOf('.');
			String suffix = originName.substring(last_index_of_dot + 1);
			String filename = String.format("%s.%s", UUID.randomUUID().toString(), suffix);
			try {
				ftpClient.uploadFile(filepath, filename, screenShotFile.getInputStream());
			} catch (IOException e) {
				throw new FTPException(e.getMessage());
			}
			content.setScreenShot(filepath + filename);
		} else {
			content.setScreenShot(null);
		}
		return contentService.update(content);
	}

	@RequestMapping(value = "/{productId}/contents/{contentId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long productId, @PathVariable long contentId) {
		contentService.delete(productId, contentId);
	}
}
