package com.ma.cm.exception.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ma.cm.exception.FTPException;

@ControllerAdvice
public class FTPExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(FTPExceptionHandler.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@ExceptionHandler(FTPException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public Map<String, Object> handle(FTPException exception) {
		logger.error(exception.getMessage());
		exception.printStackTrace();
		
		Map<String, Object> result = new HashMap<>();
		result.put("timestamp", new Date().getTime());
		result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		result.put("message", exception.getMessage());
		result.put("exception", exception.getClass().getName());
		result.put("path", request.getRequestURI());
		return result;
	}

}
