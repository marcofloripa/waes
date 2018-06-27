package com.odaguiri.waes.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.odaguiri.waes.dto.ErrorDTO;
import com.odaguiri.waes.exception.JsonEncodeNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends
		ResponseEntityExceptionHandler {

	@ExceptionHandler(JsonEncodeNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleJsonEncodeNotFoundException(JsonEncodeNotFoundException ex,
			WebRequest request) {
		ErrorDTO error = new ErrorDTO(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDTO>(error, HttpStatus.NOT_FOUND);
	}
}
