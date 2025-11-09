package tacos.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.domain.Ingredient;
import tacos.repository.IngredientRepository;

@Repository
public class JDBCIngredientRepository implements IngredientRepository {
	
	private JdbcTemplate jdbcTemplat;
	
	public JDBCIngredientRepository(JdbcTemplate jdbcTemplat) {
		this.jdbcTemplat = jdbcTemplat;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		
		List<Ingredient> result = jdbcTemplat.query("select id, name, type from Ingredient", this::mapRowToIngredient);
		return result;
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		List<Ingredient> result = jdbcTemplat.query("select id, name, type from Ingredient where id =?", this::mapRowToIngredient,id);
	
		return result.size()== 0 ? Optional.empty(): Optional.of(result.get(0)) ;
	}

	@Override
	public Ingredient save(Ingredient ingredient) {	
		jdbcTemplat.update("insert into ingredient (id, name, type ) values (?,?,?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
	
	
	private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
		
		return new Ingredient(row.getString("id"),
							  row.getString("name"), 
							  Ingredient.Type.valueOf(row.getString("type")));
		
	}

}
