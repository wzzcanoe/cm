package cm.ma.cm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	public void testFunction() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot = "demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType,
				contentLink, contentTip);
		String columnContentUrlWithId = String.format("http://localhost:%d%s", port, columnContentUriWithId);
		long position = 9;
		long positionChanged = 99;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
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
			ColumnContent result = restTemplate.postForObject(columnContentUri, columnContent, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(contentId, result.getContentId());
			assertEquals(position, result.getPosition());
		}
		{
			// gets
			ColumnContent[] columnContents = restTemplate.getForObject(columnContentUri, ColumnContent[].class);
			assertEquals(1, columnContents.length);
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithId, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(contentId, result.getContentId());
			assertEquals(position, result.getPosition());			
		}
		{
			// put
			columnContent.setPosition(positionChanged);
			RequestEntity<ColumnContent> request = RequestEntity.put(new URI(columnContentUrlWithId))
					.accept(MediaType.APPLICATION_JSON).body(columnContent);
			ResponseEntity<ColumnContent> putResult = restTemplate.exchange(request, ColumnContent.class);
			ColumnContent result = putResult.getBody();
			assertEquals(HttpStatus.OK, putResult.getStatusCode());
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(contentId, result.getContentId());
			assertEquals(positionChanged, result.getPosition());
		}
		{
			// get
			ColumnContent result = restTemplate.getForObject(columnContentUriWithId, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
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
	public void testDeleteColumn() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot = "demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType,
				contentLink, contentTip);
		long position = 4;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
		String url = String.format("http://localhost:%d%s", port, columnContentUriWithId);
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
			restTemplate.postForObject(columnContentUri, columnContent, ColumnContent.class);
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
	public void testDeleteContent() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		long position = 9;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
		String url = String.format("http://localhost:%d%s", port, contentUriWithId);
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
			restTemplate.postForObject(columnContentUri, columnContent, ColumnContent.class);
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
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		long position = 9;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
		
		String urlWithId = String.format("http://localhost:%d%s", port, columnContentUriWithId);
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
			assertEquals(columnContentErrorId, body.get("id"));
		}
		{
			// put not found
			RequestEntity<ColumnContent> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(columnContent);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(columnContentErrorId, body.get("id"));
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
			assertEquals(columnContentErrorId, body.get("id"));
		}
	}
	
	@Test
	public void testMultiKey() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		long position = 9;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
		
		String url = String.format("http://localhost:%d%s", port, columnContentUri);
		{
			restTemplate.postForObject(productUri, product, Product.class);
			restTemplate.postForObject(columnUri, column, Column.class);
			restTemplate.postForObject(contentUri, content, Content.class);
		}
		{
			// post a relation between column and content
			ColumnContent result = restTemplate.postForObject(columnContentUri, columnContent, ColumnContent.class);
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(contentId, result.getContentId());
		}
		{
			// post again
			RequestEntity<ColumnContent> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.body(columnContent);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}
	
	@Test
	public void testDetail() {
		String productName = "test";
		Product product = new Product(productId, productName);
		String columnName = "test";
		int columnType = 1;
		String columnPoster = "poster";
		String columnLink = "link";
		Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		long position = 9;
		ColumnContent columnContent = new ColumnContent(productId, columnId, contentId, position);
		
		{
			restTemplate.postForObject(productUri, product, Product.class);
			restTemplate.postForObject(columnUri, column, Column.class);
			restTemplate.postForObject(contentUri, content, Content.class);
			restTemplate.postForObject(columnContentUri, columnContent, ColumnContent.class);
		}
		{
			ColumnContent[] results = restTemplate.getForObject(columnContentUriWithDetail, ColumnContent[].class);
			assertEquals(1, results.length);
			ColumnContent result = results[0];
			assertEquals(productId, result.getProductId());
			assertEquals(columnId, result.getColumnId());
			assertEquals(contentId, result.getContentId());
			assertEquals(position, result.getPosition());	
			assertNotNull(result.getContent());
			assertEquals(productId, result.getContent().getProductId());
			assertEquals(contentId, result.getContent().getContentId());
			assertEquals(contentType, result.getContent().getType());
			assertEquals(contentPoster, result.getContent().getPoster());
			assertEquals(contentIcon, result.getContent().getIcon());
			assertEquals(contentScreenShot, result.getContent().getScreenShot());
			assertEquals(contentLink, result.getContent().getLink());
			assertEquals(contentTip, result.getContent().getTip());
		}
	}
}
