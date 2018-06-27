package com.odaguiri.waes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JsonEncodeResponse implements Serializable {

	private String id;
	private String left;
	private String right;
	private List<String> message;
	
	@JsonIgnore
	private boolean create = true;
	
	@JsonIgnore
	private boolean found = true;
	
	public JsonEncodeResponse() {		
	}

	public JsonEncodeResponse(String id, String left, String right) {
		this.id = id;
		this.left = left;
		this.right = right;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
	
	public void addMessage(String newMessage) {
		if (message == null) {
			message = new ArrayList<>();
		}
		message.add(newMessage);
	}
		
}
