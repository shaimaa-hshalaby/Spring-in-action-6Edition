package tacos.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;
import tacos.repository.IngredientRepository;
import tacos.domain.Ingredient.Type;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
	
	private IngredientRepository ingredientRepository;
	
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {

		Iterable <Ingredient> ingredients = ingredientRepository.findAll();

		Type[] types = Ingredient.Type.values();
		for(Type type: types) {

			model.addAttribute(type.toString().toLowerCase(),
					filterIngredientsByType((List<Ingredient>) ingredients, type));
		}
	}
	

	  @ModelAttribute(name = "tacoOrder")
	  public TacoOrder order() {
	    return new TacoOrder();
	  }



	private List<Ingredient> filterIngredientsByType(List<Ingredient> ingredients,Type type){
		return ingredients.stream()
						  .filter(x -> x.getType().equals(type))
						  .collect(Collectors.toList());
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		model.addAttribute("taco", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processTaco(@Valid Taco taco,@ModelAttribute("tacoOrder") TacoOrder tacoOrder,Errors errors) {
		
		if(errors.hasErrors()) {
			return "design";
		}

		tacoOrder.addTaco(taco);
		
		log.info("Processing Taco: "+taco);
		return "redirect:/orders/current";
	}

}
