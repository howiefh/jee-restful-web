package io.github.howiefh.jeews.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TestControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleNotFoundException(
			HttpServletRequest request, Exception ex) {
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorMap.put("url", request.getRequestURL().toString());
		errorMap.put("message", ex.toString());
		return new ResponseEntity<Map<String,String>>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
