package tacos.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/util")
public class UtilityController {
	
	
	@GetMapping("/whoami")
	@ResponseBody
	public String whoAmI() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return "Username: " + auth.getName() + " | Roles: " + auth.getAuthorities();
	}

}
