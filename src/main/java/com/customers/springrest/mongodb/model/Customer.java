package com.customers.springrest.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	@Id
	private String id;

	
	
	
	private String type;
	private String entity;
	private String format;
	private String title;
	private String description;
	private List<Authors> authors;
	
	

	public Customer(String id, String type, String entity, String format, String title, String description,
			Authors authors) {
		super();
		this.id = id;
		this.type = type;
		this.entity = entity;
		this.format = format;
		this.title = title;
		this.description = description;
		this.authors = (List<Authors>) authors;
	}

	public List<Authors> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Authors> authors) {
		this.authors = authors;
	}

	public String getId() {
		return id;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Customer(String id, String type, String entity, String format, String title, String description,
			List<Authors> authors) {
		super();
		this.id = id;
		this.type = type;
		this.entity = entity;
		this.format = format;
		this.title = title;
		this.description = description;
		this.authors = authors;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer() {
	}

	


	

	@Override
	public String toString() {
		return "Customer [id=" + id + ",  type=" + type
				+ ", entity=" + entity + ", format=" + format + ", title=" + title + ", description=" + description
				+ "]";
	}
}
