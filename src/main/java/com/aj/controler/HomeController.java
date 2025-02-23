package com.aj.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; 
  

@Controller
public class HomeController {

	
	@GetMapping(value = "/{path:[^\\.]*}")
	public String showPage() {
		return "forward:/index.html"; 
	}
}