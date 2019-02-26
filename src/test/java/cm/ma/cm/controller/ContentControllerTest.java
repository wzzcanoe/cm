package cm.ma.cm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import com.ma.cm.entity.Content;
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentControllerTest extends AControllerTest{

	@Test
	public void testFunction() throws URISyntaxException {
		String contentNameChanged = "changed.name";
		String contentUrl= String.format("http://localhost:%d%s%s", port, contextPath, contentUri);

		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// check contents of the product
			Content[] contents = restTemplate.getForObject(contentUri, Content[].class);
			assertEquals(0, contents.length);
		}
		{
			// post a content
			Content result = postContentForContent(content, null);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertNull(result.getPoster());
			assertNull(result.getIcon());
			assertNull(result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
			assertEquals(contentName, result.getName());
			assertEquals(contentOptions, result.getOptions());
		}
		{
			// get contents
			Content[] contents = restTemplate.getForObject(contentUri, Content[].class);
			assertEquals(1, contents.length);
		}
		{
			// get the content
			Content result = restTemplate.getForObject(contentUriWithId, Content.class);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertNull(result.getPoster());
			assertNull(result.getIcon());
			assertNull(result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
			assertEquals(contentName, result.getName());
			assertEquals(contentOptions, result.getOptions());
		}
		{
			// put the content
			content.setName(contentNameChanged);
			Content result = putContentForContent(content, null);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertNull(result.getPoster());
			assertNull(result.getIcon());
			assertNull(result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
			assertEquals(contentNameChanged, result.getName());
			assertEquals(contentOptions, result.getOptions());
			content.setName(contentName);
		}
		{
			// get the content
			Content result = restTemplate.getForObject(contentUriWithId, Content.class);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertNull(result.getPoster());
			assertNull(result.getIcon());
			assertNull(result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
			assertEquals(contentNameChanged, result.getName());
			assertEquals(contentOptions, result.getOptions());
		}
		{
			// delete the content
			restTemplate.delete(contentUriWithId);
		}
		{
			// get contents
			Content[] contents = restTemplate.getForObject(contentUri, Content[].class);
			assertEquals(0, contents.length);
		}
		{
			// delete the product
			restTemplate.delete(productUriWithId);
		}
		{
			// check contents for the product, it should be 404
			RequestEntity<Void> request = RequestEntity.get(new URI(contentUrl)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(productErrorId, body.get("id"));
		}
	}

	@Test
	public void testMultiKey() throws URISyntaxException {
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			Content result = postContentForContent(content, null);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
		}
		{
			// post again
			ResponseEntity<HashMap<String, Object>> result = postContentForEntity(content, null);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}
	
	@Test
	public void testNotFound() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s%s", port, contextPath, contentUriWithId);
		{
			restTemplate.postForObject(productUri, product, Product.class);
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
			assertEquals(contentErrorId, body.get("id"));
		}
		{
			// put not found
			ResponseEntity<HashMap<String, Object>> result = putContentForEntity(content, null);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(contentErrorId, body.get("id"));
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
			assertEquals(contentErrorId, body.get("id"));
		}
	}

	@Test
	public void testPostWithoutProduct() throws URISyntaxException {
		ResponseEntity<HashMap<String, Object>> result = postContentForEntity(content, null);
		HashMap<String, Object> body = result.getBody();
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertNotNull(body.get("error"));
		assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
	}

	@Test
	public void testDeleteProduct() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, contentUriWithId);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			postContentForContent(content, null);
		}
		{
			// delete the product
			restTemplate.delete(productUriWithId);
		}
		{
			// check the content of the product, it should be not exist
			RequestEntity<Void> request = RequestEntity.get(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
		}
	}

	@Test
	public void testAutoIncrement() {	
		Content content = new Content(productId, columnName, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip, columnOptions);
		// post a product
		restTemplate.postForObject(productUri, product, Product.class);

		// get contents from the product
		Content[] results = restTemplate.getForObject(contentUri, Content[].class);
		int contentCountBefore = results.length;

		// post a content
		Content result = postContentForContent(content, null);
		assertEquals(productId, result.getProductId());
		assertNotEquals(0, result.getContentId());
		assertEquals(contentType, result.getType());
		assertNull(result.getPoster());
		assertNull(result.getIcon());
		assertNull(result.getScreenShot());
		assertEquals(contentLink, result.getLink());
		assertEquals(contentTip, result.getTip());
		
		// get contents from the product
		results = restTemplate.getForObject(contentUri, Content[].class);
		assertEquals(contentCountBefore + 1, results.length);
		
		// delete the content
		restTemplate.delete(String.format("%s/%d", contentUri, result.getContentId()));
		
		// get contents from the product
		results = restTemplate.getForObject(contentUri, Content[].class);
		assertEquals(contentCountBefore, results.length);
	}

	@Test
	public void testPoster() {

		// post a product
		restTemplate.postForObject(productUri, product, Product.class);

		// post a content
		Content result = postContentForContent(content, posterFilepath);
		assertEquals(productId, result.getProductId());
		assertEquals(contentId, result.getContentId());
		assertNotNull(result.getPoster());
		assertNotNull(result.getIcon());
		assertNotNull(result.getScreenShot());

		// get the content
		result = restTemplate.getForObject(contentUriWithId, Content.class);
		assertEquals(productId, result.getProductId());
		assertEquals(contentId, result.getContentId());
		assertNotNull(result.getPoster());
		assertNotNull(result.getIcon());
		assertNotNull(result.getScreenShot());

		// put the column
		result = putContentForContent(content, posterFilepath);
		assertEquals(productId, result.getProductId());
		assertEquals(contentId, result.getContentId());
		assertNotNull(result.getPoster());
		assertNotNull(result.getIcon());
		assertNotNull(result.getScreenShot());

	}
}
