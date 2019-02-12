/**
 * 
 */
package com.desc.jasperpoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Nazar
 *
 */
public class ReportProducerController {
	
	private ReportsProducerFactory reportsProducerFactory = ReportsProducerFactory.getInstance();

     public ReportDocument produceReport(String jasperTemplatePath, String jsonDocumentString) {
    	 
    	 DocumentType documentType = getDocumentType(jsonDocumentString);
    	 ReportProducer reportProducer = reportsProducerFactory.getReportProducer(documentType, jasperTemplatePath, jsonDocumentString);
    	 ReportDocument reportDocument = reportProducer.produce();
    	 return reportDocument;
    }
     
    private DocumentType getDocumentType(String jsonDocString) {
    	// TODO
    	return DocumentType.PDF;
    }

    private void log(String msg) {
    	log(msg, null);
    }
    
    private void log(String msg, Exception e) {
    	System.out.println(msg);
    	if (e != null)
    		e.printStackTrace();
    }

    public static void main(String[] args) {
//    	String jrxmlFile = "E:\\Nazar\\work\\21jan2019\\project-code\\jasper-poc\\src\\main\\resources\\test-json-format.jrxml";
//    	String outputFile = "E:\\Nazar\\work\\21jan2019\\project-code\\jasper-poc\\src\\main\\resources\\output\\test-pdf-report.json.pdf";
    	
//    	String jrxmlFile = "E:\\Nazar\\work\\21jan2019\\project-code\\jasper-poc\\src\\main\\resources\\json-report-format.jrxml";
//    	String outputFile = "E:\\Nazar\\work\\21jan2019\\project-code\\jasper-poc\\src\\main\\resources\\pdf-report.pdf";

    	String jasperTemplatePath = args[0];
    	String jsonFilePath = args[1];
    	//String outputFile = args[2];
    	ReportProducerController reportProducerController = new ReportProducerController();
    	String jsonDocumentString = reportProducerController.readJsonFile(jsonFilePath);
    	ReportDocument reportDocument = reportProducerController.produceReport(jasperTemplatePath, jsonDocumentString);
    	reportProducerController.log("Generated docuemnt type = "+reportDocument.getDocumentType());
    	reportProducerController.log("Generated document file path : "+reportDocument.getFilePath());
    }

    private String readJsonFile(String jsonFile) {
    	//String url = "http://e1255fbaf8cb8.cloud.wavemakeronline.com/RestJasper/services/hrdb/Department?sort=location&r=json";
    	//String jsonFile = "E:\\Nazar\\work\\21jan2019\\project-code\\jasper-poc\\src\\main\\resources\\test-json-input.json";
    	//String file = "";
        String jsonResponse = "";
        
        //Stream<String> st = Files.lines(Paths.get(file));

        try {
            log("Reading file : " + jsonFile);
            jsonResponse = new String(Files.readAllBytes(Paths.get(jsonFile)));
        } catch (IOException e) {
            jsonResponse = "Error encountered while reading, Error message: " + e.toString();
            log(jsonResponse);
            return jsonResponse;
        }
        //logger.info(" JSON input : " + jsonResponse);
        //log(" JSON input : " + jsonResponse);
        return jsonResponse;
    }
    
}