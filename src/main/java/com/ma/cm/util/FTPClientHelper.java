package com.ma.cm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ma.cm.entity.FTP;
import com.ma.cm.service.FTPService;

@Component
public class FTPClientHelper {

	private FTP ftp = null;
	
	private boolean isChanged = false;

	private FTPClient ftpClient = null;
	
	@Autowired
	private FTPService ftpService;


	private void createFtpClient() throws IOException {
		if (ftp == null) {
			ftp = ftpService.get(FTP.FTP_ID);
		}
		if (null == ftpClient || isChanged) {
			boolean ok;
			ftpClient = new FTPClient();
			ftpClient.connect(ftp.getHost(), ftp.getPort());
			ok = ftpClient.login(ftp.getUsername(), ftp.getPassword());
			if (!ok) {
				throw new IOException(String.format("login on ftp server %s:%d error", ftp.getHost(), ftp.getPort()));
			}
			ftpClient.setControlEncoding("UTF-8");
			ok = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			if (!ok) {
				throw new IOException(String.format("set file type on ftp server %s:%d error", ftp.getHost(), ftp.getPort()));
			}
		}
	}
	
	public synchronized void change(FTP ftp) {
		this.ftp = ftp;
		isChanged = true;
	}

	public synchronized void uploadFile(String pathname, String fileName, InputStream inputStream) throws IOException {
		pathname = new String(pathname.getBytes("UTF-8"), "ISO-8859-1");
		createFtpClient();
		try {
			changeWorkingDirectory(pathname);
		} catch (IOException e) {
			makeDirectory(pathname);
			changeWorkingDirectory(pathname);
		}
		if (!ftpClient.storeFile(fileName, inputStream)) {
			throw new IOException(String.format("store file %s on ftp server %s:%d error", fileName, ftp.getHost(), ftp.getPort()));
		}
	}

	private void changeWorkingDirectory(String pathname) throws IOException {
		if (!ftpClient.changeWorkingDirectory(pathname)) {
			throw new IOException(
					String.format("change working directory to %s on ftp server %s:%d error", pathname, ftp.getHost(), ftp.getPort()));
		}
	}

	private void makeDirectory(String pathname) throws IOException {
		FTPFile[] files = ftpClient.listFiles(pathname);
		if (files.length > 0) {
			return;
		}
		if (!ftpClient.makeDirectory(pathname)) {
			throw new IOException(String.format("make directory %s on ftp server %s:%d error", pathname, ftp.getHost(), ftp.getPort()));
		}
	}

	public static void main(String[] args) throws IOException {
		FTPClientHelper helper = new FTPClientHelper();
		InputStream inputStream = new FileInputStream(
				"D:\\program\\apache-tomcat-9.0.14\\work\\Catalina\\localhost\\cm\\poster.gif");
		helper.uploadFile("/中国/2/", "poster.gif", inputStream);
	}
}
