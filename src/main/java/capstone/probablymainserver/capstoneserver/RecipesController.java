package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

record DBRecipeSearchRequest(List<String> ing) {}

@RestController
@RequestMapping("/recipes")
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
		
		// 받은 데이터(유저, 재료)에 기반 레시피 ai 검색 후 출력
		
		// 알러지 등 금지 목록 지정
		String Banned = BannedIng.getBannedList(dfgr.getBanned()).toString();
		String ToolsHave = Tool.getToolListByCodes(dfgr.getTool());
		// AI 체크
		
		List<Recipe> recipeList = new ArrayList<>();
	    String aiResponse;
	    if (dfgr.getMainIngredients() != null)
	    {
	    	aiResponse = aiCallResponse.MealByMain(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    else
	    {
	    	aiResponse = aiCallResponse.Meal(dfgr.getSubIngList(), Banned, ToolsHave);
	    }
	    
	    for (String line : aiResponse.split("\n"))
	    {
	    	Recipe r = UseAi.makeRecipe(line);
	    	
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
		// 해당 레시피의 코드를 유저의 찜 목록에 등록하기 (검사 필수)
	}
	@DeleteMapping("/like")
	public ResponseEntity<Integer> likeDelete(@RequestParam("recipeCode") int rcode)
	{
		System.out.println("[Log} recipes/delike commanded");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		return ResponseEntity.ok(capstone.RecipeDeleteInAILike(rcode, uid));
		// postID에 해당하는 찜 목록의 레시피 찜 취소
	}
	@GetMapping("/like")
	public ResponseEntity<List<Recipe>> liked()
	{
		System.out.println("[Log] recipes/liked commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
		
		// 해당 유저가 찜한 모든 레시피 출력
		return ResponseEntity.ok(capstone.showRecipeInAILike(uid));
	}
	@GetMapping("added")
	public ResponseEntity<List<Recipe>> getRecipeAddedByUser()
	{
		System.out.println("[Log] recipes/addedList commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		// 해당 유저가 등록한 레시피 목록 출력
		return ResponseEntity.ok(capstone.showRecipeInDB(uid));
	}
	@PostMapping("added")
	public ResponseEntity<Integer> addRecipe(@RequestBody Recipe data)
	{
		System.out.println("[Log] recipes/add commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
        return ResponseEntity.ok(capstone.RecipeAddInDB(data, uid));
		// 해당 레시피를 등록.
	}
	@PutMapping("added")
	public ResponseEntity<Integer> patchRecipe(@RequestBody Recipe data)
	{
		System.out.println("[Log] recipes/update commanded");
		
		return ResponseEntity.ok(capstone.RecipeUpdateInDB(data, data.getCode()));
		
		// 해당 레시피를 수정
	}
	@DeleteMapping("added")
	public ResponseEntity<Integer> deleteRecipe(@RequestParam("recipeCode") int rcode)
	{
		System.out.println("[Log] recipes/delete commanded");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int uid = (int) auth.getPrincipal();
        
		return ResponseEntity.ok(capstone.RecipeDeleteInDB(rcode, uid));
		// 해당 레시피 삭제
	}
	@GetMapping("recipetoday")
	public ResponseEntity<Recipe> recipeToday()
	{
		System.out.println("[Log] recipes/recipeToday commanded");
		
		// 미완
		return null;
	}
	@GetMapping("Ingredienttoday")
	public ResponseEntity<Ingredient> ingredientToday()
	{
		System.out.println("[Log] recipes/ingredientToday commanded");

		return ResponseEntity.ok(Ingredient.today);
	}
}
