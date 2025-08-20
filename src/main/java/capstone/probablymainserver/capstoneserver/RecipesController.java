package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

record DBRecipeSearchRequest(List<String> ing) {}

@RestController
@RequestMapping("/api/recipes") 
public class RecipesController {
	
	@PostMapping("/searchFromDB")
	public ResponseEntity<List<Recipe>> searchFromDB(@RequestBody DBRecipeSearchRequest req)
	{
		System.out.println("[Log] recipes/searchFromDB commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
        return ResponseEntity.ok(capstone.RecipeListSearchFromDB(req.ing(), uid));
	}

	@PostMapping("/searchFromAI")
	public ResponseEntity<List<Recipe>> searchFromAI(@RequestBody DataForGetRecipe dfgr)
	{
		System.out.println("[Log] recipes/searchFromAI commanded");
		
		String Banned = BannedIng.getBannedList(dfgr.getBanned()).toString();
		String ToolsHave = Tool.getToolListByCodes(dfgr.getTool());
		
		List<Recipe> recipeList = new ArrayList<>();
	    String aiResponse;
	    if (dfgr.getMainIngredients() != null)
	    {
	    	if (dfgr.getType() == 2) aiResponse = aiCallResponse.DessertByMain(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), Banned, ToolsHave);
	    	else aiResponse = aiCallResponse.MealByMain(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    else
	    {
	    	if (dfgr.getType() == 2) aiResponse = aiCallResponse.Dessert(dfgr.getSubIngList(), Banned, ToolsHave);
	    	else aiResponse = aiCallResponse.Meal(dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    
	    for (String line : aiResponse.split("\n"))
	    {
	    	Recipe r = UseAi.makeRecipe(line);
	    	r.setType(dfgr.getType());
	    	recipeList.add(r);
	    }
	    return ResponseEntity.ok(recipeList);
	}
	@PostMapping("/searchFromAIAddAllow")
	public ResponseEntity<List<Recipe>> searchFromAIAddAllow(@RequestBody DataForGetRecipe dfgr)
	{
		System.out.println("[Log] recipes/searchFromAI commanded");
		
		String Banned = BannedIng.getBannedList(dfgr.getBanned()).toString();
		String ToolsHave = Tool.getToolListByCodes(dfgr.getTool());
		
		List<Recipe> recipeList = new ArrayList<>();
	    String aiResponse;
	    if (dfgr.getMainIngredients() != null)
	    {
	    	if (dfgr.getType() == 2) aiResponse = aiCallResponse.DessertByMain_AddAllow(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), Banned, ToolsHave);
	    	else aiResponse = aiCallResponse.MealByMain_AddAllow(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    else
	    {
	    	if (dfgr.getType() == 2) aiResponse = aiCallResponse.Dessert_AddAllow(dfgr.getSubIngList(), Banned, ToolsHave);
	    	else aiResponse = aiCallResponse.Meal_AddAllow(dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    
	    for (String line : aiResponse.split("\n"))
	    {
	    	Recipe r = UseAi.makeRecipe(line);
	    	r.setType(dfgr.getType());
	    	recipeList.add(r);
	    }
	    return ResponseEntity.ok(recipeList);
	}
	@PostMapping("/like")
	public ResponseEntity<Integer> like(@RequestBody Recipe recipe)
	{
		System.out.println("[Log} recipes/like commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
        return ResponseEntity.ok(capstone.RecipeAddInAILike(recipe, uid));
	}

	@DeleteMapping("/like")
	public ResponseEntity<Integer> likeDelete(@RequestParam("recipeCode") int rcode)
	{
		System.out.println("[Log} recipes/delike commanded");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		return ResponseEntity.ok(capstone.RecipeDeleteInAILike(rcode, uid));
	}

	@GetMapping("/like")
	public ResponseEntity<List<Recipe>> liked()
	{
		System.out.println("[Log] recipes/liked commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
		
		return ResponseEntity.ok(capstone.showRecipeInAILike(uid));
	}

	@GetMapping("/added") 
	public ResponseEntity<List<Recipe>> getRecipeAddedByUser()
	{
		System.out.println("[Log] recipes/addedList commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		return ResponseEntity.ok(capstone.showRecipeInDB(uid));
	}

	@PostMapping("/added") 
	public ResponseEntity<Integer> addRecipe(@RequestBody Recipe data)
	{
		System.out.println("[Log] recipes/add commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
        return ResponseEntity.ok(capstone.RecipeAddInDB(data, uid));
	}

	@PutMapping("/added") 
	public ResponseEntity<Integer> updateRecipe(@RequestBody Recipe data) 
	{
		System.out.println("[Log] recipes/update commanded");
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
		
        
		return ResponseEntity.ok(capstone.RecipeUpdateInDB(data, data.getCode()));
	}

	@DeleteMapping("/added") 
	public ResponseEntity<Integer> deleteRecipe(@RequestParam("recipeCode") int rcode)
	{
		System.out.println("[Log] recipes/delete commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		return ResponseEntity.ok(capstone.RecipeDeleteInDB(rcode, uid));
	}

	@GetMapping("/recipetoday") 
	public ResponseEntity<Recipe> recipeToday()
	{
		System.out.println("[Log] recipes/recipeToday commanded");
		
		return ResponseEntity.ok(Recipe.today);
	}
    
	@GetMapping("/Ingredienttoday")
	public ResponseEntity<Ingredient> ingredientToday()
	{
		System.out.println("[Log] recipes/ingredientToday commanded");

		return ResponseEntity.ok(Ingredient.today);
	}
}