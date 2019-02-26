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
import com.ma.cm.entity.FTP;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FTPControllerTest extends AControllerTest {

	private String ftpUri = "/v1/ftps";

	private String ftpUriWithId = String.format("%s/%d", ftpUri, FTP.FTP_ID);

	private String ftpHost = "192.168.0.1";

	private int ftpPort = 22;

	private String ftpUsername = "username";

	private String ftpPassword = "password";

	private FTP ftp_ = new FTP(FTP.FTP_ID, ftpHost, ftpPort, ftpUsername, ftpPassword);

	@Test
	public void testFunction() throws URISyntaxException {
		String urlWithId = String.format("http://localhost:%d%s%s", port, contextPath, ftpUriWithId);

		// gets
		FTP[] ftps = restTemplate.getForObject(ftpUri, FTP[].class);
		assertEquals(1, ftps.length);
		assertEquals(FTP.FTP_ID, ftps[0].getId());

		// get
		FTP ftp = restTemplate.getForObject(ftpUriWithId, FTP.class);
		assertEquals(FTP.FTP_ID, ftp.getId());

		// put
		RequestEntity<FTP> request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
				.body(ftp_);
		ResponseEntity<FTP> putResult = restTemplate.exchange(request, FTP.class);
		FTP result = putResult.getBody();
		assertEquals(FTP.FTP_ID, result.getId());
		assertEquals(ftpHost, result.getHost());
		assertEquals(ftpPort, result.getPort());
		assertEquals(ftpUsername, result.getUsername());
		assertEquals(ftpPassword, result.getPassword());

		// recover
		request = RequestEntity.put(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON).body(ftp);
		putResult = restTemplate.exchange(request, FTP.class);
		result = putResult.getBody();
		assertEquals(FTP.FTP_ID, result.getId());
	}

	@Test
	public void testMethodNotFound() throws URISyntaxException {
		String url = String.format("http://localhost:%d%s%s", port, contextPath, ftpUri);
		String urlWithId = String.format("http://localhost:%d%s%s", port, contextPath, ftpUriWithId);
		{
			// delete
			RequestEntity<Void> request = RequestEntity.delete(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.build();
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), body.get("status"));
		}
		{
			// post
			RequestEntity<FTP> request = RequestEntity.post(new URI(urlWithId)).accept(MediaType.APPLICATION_JSON)
					.body(ftp_);
			ResponseEntity<HashMap<String, Object>> result = restTemplate.exchange(request, responseType);
			HashMap<String, Object> body = result.getBody();
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED, result.getStatusCode());
			assertNotNull(body.get("error"));
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), body.get("status"));
		}
	}
}
