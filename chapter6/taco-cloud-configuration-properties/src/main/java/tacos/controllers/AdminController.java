package tacos.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.services.OrderAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private OrderAdminService adminService;
	
	public AdminController(OrderAdminService orderAdminService) {
		this.adminService = orderAdminService;
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteOrders")
	public String deleteAllOrders() {
		this.adminService.deleteAllOrders();
		return "redirect:/admin";
	}

}
