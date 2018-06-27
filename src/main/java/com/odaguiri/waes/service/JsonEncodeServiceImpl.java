package com.odaguiri.waes.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odaguiri.waes.domain.JsonEncode;
import com.odaguiri.waes.dto.JsonEncodeResponse;
import com.odaguiri.waes.exception.JsonEncodeNotFoundException;
import com.odaguiri.waes.repository.JsonEncodeRepository;

@Service
public class JsonEncodeServiceImpl implements JsonEncodeService {

	@Autowired
	private JsonEncodeRepository jsonEncodeRepository;
	
	@Override
	public Optional<JsonEncodeResponse> findById(String id) {
		return jsonEncodeRepository.findById(id)
				.map(json -> {
					return Optional.of(new JsonEncodeResponse(json.getId(), json.getLeft(), json.getRight()));
				}).orElse(Optional.empty());
	}

	/**
	 * 
	 * @param id
	 * @param json
	 * @param method
	 * @return
	 */
	@Override
	public JsonEncodeResponse save(String id, String json, String method) {
		
		JsonEncode jsonEncode = null;
		Optional<JsonEncode> opJsonEncode = jsonEncodeRepository.findById(id);
		
		if (opJsonEncode.isPresent()) {
			jsonEncode = opJsonEncode.get();
		} else {
			jsonEncode = new JsonEncode();
			jsonEncode.setId(id);
		}
		
		if ("left".equals(method)) {
			jsonEncode.setLeft(json);
		} else {
			jsonEncode.setRight(json);
		}
		
		jsonEncode = jsonEncodeRepository.save(jsonEncode);
		
		JsonEncodeResponse response = new JsonEncodeResponse(jsonEncode.getId(), jsonEncode.getLeft(), jsonEncode.getRight());
		response.setCreate(!opJsonEncode.isPresent());
		
		return response;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public JsonEncodeResponse diff(String id) {
		
		JsonEncodeResponse response = null;
		
		Optional<JsonEncodeResponse> opJsonEncode = this.findById(id);
		
		if (opJsonEncode.isPresent()) {
			
			response = opJsonEncode.get();			
			
			if (response.getLeft() == null || response.getRight() == null) {
				response.addMessage(String.format("Missing %s data for Json with id %s", 
						response.getLeft() == null ? "left" : "right", id));
			} else {
			
    			char[] left = response.getLeft().toCharArray();
    			char[] right = response.getRight().toCharArray();
    			
    			if (Arrays.equals(left, right)) {
    				response.addMessage("Left and Right data are equal");
    			} else if (left.length != right.length) {				
    				response.addMessage("Left and Right data don't have same size");
    			} else {				
    				for (int index = 0; index < left.length; index++) {
    					if (left[index] != right[index]) {
    						response.addMessage(String.format("Char at index %d is different", index));
    					}
    				}
    			}
			}
		}
		return response;
	}
	
}
