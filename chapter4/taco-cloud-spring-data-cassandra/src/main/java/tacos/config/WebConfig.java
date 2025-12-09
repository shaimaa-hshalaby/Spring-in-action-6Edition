package tacos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.repository.IngredientRepository;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
	
	
	@Bean 
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		
		return arg -> {
			repo.save(new Ingredient("FLTO","Flour Tortilla",Type.WRAP));
			repo.save(new Ingredient("COTO","CORN Tortilla",Type.WRAP));
			repo.save(new Ingredient("GRBF","Ground Beef",Type.PROTEIN));
			repo.save(new Ingredient("CARN","Carnitas",Type.PROTEIN));
			repo.save(new Ingredient("LETC","Lettuce",Type.VEGGIES));
			repo.save(new Ingredient("CHED","Cheddar",Type.CHEESE));
			repo.save(new Ingredient("JACK","Monterrey Jack",Type.CHEESE));
			repo.save(new Ingredient("SLSA","Salsa",Type.SAUCE));
			repo.save(new Ingredient("SRCR","Sour Cream",Type.SAUCE));
		};
		
	}
	
	
	

}
