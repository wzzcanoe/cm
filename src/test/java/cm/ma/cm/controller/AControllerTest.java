package cm.ma.cm.controller;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;

public abstract class AControllerTest {

	protected long productId = 999999;
	
	protected long columnId = 999999;

	protected long contentId = 999999;
	

	protected String productUri = "/v1/products";

	protected String productUriWithId = String.format("%s/%d", productUri, productId);
	

	protected String columnUri = String.format("%s/columns", productUriWithId);

	protected String columnUriWithId = String.format("%s/%d", columnUri, columnId);
	

	protected String contentUri = String.format("%s/contents", productUriWithId);

	protected String contentUriWithId = String.format("%s/%d", contentUri, contentId);
	

	protected String columnContentUri = String.format("%s/contents", columnUriWithId);

	protected String columnContentUriWithId = String.format("%s/%d", columnContentUri, contentId);

	protected String columnContentUriWithDetail = String.format("%s?detail", columnContentUri);
	

	protected String productErrorId = String.valueOf(productId);

	protected String columnErrorId = String.format("%d:%d", productId, columnId);
	
	protected String contentErrorId = String.format("%d:%d", productId, contentId);
	
	protected String columnContentErrorId = String.format("%d:%d:%d", productId, columnId, contentId);

	
	@Autowired
	protected TestRestTemplate restTemplate;

	@LocalServerPort
	protected int port;
	

	protected ParameterizedTypeReference<HashMap<String, Object>> responseType = new ParameterizedTypeReference<HashMap<String, Object>>() {
	};

	@Before
	public void setup() {
		// delete
		restTemplate.delete(productUriWithId);
	}

	@After
	public void teardown() {
		// delete
		restTemplate.delete(productUriWithId);
	}

}
