package com.odaguiri.waes.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "json_encode")
public class JsonEncode {

	@Id	
	private String id;
	
	private String left;
	private String right;
	
	public JsonEncode() {
		
	}
	
	public JsonEncode(String id, String left, String right) {
		super();
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
	
}
