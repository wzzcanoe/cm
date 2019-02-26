package com.ma.cm.exception.handler;

import java.sql.SQLException;
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

@ControllerAdvice
public class SQLExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(SQLExceptionHandler.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public Map<String, Object> handle(SQLException exception) {
		logger.error(exception.getMessage());
		exception.printStackTrace();
		
		Map<String, Object> result = new HashMap<>();
		result.put("timestamp", new Date().getTime());
		result.put("status", HttpStatus.FORBIDDEN.value());
		result.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
		result.put("message", exception.getMessage());
		result.put("exception", exception.getClass().getName());
		result.put("path", request.getRequestURI());
		return result;
	}

}
