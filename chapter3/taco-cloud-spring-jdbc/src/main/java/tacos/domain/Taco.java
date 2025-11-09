package tacos.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {
	
	@Id
	private long id;
	
	private Date createdAt = new Date();

	@NotNull
	@Size(min=5 , message = "Name must be at least 5 characters long")
	private String name;
	
	@NotNull
	@Size(min=1, message="You must choose at least 1 ingrdient")
	private List<IngredientRef> ingredients;
}
