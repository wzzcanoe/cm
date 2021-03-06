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
import com.ma.cm.entity.Column;
import com.ma.cm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColumnControllerTest extends AControllerTest {

	@Test
	public void testFunction() throws URISyntaxException {
		String changedColumnName = "test-changed";
		String columnUrl = String.format("http://localhost:%d%s%s", port, contextPath, columnUri);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// check columns of the product and check root column
			Column[] columns = restTemplate.getForObject(columnUri, Column[].class);
			assertEquals(0, columns.length);
		}
		{
			// post a column
			Column theColumn = postColumnForColumn(column, null);
			assertEquals(productId, theColumn.getProductId());
			assertEquals(columnId, theColumn.getColumnId());
			assertEquals(columnName, theColumn.getName());
			assertEquals(columnType, theColumn.getType());
			assertNull(theColumn.getPoster());
			assertEquals(columnLink, theColumn.getLink());
			assertEquals(columnOptions, theColumn.getOptions());
		}
		{
			// get columns
			Column[] columns = restTemplate.getForObject(columnUri, Column[].class);
			assertEquals(1, columns.length);
		}
		{
			// get the column
			Column theColumn = restTemplate.getForObject(columnUriWithId, Column.class);
			assertEquals(productId, theColumn.getProductId());
			assertEquals(columnId, theColumn.getColumnId());
			assertEquals(columnName, theColumn.getName());
			assertEquals(columnType, theColumn.getType());
			assertNull(theColumn.getPoster());
			assertEquals(columnLink, theColumn.getLink());
			assertEquals(columnOptions, theColumn.getOptions());
		}
		{
			// put the column
			column.setName(changedColumnName);
			Column theColumn = putColumnForColumn(column, null);
			assertEquals(productId, theColumn.getProductId());
			assertEquals(columnId, theColumn.getColumnId());
			assertEquals(changedColumnName, theColumn.getName());
			assertEquals(columnType, theColumn.getType());
			assertNull(theColumn.getPoster());
			assertEquals(columnLink, theColumn.getLink());
			assertEquals(columnOptions, theColumn.getOptions());
			column.setName(columnName);
		}
		{
			// get the column
			Column theColumn = restTemplate.getForObject(columnUriWithId, Column.class);
			assertEquals(productId, theColumn.getProductId());
			assertEquals(columnId, theColumn.getColumnId());
			assertEquals(changedColumnName, theColumn.getName());
			assertEquals(columnType, theColumn.getType());
			assertNull(theColumn.getPoster());
			assertEquals(columnLink, theColumn.getLink());
			assertEquals(columnOptions, theColumn.getOptions());
		}
		{
			// delete the column
			restTemplate.delete(columnUriWithId);
		}
		{
			// get columns
			Column[] columns = restTemplate.getForObject(columnUri, Column[].class);
			assertEquals(0, columns.length);
		}
		{
			// delete the product
			restTemplate.delete(productUriWithId);
		}
		{
			// check columns for the product, it should be 404
			RequestEntity<Void> request = RequestEntity.get(new URI(columnUrl)).accept(MediaType.APPLICATION_JSON)
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
			// post a column
			Column theColumn = postColumnForColumn(column, null);
			assertEquals(productId, theColumn.getProductId());
			assertEquals(columnId, theColumn.getColumnId());
		}
		{
			// post again
			ResponseEntity<HashMap<String, Object>> result = postColumnForEntity(column, null);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.FORBIDDEN.value(), body.get("status"));
		}
	}

	@Test
	public void testNotFound() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s%s", port, contextPath, columnUriWithId);
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
			assertEquals(columnErrorId, body.get("id"));
		}
		{
			// put not found
			ResponseEntity<HashMap<String, Object>> result = putColumnForEntity(column, null);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
			assertEquals(columnErrorId, body.get("id"));
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
			assertEquals(columnErrorId, body.get("id"));
		}
	}

	@Test
	public void testPostWithoutProduct() throws URISyntaxException {
		ResponseEntity<HashMap<String, Object>> result = postColumnForEntity(column, null);
		HashMap<String, Object> body = result.getBody();
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertNotNull(body.get("error"));
		assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
	}

	@Test
	public void testDeleteProduct() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, columnUriWithId);
		{
			// post a product
			restTemplate.postForObject(productUri, product, Product.class);
		}
		{
			// post a column
			postColumnForColumn(column, null);
		}
		{
			// delete the product
			restTemplate.delete(productUriWithId);
		}
		{
			// check the column of the product, it should be not exist
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
		Column column = new Column(productId, columnName, columnType, columnPoster, columnLink, columnOptions);

		// post a product
		restTemplate.postForObject(productUri, product, Product.class);

		// get columns from the product
		Column[] results = restTemplate.getForObject(columnUri, Column[].class);
		int columnCountBefore = results.length;

		// post a column
		Column result = postColumnForColumn(column, null);
		assertEquals(productId, result.getProductId());
		assertNotEquals(0, result.getColumnId());
		assertEquals(columnName, result.getName());
		assertEquals(columnType, result.getType());
		assertNull(result.getPoster());
		assertEquals(columnLink, result.getLink());

		// get columns from the product
		results = restTemplate.getForObject(columnUri, Column[].class);
		assertEquals(columnCountBefore + 1, results.length);

		// delete the column
		restTemplate.delete(String.format("%s/%d", columnUri, result.getColumnId()));

		// get columns from the product
		results = restTemplate.getForObject(columnUri, Column[].class);
		assertEquals(columnCountBefore, results.length);
	}

	@Test
	public void testPoster() {

		// post a product
		restTemplate.postForObject(productUri, product, Product.class);

		// post a column
		Column result = postColumnForColumn(column, posterFilepath);
		assertEquals(productId, result.getProductId());
		assertEquals(columnId, result.getColumnId());
		assertNotNull(result.getPoster());

		// get the column
		result = restTemplate.getForObject(columnUriWithId, Column.class);
		assertEquals(productId, result.getProductId());
		assertEquals(columnId, result.getColumnId());
		assertNotNull(result.getPoster());

		// put the column
		result = putColumnForColumn(column, posterFilepath);
		assertEquals(productId, result.getProductId());
		assertEquals(columnId, result.getColumnId());
		assertNotNull(result.getPoster());

	}

}
