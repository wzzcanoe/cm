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
import com.ma.cm.entity.Content;
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentControllerTest extends AControllerTest{

	@Test
	public void testFunction() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentPosterChanged = "demo-changed.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		String contentUrl= String.format("http://localhost:%d%s", port, contentUri);
		String contentUrlWithId = String.format("http://localhost:%d%s", port, contentUriWithId);

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
			Content result = restTemplate.postForObject(contentUri, content, Content.class);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertEquals(contentPoster, result.getPoster());
			assertEquals(contentIcon, result.getIcon());
			assertEquals(contentScreenShot, result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
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
			assertEquals(contentPoster, result.getPoster());
			assertEquals(contentIcon, result.getIcon());
			assertEquals(contentScreenShot, result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
		}
		{
			// put the content
			content.setPoster(contentPosterChanged);
			RequestEntity<Content> request = RequestEntity.put(new URI(contentUrlWithId))
					.accept(MediaType.APPLICATION_JSON).body(content);
			ResponseEntity<Content> putResult = restTemplate.exchange(request, Content.class);
			Content result = putResult.getBody();
			assertEquals(HttpStatus.OK, putResult.getStatusCode());
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertEquals(contentPosterChanged, result.getPoster());
			assertEquals(contentIcon, result.getIcon());
			assertEquals(contentScreenShot, result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
		}
		{
			// get the content
			Content result = restTemplate.getForObject(contentUriWithId, Content.class);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
			assertEquals(contentType, result.getType());
			assertEquals(contentPosterChanged, result.getPoster());
			assertEquals(contentIcon, result.getIcon());
			assertEquals(contentScreenShot, result.getScreenShot());
			assertEquals(contentLink, result.getLink());
			assertEquals(contentTip, result.getTip());
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
		String productName = "test";
		Product product = new Product(productId, productName);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		String url = String.format("http://localhost:%d%s", port, contentUri);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a content
			Content result = restTemplate.postForObject(contentUri, content, Content.class);
			assertEquals(productId, result.getProductId());
			assertEquals(contentId, result.getContentId());
		}
		{
			// post again
			RequestEntity<Content> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.body(content);
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
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		
		String urlWithId = String.format("http://localhost:%d%s", port, contentUriWithId);
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
			RequestEntity<Content> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(content);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
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
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
		String url = String.format("http://localhost:%d%s", port, contentUri);
		RequestEntity<Content> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
				.body(content);
		ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
		HashMap<String, Object> body = result.getBody();
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertNotNull(body.get("error"));
		assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
	}

	@Test
	public void testDeleteProduct() throws URISyntaxException {
		String productName = "test";
		Product product = new Product(productId, productName);
		int contentType = 0;
		String contentPoster = "demo.pic";
		String contentLink = "demo.html";
		String contentIcon = "demo.icon";
		String contentScreenShot ="demo.screenShot";
		String contentTip = "demo.tip";
		Content content = new Content(productId, contentId, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip);
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
}
