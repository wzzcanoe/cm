package cm.ma.cm.controller;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import com.ma.cm.entity.Column;
import com.ma.cm.entity.ColumnContent;
import com.ma.cm.entity.Content;
import com.ma.cm.entity.Product;

public abstract class AControllerTest {

	protected long productId = 999999;
	
	protected long columnId = 999999;
	
	protected long childColumnId = columnId + 1;

	protected long contentId = 999999;
	
	
	protected String contextPath = "/cm";

	// /v1/products
	protected String productUri = "/v1/products";

	// /v1/products/999999
	protected String productUriWithId = String.format("%s/%d", productUri, productId);
	

	// /v1/products/999999/columns
	protected String columnUri = String.format("%s/columns", productUriWithId);

	// /v1/products/999999/columns/999999
	protected String columnUriWithId = String.format("%s/%d", columnUri, columnId);

	// /v1/products/999999/columns/1000000
	protected String columnUriWithChildId = String.format("%s/%d", columnUri, childColumnId);
	

	// /v1/products/999999/contents
	protected String contentUri = String.format("%s/contents", productUriWithId);

	// /v1/products/999999/contents/999999
	protected String contentUriWithId = String.format("%s/%d", contentUri, contentId);
	
	
	// /v1/products/999999/columns/999999/contents
	protected String columnContentUri = String.format("%s/contents", columnUriWithId);

	// /v1/products/999999/columns/999999/contents/999999?type=0
	protected String columnContentUriWithIdType0 = String.format("%s/%d?type=0", columnContentUri, contentId);

	// /v1/products/999999/columns/999999/contents/1000000?type=1
	protected String columnContentUriWithIdType1 = String.format("%s/%d?type=1", columnContentUri, childColumnId);

	// /v1/products/999999/columns/999999/contents?detail
	protected String columnContentUriWithDetail = String.format("%s?detail", columnContentUri);
	

	protected String productErrorId = String.valueOf(productId);

	protected String columnErrorId = String.format("%d:%d", productId, columnId);
	
	protected String contentErrorId = String.format("%d:%d", productId, contentId);
	
	protected String columnContentErrorIdType0 = String.format("%d:%d:%d:%d", productId, columnId, 0, contentId);
	
	protected String columnContentErrorIdType1 = String.format("%d:%d:%d:%d", productId, columnId, 1, contentId);

	
	@Autowired
	protected TestRestTemplate restTemplate;

	@LocalServerPort
	protected int port;
	

	protected String productName = "test";
	protected String proudctOptions = "options";
	protected Product product = new Product(productId, productName, proudctOptions);
	

	protected int contentType = 0;
	protected String contentName = "name";
	protected String contentPoster = "demo.pic";
	protected String contentLink = "demo.html";
	protected String contentIcon = "demo.icon";
	protected String contentScreenShot ="demo.screenShot";
	protected String contentTip = "demo.tip";
	protected String contentOptions = "options";
	protected Content content = new Content(productId, contentId, contentName, contentPoster, contentIcon, contentScreenShot, contentType, contentLink, contentTip, contentOptions);


	protected String columnName = "test";
	protected int columnType = 0;
	protected String columnPoster = "demo.pic";
	protected String columnLink = "demo.html";
	protected String columnOptions = "options";
	protected Column column = new Column(productId, columnId, columnName, columnType, columnPoster, columnLink, columnOptions);
	protected Column childColumn = new Column(productId, childColumnId, columnName, columnType, columnPoster, columnLink, columnOptions);
	
	protected int columnContentType1 = ColumnContent.TYPE_CONTENT;
	protected long columnContentPosition1 = 9;
	protected ColumnContent columnContent1 = new ColumnContent(productId, columnId, columnContentType1, contentId, columnContentPosition1);
	
	protected int columnContentType2 = ColumnContent.TYPE_CHILD_COLUMN;
	protected long columnContentPosition2 = 8;
	protected ColumnContent columnContent2 = new ColumnContent(productId, columnId, columnContentType2, childColumnId, columnContentPosition2);
	
	
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
