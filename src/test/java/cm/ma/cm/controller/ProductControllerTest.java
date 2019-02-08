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
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends AControllerTest{

	@Test
	public void testFunction() throws URISyntaxException {
		String name = "test";
		String changedName = "test-changed";
		Product product = new Product(productId, name);
		String urlWithId = String.format("http://localhost:%d%s", port, productUriWithId);

		// gets
		Product[] products = restTemplate.getForObject(productUri, Product[].class);
		int before_post_count = products.length;

		// post
		Product result = restTemplate.postForObject(productUri, product, Product.class);
		assertEquals(productId, result.getId());
		assertEquals(name, result.getName());

		// gets
		products = restTemplate.getForObject(productUri, Product[].class);
		int after_post_count = products.length;
		assertEquals(before_post_count + 1, after_post_count);

		// get
		result = restTemplate.getForObject(productUriWithId, Product.class);
		assertEquals(productId, result.getId());
		assertEquals(name, result.getName());

		// put
		product.setName(changedName);
		RequestEntity<Product> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
				.body(product);
		ResponseEntity<Product> putResult = restTemplate.exchange(request, Product.class);
		Product updatedProduct = putResult.getBody();
		assertEquals(HttpStatus.OK, putResult.getStatusCode());
		assertEquals(productId, updatedProduct.getId());
		assertEquals(changedName, updatedProduct.getName());

		// get
		result = restTemplate.getForObject(productUriWithId, Product.class);
		assertEquals(productId, result.getId());
		assertEquals(changedName, result.getName());

		// delete
		restTemplate.delete(productUriWithId);

		// gets
		products = restTemplate.getForObject(productUri, Product[].class);
		int after_delete_count = products.length;
		assertEquals(before_post_count, after_delete_count);

	}

	@Test
	public void testMultiKey() throws URISyntaxException {
		String name = "test";
		Product product = new Product(productId, name);
		String url = String.format("http://localhost:%d%s", port, productUri);
		{
			// post
			Product result = restTemplate.postForObject(productUri, product, Product.class);
			assertEquals(productId, result.getId());
			assertEquals(name, result.getName());
		}
		{
			// post again
			RequestEntity<Product> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.body(product);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}

	@Test
	public void testNotFound() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s", port, productUriWithId);
		Product product = new Product(productId, "testNotFound");
		{
			// get not found
			RequestEntity<Void> request = RequestEntity.get(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(productErrorId, body.get("id"));
		}
		{
			// put not found
			RequestEntity<Product> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(product);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(productErrorId, body.get("id"));
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
			assertEquals(productErrorId, body.get("id"));
		}
	}
}
