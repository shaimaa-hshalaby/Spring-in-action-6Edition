package tacos.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;



@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
	
	@Max(value = 20, message = "the page size should be between 5 and 20")
	@Min(value = 5, message = "the page size should be between 5 and 20")
	private int pageSize =20;
	
	private String defaultNote;

}
