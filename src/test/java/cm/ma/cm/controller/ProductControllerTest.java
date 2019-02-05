package cm.ma.cm.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ma.cm.Application;
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

	private String uri = "/v1/products";
	
	private long id = 999999;
	
	private String uriWithId = String.format("%s/%d", uri, id);

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		// delete
		restTemplate.delete(uriWithId);
	}
	
	@After
	public void teardown() {
		// delete
		restTemplate.delete(uriWithId);
	}

	@Test
	public void testFunction() throws URISyntaxException {
		String name = "test";
		String changedName = "test-changed";
		Product product = new Product(id, name);
		String urlWithId = String.format("http://localhost:%d%s/%d", port, uri, id);

		// gets
		Product[] products = restTemplate.getForObject(uri, Product[].class);
		int before_post_count = products.length;

		// post
		Product result = restTemplate.postForObject(uri, product, Product.class);
		assertEquals(id, result.getId());
		assertEquals(name, result.getName());

		// gets
		products = restTemplate.getForObject(uri, Product[].class);
		int after_post_count = products.length;
		assertEquals(before_post_count + 1, after_post_count);

		// get
		result = restTemplate.getForObject(uriWithId, Product.class);
		assertEquals(id, result.getId());
		assertEquals(name, result.getName());

		// put
		product.setName(changedName);
		RequestEntity<Product> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
				.body(product);
		ResponseEntity<Product> putResult = restTemplate.exchange(request, Product.class);
		Product updatedProduct = putResult.getBody();
		assertEquals(HttpStatus.OK, putResult.getStatusCode());
		assertEquals(id, updatedProduct.getId());
		assertEquals(changedName, updatedProduct.getName());

		// get
		result = restTemplate.getForObject(uriWithId, Product.class);
		assertEquals(id, result.getId());
		assertEquals(changedName, result.getName());

		// delete
		restTemplate.delete(uriWithId);

		// gets
		products = restTemplate.getForObject(uri, Product[].class);
		int after_delete_count = products.length;
		assertEquals(before_post_count, after_delete_count);

	}

	@Test
	public void testMultiKey() throws URISyntaxException {
		String name = "test";
		Product product = new Product(id, name);
		String url = String.format("http://localhost:%d%s", port, uri);
		{
			// post
			Product result = restTemplate.postForObject(uri, product, Product.class);
			assertEquals(id, result.getId());
			assertEquals(name, result.getName());
		}
		{
			// post again
			ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<HashMap<String, Object>>() {
			};
			RequestEntity<Product> request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.body(product);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
		}
	}

	@Test
	public void testNotFound() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s/%d", port, uri, id);
		Product product = new Product(id, "testNotFound");
		ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<HashMap<String, Object>>() {
		};
		{
			// get not found
			RequestEntity<Void> request = RequestEntity.get(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(String.valueOf(id), body.get("id"));
		}
		{
			// put not found
			RequestEntity<Product> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(product);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(String.valueOf(id), body.get("id"));
		}
		{
			// delete not found
			RequestEntity<Void> request = RequestEntity.delete(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(String.valueOf(id), body.get("id"));
		}
	}
}
