package capstone.probablymainserver.capstoneserver;
/*
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api")
public class MyController {
	
	private final RecipeSearch recipeSearch;
	
	public MyController(RecipeSearch recipeSearch)
	{
		this.recipeSearch = recipeSearch;
	}
	
	@PostMapping("/getRecipe")
	public ResponseEntity<List<Recipe>> getRecipe(@RequestBody DataForGetRecipe data)
	{
		System.out.println("[LOG] getRecipe commanded");
		
		
		List<Recipe> recipeList = new ArrayList<>();
		
		StringBuilder main = new StringBuilder();
	    for (Ingredient i : data.getMainIngredients())
	    {
	        main.append(i.getName()).append(", ");
	    }
	    
	    String aiResponse = aiCallResponse.Meal(main.toString(), "NULL", "프라이팬");
	    
	    for (String line : aiResponse.split("\n"))
	    {
	    	Recipe r = UseAi.makeRecipe(line);
	    	
	    	recipeList.add(r);
	    }
	    return ResponseEntity.ok(recipeList);
	}
	
	@GetMapping("/getRecipe3")
	public ResponseEntity<List<Recipe>> getRecipe3()
	{
		System.out.println("[LOG] getRecipe3 commanded");
		return ResponseEntity.ok(recipeSearch.getMealRecipe(null));
	}
	@GetMapping("/getTest")
	public ResponseEntity<List<Recipe>> getTest()
	{
		System.out.println("[LOG] getTest commanded");
		List<Recipe> data = new ArrayList<Recipe>();

		ObjectMapper objMpr = new ObjectMapper();
		String strdata = aiCallResponse.Meal("김치, 참치, 밥, 참기름, 버터, 고추장, 우유", "새우", "프라이팬, 냄비");
		for(String s : strdata.split("\n"))
		{
			System.out.println(s);
			Recipe r = UseAi.makeRecipe(s);
			try
			{
				System.out.println(objMpr.writerWithDefaultPrettyPrinter().writeValueAsString(r));
			}
			catch (JsonProcessingException e)
			{
				e.printStackTrace();
			}
			data.add(r);
		}
		return ResponseEntity.ok(data);
	}
}*/
