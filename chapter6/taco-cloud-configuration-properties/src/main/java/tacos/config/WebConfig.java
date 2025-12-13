package tacos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.User;
import tacos.repository.IngredientRepository;
import tacos.repository.UserRepository;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("registration");
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


	@Bean 
	@Profile("!prod")
	public CommandLineRunner adminsLoader(UserRepository repo, PasswordEncoder encoder) {

		return arg -> {
			repo.save(new User("Hamza_admin",
					encoder.encode("password"),
					"Hamza Admin",
					"",
					"",
					"",
					"",
					"",
					new String[]{"ROLE_USER","ROLE_ADMIN"})
					);
			repo.save(new User("Hamza_user",
					encoder.encode("password"),
					"Hamza Admin",
					"",
					"",
					"",
					"",
					"",
					new String[]{"ROLE_USER"})
					);

		};
		
	}
	
	@Bean 
	@Profile("prod")
	public CommandLineRunner productionUsersLoader(UserRepository repo, PasswordEncoder encoder) {

		return arg -> {
			repo.save(new User("Hamza_prod",
					encoder.encode("password"),
					"Hamza Admin",
					"",
					"",
					"",
					"",
					"",
					new String[]{"ROLE_USER","ROLE_ADMIN"})
					);



		};

	}




}
