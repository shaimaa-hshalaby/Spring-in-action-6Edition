package tacos.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.domain.TacoOrder;
import tacos.domain.User;
import tacos.repository.OrderRepository;
import tacos.repository.UserRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
	private OrderRepository orderRepository;
	private UserRepository userRepository;
	
	public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		
		return "orderForm";
	}
	
//	@PostMapping
//	public String processOrder(@Valid TacoOrder tacoOrder,Errors errors, SessionStatus sessionStatus, Principal principal) {
//		if(errors.hasErrors()) {
//			return "orderForm";
//		}
//		// the first method to get the user
//		User user = userRepository.findByUsername(principal.getName());
//	
//		log.info("[OrderController] the user is :"+user.toString());
//		
//		if (user != null) {
//			tacoOrder.setUser(user);
//		}
//		tacoOrder.setPlacedAt(new Date());
//		
//		orderRepository.save(tacoOrder);
//		sessionStatus.setComplete();
//		
//		log.info("Order Submitted: "+tacoOrder);
//		return "redirect:/";
//	}
	
//	@PostMapping
//	public String processOrder(@Valid TacoOrder tacoOrder,Errors errors, SessionStatus sessionStatus, Authentication authentication) {
//		if(errors.hasErrors()) {
//			return "orderForm";
//		}
//		// the first method to get the user
////		User user = userRepository.findByUsername(principal.getName());
//
//		// the second method to get the user
//		User user = (User) authentication.getPrincipal();
//		
//		log.info("[OrderController] the user is :"+user.toString());
//		
//		if (user != null) {
//			tacoOrder.setUser(user);
//		}
//		tacoOrder.setPlacedAt(new Date());
//		
//		orderRepository.save(tacoOrder);
//		sessionStatus.setComplete();
//		
//		log.info("Order Submitted: "+tacoOrder);
//		return "redirect:/";
//	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder tacoOrder,Errors errors, SessionStatus sessionStatus,@AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		log.info("[OrderController] the user is :"+user.toString());
		
		if (user != null) {
			tacoOrder.setUser(user);
		}
		tacoOrder.setPlacedAt(new Date());
		
		orderRepository.save(tacoOrder);
		sessionStatus.setComplete();
		
		log.info("Order Submitted: "+tacoOrder);
		return "redirect:/";
	}

}
