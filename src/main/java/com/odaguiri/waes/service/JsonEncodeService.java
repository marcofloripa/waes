package com.odaguiri.waes.service;

import java.util.Optional;

import com.odaguiri.waes.dto.JsonEncodeResponse;

public interface JsonEncodeService {

	Optional<JsonEncodeResponse> findById(String id);
	JsonEncodeResponse save(String id, String json, String method);
	JsonEncodeResponse diff(String id);
}
