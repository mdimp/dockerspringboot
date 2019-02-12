/**
 * 
 */
package com.desc.jasperpoc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
//*/
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author Nazar
 *
 */
public class PDFProducer implements ReportProducer {
	
	private String jasperTemplatePath;
	
	private String jsonDocumentString;
	
	private String outputFile;
	
    public PDFProducer(String jasperTemplatePath, String jsonDocumentString) {
		this.jasperTemplatePath = jasperTemplatePath;
		this.jsonDocumentString = jsonDocumentString;
		this.outputFile = getOutputFile();
	}
    
    private String getOutputFile() {
    	/*
    	byte[] name = "pdfoutput".getBytes("UTF-8");
    	return UUID.nameUUIDFromBytes(name).toString();
    	*/
    	//UUID.fromString("pdfoutput").toString();
    	String fileName = UUID.randomUUID().toString()+".pdf";
    	String tempDirPath = System.getProperty("java.io.tmpdir");
    	//System.out.println(getClass().getSimpleName() + ": path = "+tempDirPath);
    	String fileSeparator = System.getProperty("file.separator");
    	//System.out.println(getClass().getSimpleName() + ": fileSeparator = "+fileSeparator);
    	String fullPath = tempDirPath + (tempDirPath.endsWith(fileSeparator) ? "" : fileSeparator) + fileName;
    	//System.out.println(getClass().getSimpleName() + ": fullPath = "+fullPath);
    	return fullPath;
    }
	

	public ReportDocument produce() {
        try {
        	log("Reading "+jasperTemplatePath);
            //InputStream jrxmlInput = this.getClass().getClassLoader().getResource(jrxmlFile).openStream();
        	InputStream jasperTemplateInputStream = Files.newInputStream(Paths.get(jasperTemplatePath)); 
        			
            JasperDesign design = JRXmlLoader.load(jasperTemplateInputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            log("Report compiled");

            JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(jsonDocumentString.getBytes()), "sections");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jsonDataSource);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jsonDataSource.subDataSource("sections"));

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            Path path = Paths.get(outputFile);
            OutputStream responseOutputStream = Files.newOutputStream(path);

            //OutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(pdfReportStream.toByteArray());
            responseOutputStream.close();
            pdfReportStream.close();
            log("Completed Successfully: ");
            
            return new ReportDocument(DocumentType.PDF, outputFile);
        } catch (Exception e) {
            log("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    private void log(String msg) {
    	log(msg, null);
    }
    
    private void log(String msg, Exception e) {
    	System.out.println(msg);
    	if (e != null)
    		e.printStackTrace();
    }

}