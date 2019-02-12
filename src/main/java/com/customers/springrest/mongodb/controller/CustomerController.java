package com.customers.springrest.mongodb.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customers.springrest.mongodb.model.Customer;
import com.customers.springrest.mongodb.repo.CustomerRepository;
import com.desc.jasperpoc.ReportDocument;
import com.desc.jasperpoc.ReportProducerController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository repository;	

	
	@RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> download(@RequestBody String payload) throws IOException {
	String fileName = "testReport1.pdf";
	System.out.println("Calling Download:- " + fileName);

	String jasperTemplatePath="C:\\Users\\Admin\\Documents\\jasper-poc\\jasper-poc\\src\\main\\resources\\tableofcontents.jrxml";
	ReportProducerController reportProducerController = new ReportProducerController();
	String jsonDocumentString = "{\"fh\":\"jj\"}";
	ReportDocument reportDocument = reportProducerController.produceReport(jasperTemplatePath, payload);

	//ClassPathResource pdfFile = new ClassPathResource(reportDocument.getFilePath());
    FileSystemResource pdfInputStearm = new FileSystemResource(new File(reportDocument.getFilePath()));  // Files.newInputStream(path);
    //OutputStream responseOutputStream = Files.newOutputStream(path);
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.parseMediaType("application/pdf"));
	headers.add("Access-Control-Allow-Origin", "*");
	headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
	headers.add("Access-Control-Allow-Headers", "Content-Type");
	headers.add("Content-Disposition", "filename=" + fileName);
	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	headers.add("Pragma", "no-cache");
	headers.add("Expires", "0");

	


	headers.setContentLength(pdfInputStearm.contentLength());
	ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
			//new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
			new InputStreamResource(pdfInputStearm.getInputStream()), headers, HttpStatus.OK);
	return response;

	}

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("Get all Customers...");

		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);

		return customers;
	}

	@PostMapping("/customers/create")
	public Customer postCustomer(@RequestBody Customer customer) {

		Customer _customer = repository.save(new Customer(customer.getId(), customer.getEntity(), customer.getFormat(), customer.getTitle(),
				customer.getTitle(), customer.getDescription(), customer.getAuthors() ));
		return _customer;
	}
	
	
	

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
		System.out.println("Delete Customer with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete All Customers...");

		repository.deleteAll();

		return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
	}

	//	@GetMapping("customers/age/{age}")
	//	public List<Customer> findByAge(@PathVariable int age) {
	//
	//		List<Customer> customers = repository.findByAge(age);
	//		return customers;
	//	}

//	@PutMapping("/customers/{id}")
//	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
//		System.out.println("Update Customer with ID = " + id + "...");
//
//		Optional<Customer> customerData = repository.findById(id);
//
//		if (customerData.isPresent()) {
//			Customer _customer = customerData.get();
//			_customer.setName(customer.getName());
//			_customer.setAge(customer.getAge());
//			_customer.setActive(customer.isActive());
//			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
}
