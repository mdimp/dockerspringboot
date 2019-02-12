/**
 * 
 */
package com.desc.jasperpoc;

/**
 * @author Nazar
 *
 */
public class ReportsProducerFactory {
	
	public ReportProducer getReportProducer(DocumentType documentType, String jasperTemplatePath, String jsonDocumentString) {
		if (documentType == DocumentType.PDF)
			return new PDFProducer(jasperTemplatePath, jsonDocumentString);

		throw new RuntimeException("Undefined document type: "+documentType);
	}

	private static ReportsProducerFactory instance = new ReportsProducerFactory();
	
	public static ReportsProducerFactory getInstance() {
		return instance;
	}
	
}
