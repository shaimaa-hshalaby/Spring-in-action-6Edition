package tacos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import tacos.domain.Ingredient;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	


}
