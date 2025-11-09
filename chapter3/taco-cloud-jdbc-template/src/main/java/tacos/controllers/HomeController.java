package tacos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// comment @Controller to test WebConfig.addViewControllers()
//@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

}
