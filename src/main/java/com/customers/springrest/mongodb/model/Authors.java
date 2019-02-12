package com.customers.springrest.mongodb.model;



public class Authors {
	
	private String name;
	private String title;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Authors [name=" + name + ", title=" + title + "]";
	}
	public Authors(String name, String title) {
		super();
		this.name = name;
		this.title = title;
	}
	
	

}
