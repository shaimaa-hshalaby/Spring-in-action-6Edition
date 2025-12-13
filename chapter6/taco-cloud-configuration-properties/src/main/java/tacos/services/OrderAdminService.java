package tacos.services;

import org.springframework.stereotype.Service;

import tacos.repository.OrderRepository;


@Service
public class OrderAdminService {
	
	private OrderRepository orderRepository;
	
	public OrderAdminService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public void deleteAllOrders() {
		this.orderRepository.deleteAll();
	}
	

}
