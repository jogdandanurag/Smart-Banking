 package com.aj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aj.model.ContactDetails;
import com.aj.service.GeneralService; 
  

@RestController
public class GeneralController {

	@Autowired
	private GeneralService service;
	
	@PostMapping("/hello")
	public String logContactForm() {
		return "Hello world";
	}

	@PostMapping("/contact")
	public String logContactForm(@RequestBody ContactDetails userInfo) {
		return service.saveContactDetails(userInfo);
	}
}