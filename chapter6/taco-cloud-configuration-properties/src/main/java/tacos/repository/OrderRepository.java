package tacos.repository;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tacos.domain.TacoOrder;
import tacos.domain.User;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{

	List<TacoOrder> findByDeliveryZip(String deliveryZip);
	
	List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
	
	List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

}
