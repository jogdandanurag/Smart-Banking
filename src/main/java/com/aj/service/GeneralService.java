package com.aj.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aj.model.ContactDetails;
import com.aj.repo.ContactDetailRepo;

@Service
public class GeneralService {

	@Autowired
	ContactDetailRepo contactDetailRepo;


	
	public String saveContactDetails(ContactDetails details) {
		System.out.println("contact Details : "+ details);
		if(contactDetailRepo.save(details)!=null)
			return "Thank you, we will connect with you shortly";
		return "Sorry failed to submit your details, please try again";
	}
	
	 public String getCurrentUsername() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.isAuthenticated()) {
	            Object principal = authentication.getPrincipal();
	            
	            if (principal instanceof UserDetails) {
	                return ((UserDetails) principal).getUsername();
	            } else {
	                return principal.toString();
	            }
	        }
	        throw new RuntimeException("User not authenticated");
	    }

}
