package com.desc.jasperpoc;

/**
 * 
 * @author Nazar
 *
 */
enum DocumentType {
	PDF("pdf");
	
	private String type;
	
	DocumentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}