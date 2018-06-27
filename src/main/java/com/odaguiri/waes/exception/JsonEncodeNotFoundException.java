package com.odaguiri.waes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JsonEncodeNotFoundException extends Exception {

	public JsonEncodeNotFoundException(String message) {
		super(message);
	}
}
