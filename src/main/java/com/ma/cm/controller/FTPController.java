package com.ma.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ma.cm.entity.FTP;
import com.ma.cm.service.FTPService;
import com.ma.cm.util.FTPClientHelper;

@RestController
@RequestMapping("/v1/ftps")
public class FTPController {

	@Autowired
	private FTPService ftpService;
	
	@Autowired
	private FTPClientHelper ftpClient;

	@RequestMapping(method = RequestMethod.GET)
	public List<FTP> gets() {
		return ftpService.gets();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public FTP get(@PathVariable long id) {
		return ftpService.get(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public FTP update(@RequestBody FTP ftp) {
		FTP result = ftpService.update(ftp);
		ftpClient.change(ftp);
		return result;
	}

}
