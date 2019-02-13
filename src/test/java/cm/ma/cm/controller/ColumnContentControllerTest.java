package cm.ma.cm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ma.cm.Application;
import com.ma.cm.entity.Column;
import com.ma.cm.entity.ColumnContent;
import com.ma.cm.entity.Content;
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColumnContentControllerTest extends AControllerTest{

	@Test
	public void testFunctionType0() throws URISyntaxException {
		int positionChanged = 99;
		String columnContentUrlWithId = String.format("http://localhost:%d%s%s", port, contextPath, columnContentUriWithIdType0);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// post a column
			restTemplate.postForObject(columnUri, column, Column.class);
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(0, columnContents.length);
		}
		{
			// post
			ColumnContent result = restTemplate.postForObject(columnContentUri, columnContent1, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(columnContentPosition1, result.getPosition());
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(1, columnContents.length);
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithIdType0, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(columnContentPosition1, result.getPosition());		
		}
		{
			// put
			columnContent1.setPosition(positionChanged);
			RequestEntity<ColumnContent> request = RequestEntity.put(new URI(columnContentUrlWithId))
					.accept(MediaType.APPLICATION_JSON).body(columnContent1);
			ResponseEntity<ColumnContent> putResult = restTemplate.exchange(request, ColumnContent.class);
			ColumnContent result = putResult.getBody();
			assertEquals(HttpStatus.OK, putResult.getStatusCode());
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(positionChanged, result.getPosition());
			columnContent1.setPosition(columnContentPosition1);
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithIdType0, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(positionChanged, result.getPosition());			
		}
		{
			// delete
			restTemplate.delete(columnContentUrlWithId);
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(0, columnContents.length);
		}
		{
			// delete product
			restTemplate.delete(productUriWithId);
		}
		{
			// check relation between column and content for the product, it should be 404
			RequestEntity<Void> request = RequestEntity.get(new URI(columnContentUrlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
		}
	}

	@Test
	public void testFunctionType1() throws URISyntaxException {
		String columnContentUrlWithId = String.format("http://localhost:%d%s%s", port, contextPath, columnContentUriWithIdType1);
		long positionChanged = 99;
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a child column
			restTemplate.postForObject(columnUri, childColumn, Column.class);
		}
		{
			// post a column
			restTemplate.postForObject(columnUri, column, Column.class);
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(0, columnContents.length);
		}
		{
			// post
			ColumnContent result = restTemplate.postForObject(columnContentUri, columnContent2, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType2, result.getType());
			assertEquals(childColumnId, result.getContentId());
			assertEquals(columnContentPosition2, result.getPosition());
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(1, columnContents.length);
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithIdType1, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType2, result.getType());
			assertEquals(childColumnId, result.getContentId());
			assertEquals(columnContentPosition2, result.getPosition());		
		}
		{
			// put
			columnContent2.setPosition(positionChanged);
			RequestEntity<ColumnContent> request = RequestEntity.put(new URI(columnContentUrlWithId))
					.accept(MediaType.APPLICATION_JSON).body(columnContent2);
			ResponseEntity<ColumnContent> putResult = restTemplate.exchange(request, ColumnContent.class);
			ColumnContent result = putResult.getBody();
			assertEquals(HttpStatus.OK, putResult.getStatusCode());
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType2, result.getType());
			assertEquals(childColumnId, result.getContentId());
			assertEquals(positionChanged, result.getPosition());
			columnContent2.setPosition(columnContentPosition2);
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithIdType1, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType2, result.getType());
			assertEquals(childColumnId, result.getContentId());
			assertEquals(positionChanged, result.getPosition());			
		}
		{
			// delete
			restTemplate.delete(columnContentUrlWithId);
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(0, columnContents.length);
		}
		{
			// delete product
			restTemplate.delete(productUriWithId);
		}
		{
			// check relation between column and content for the product, it should be 404
			RequestEntity<Void> request = RequestEntity.get(new URI(columnContentUrlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
		}
	}

	@Test
	public void testDeleteColumn() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, columnContentUriWithIdType0);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// post a column
			restTemplate.postForObject(columnUri, column, Column.class);
		}
		{
			// post a relation between column and content
			restTemplate.postForObject(columnContentUri, columnContent1, ColumnContent.class);
		}
		{
			// delete the column
			restTemplate.delete(columnUriWithId);
		}
		{
			// check the relation between column and content, it should not be exist
			RequestEntity<Void> request = RequestEntity.get(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
		}
	}
	

	@Test
	public void testDeleteChildColumn() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s/%d", port, contextPath, columnUri, childColumnId);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a child column
			restTemplate.postForObject(columnUri, childColumn, Column.class);
		}
		{
			// post a column
			restTemplate.postForObject(columnUri, column, Column.class);
		}
		{
			// post a relation between column and content
			restTemplate.postForObject(columnContentUri, columnContent2, ColumnContent.class);
		}
		{
			// delete the child column
			RequestEntity<Void> request = RequestEntity.delete(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}

	@Test
	public void testDeleteContent() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, contentUriWithId);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// post a column
			restTemplate.postForObject(columnUri, column, Column.class);
		}
		{
			// post a relation between column and content
			restTemplate.postForObject(columnContentUri, columnContent1, ColumnContent.class);
		}
		{
			// delete the content
			RequestEntity<Void> request = RequestEntity.delete(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}
	
	@Test
	public void testNotFound() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s%s", port, contextPath, columnContentUriWithIdType0);
		{
			restTemplate.postForObject(productUri, product, Product.class);
			restTemplate.postForObject(columnUri, column, Column.class);
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// get not found
			RequestEntity<Void> request = RequestEntity.get(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(columnContentErrorIdType0, body.get("id"));
		}
		{
			// put not found
			RequestEntity<ColumnContent> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(columnContent1);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(columnContentErrorIdType0, body.get("id"));
		}
		{
			// delete not found
			RequestEntity<Void> request = RequestEntity.delete(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(columnContentErrorIdType0, body.get("id"));
		}
	}
	
	@Test
	public void testMultiKey() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, columnContentUri);
		{
			restTemplate.postForObject(productUri, product, Product.class);
			restTemplate.postForObject(columnUri, column, Column.class);
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// post a relation between column and content
			ColumnContent result = restTemplate.postForObject(columnContentUri, columnContent1, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(columnContentPosition1, result.getPosition());
		}
		{
			// post again
			RequestEntity<ColumnContent> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.body(columnContent1);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}
	
	@Test
	public void testDetail() {
		{
			restTemplate.postForObject(productUri, product, Product.class);
			restTemplate.postForObject(columnUri, column, Column.class);
			restTemplate.postForObject(columnUri, childColumn, Column.class);
			restTemplate.postForObject(contentUri, content, Content.class);
			restTemplate.postForObject(columnContentUri, columnContent1, ColumnContent.class);
			restTemplate.postForObject(columnContentUri, columnContent2, ColumnContent.class);
		}
		{
			ColumnContent[] results = restTemplate.getForObject(columnContentUriWithDetail, ColumnContent[].class);
			assertEquals(2, results.length);
			
			ColumnContent result = results[1];
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType1, result.getType());
			assertEquals(contentId, result.getContentId());
			assertEquals(columnContentPosition1, result.getPosition());
			assertNotNull(result.getContent());
			assertNull(result.getChildColumn());
			assertEquals(productId, result.getContent().getProductId());
			assertEquals(contentId, result.getContent().getContentId());
			assertEquals(contentType, result.getContent().getType());
			assertEquals(contentPoster, result.getContent().getPoster());
			assertEquals(contentIcon, result.getContent().getIcon());
			assertEquals(contentScreenShot, result.getContent().getScreenShot());
			assertEquals(contentLink, result.getContent().getLink());
			assertEquals(contentTip, result.getContent().getTip());

			result = results[0];
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(columnContentType2, result.getType());
			assertEquals(childColumnId, result.getContentId());
			assertEquals(columnContentPosition2, result.getPosition());
			assertNotNull(result.getChildColumn());		
			assertNull(result.getContent());		
			assertEquals(productId, result.getChildColumn().getProductId());
			assertEquals(childColumnId, result.getChildColumn().getColumnId());
			assertEquals(columnName, result.getChildColumn().getName());
			assertEquals(columnType, result.getChildColumn().getType());
			assertEquals(columnPoster, result.getChildColumn().getPoster());
			assertEquals(columnLink, result.getChildColumn().getLink());
		}
	}
	
}
