package tacos.repository;

import tacos.domain.TacoOrder;

public interface OrderRepository {
	
	public TacoOrder save(TacoOrder order);

}
