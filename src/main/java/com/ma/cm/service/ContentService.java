package com.ma.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.ColumnContent;
import com.ma.cm.entity.Content;
import com.ma.cm.exception.ForbiddenException;

@Service
public class ContentService extends AService {
	
	@Transactional
	public List<Content> getByProduct(long productId) {
		checkProductExist(productId);
		List<Content> contents = contentMapper.getByProduct(productId);
		return contents;
	}

	@Transactional
	public Content save(Content content) {
		checkProductExist(content.getProductId());
		contentMapper.insert(content);
		Content result = contentMapper.getOne(content.getProductId(), content.getContentId());
		return result;
	}

	@Transactional
	public Content get(long productId, long contentId) {
		checkContentExist(productId, contentId);
		Content content = contentMapper.getOne(productId, contentId);
		return content;
	}

	@Transactional
	public Content update(Content content) {
		checkContentExist(content.getProductId(), content.getContentId());
		contentMapper.update(content);
		Content result = contentMapper.getOne(content.getProductId(), content.getContentId());
		return result;
	}

	@Transactional
	public void delete(long productId, long contentId) {
		checkContentExist(productId, contentId);
		// if content is be related by column, it should forbidden to delete
		List<ColumnContent> columnContents = columnContentMapper.getByProductByContent(productId, contentId);
		if (!columnContents.isEmpty()) {
			String message = String.format("should not delete the content %d:%d because it is used by column %d:%d", productId, contentId, productId, columnContents.get(0).getColumnId());
			throw new ForbiddenException(message);
		}
		contentMapper.delete(productId, contentId);
	}
}
