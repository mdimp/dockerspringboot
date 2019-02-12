/**
 * 
 */
package com.desc.jasperpoc;

/**
 * @author Nazar
 *
 */
public class ReportDocument {
	
	private DocumentType documentType;
	private String filePath;

	public ReportDocument(DocumentType documentType, String filePath) {
		this.documentType = documentType;
		this.filePath = filePath;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public String getFilePath() {
		return filePath;
	}

}
