package com.odaguiri.waes.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class JsonEncodeRequest implements Serializable {

	@NotBlank
	private String content;

	public JsonEncodeRequest() {
	}
	
	public JsonEncodeRequest(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
