package tacos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.Data;

@Data
public class Ingredient implements Persistable<String> {
	
	@Id
	private final String id;
	private final String name;
	private final Type type;
	
	@Transient
	  private boolean isNew = true; 
	
	
	public enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}


	@Override
	public boolean isNew() {
		
		return isNew;
	}

}
