package com.ma.cm.exception.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ma.cm.exception.ForbiddenException;

@ControllerAdvice
public class ForbiddenExceptionHandler {
	
	@Autowired
	private HttpServletRequest request;
	
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public Map<String, Object> handle(ForbiddenException exception) {
		// TODO
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
