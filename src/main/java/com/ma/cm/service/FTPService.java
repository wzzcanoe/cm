package com.ma.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ma.cm.entity.FTP;
import com.ma.cm.exception.FTPNotExistException;
import com.ma.cm.mapper.FTPMapper;

@Service
public class FTPService {

	@Autowired
	private FTPMapper ftpMapper;

	@Transactional
	public List<FTP> gets() {
		List<FTP> ftps = ftpMapper.getAll();
		return ftps;
	}

	@Transactional
	public FTP get(long id) {
		FTP ftp = ftpMapper.getOne(id);
		if (null == ftp) {
			throw new FTPNotExistException(id);
		}
		return ftp;
	}

	@Transactional
	public FTP update(FTP ftp) {
		ftpMapper.update(ftp);
		FTP updatedFtp = ftpMapper.getOne(ftp.getId());
		if (null == updatedFtp) {
			throw new FTPNotExistException(ftp.getId());
		}
		return updatedFtp;
	}
}
