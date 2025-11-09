package tacos.repository.impl;

import java.awt.Window.Type;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;
import tacos.repository.OrderRepository;

@Repository
public class JDBCOrderRepository implements OrderRepository {
	
	// the parent interface of the JdbcTemplate
	private JdbcOperations jdbcOperation;
	
	public JDBCOrderRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperation = jdbcOperations;
	}

	@Override
	public TacoOrder save(TacoOrder order) {
		
		
		// create prepared statement factory and add the insert statement to it
		PreparedStatementCreatorFactory prepStatementFactory = new PreparedStatementCreatorFactory
				("insert into Taco_Order (delivery_name, "
						+ "delivery_street, delivery_city, "
						+ "delivery_state, delivery_zip, "
						+ "cc_number, cc_expiration, "
						+ "cc_ccv, placed_at) values (?,?,?,?,?,?,?,?,?)",
						Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
						Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
						Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP);
		
		// set the return generated key to true
		prepStatementFactory.setReturnGeneratedKeys(true);
		
		// set the placed at date to the order
		order.setPlaceAt(new Date());
		
		// add the prepared statement parameters from the order values
		PreparedStatementCreator psc = prepStatementFactory.newPreparedStatementCreator(Arrays.asList(
				order.getDeliveryName(),
				order.getDeliveryStreet(),
				order.getDeliveryCity(),
				order.getDeliveryState(),
				order.getDeliveryZip(),
				order.getCcNumber(),
				order.getCcExpiration(),
				order.getCcCVV(),
				order.getPlaceAt()
				
				));
		
		// create key holder
		GeneratedKeyHolder gkh = new GeneratedKeyHolder();
		
		// call the update method
		jdbcOperation.update(psc,gkh);
		
		// get the generated id from the key holder
		long orderId = gkh.getKey().longValue();
		
		// set the generated id to the order
		order.setId(orderId);
		
		// save tacos
		List<Taco> tacos = order.getTacos();
		int i=0;
		for(Taco taco:tacos) {
			taco.setCreatedAt(new Date());
			saveTaco(orderId, i++, taco);
			
		}
		
		
		// return order
		return order;
	}
	
	
	private long saveTaco(Long orderId, int orderKey, Taco taco) {
		
		PreparedStatementCreatorFactory prepStatementCreatorFactory = new PreparedStatementCreatorFactory
				("insert into Taco (name, created_at, taco_order, taco_order_key) values (?,?,?,?)", 
						Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT);
		
		
		prepStatementCreatorFactory.setReturnGeneratedKeys(true);
		
		PreparedStatementCreator psc = prepStatementCreatorFactory.newPreparedStatementCreator(
				Arrays.asList(taco.getName(), taco.getCreatedAt(), orderId, orderKey));
		
		
		GeneratedKeyHolder gkh = new GeneratedKeyHolder();
		
		
		jdbcOperation.update(psc, gkh);
		
		long tacoId = gkh.getKey().longValue();
		
		taco.setId(tacoId);
		
		saveIngredient(tacoId, taco.getIngredients());
		
		return tacoId;
	}
	
	
	private void saveIngredient(long tacoId, List<Ingredient> ingredients) {
		
		int key=0;
		for(Ingredient ingrediet: ingredients) {
			jdbcOperation.update("insert into Ingredient_Ref "
					+ "(ingredient,taco,taco_key) values (?,?,?)",ingrediet.getId(), tacoId, key++);
		}
	}

}
