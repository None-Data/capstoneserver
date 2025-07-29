package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
	
	@PostMapping("/searchFromDB")
	public ResponseEntity<List<Recipe>> searchFromDB(@RequestBody DataForGetRecipe dfgr)
	{
		System.out.println("[Log] recipes/searchFromDB commanded");
		
		
		
		// DB에서 해당하는 데이터의 레시피 목록 추출후 제공
		return null;
	}
	@PostMapping("/searchFromAI")
	public ResponseEntity<List<Recipe>> searchFromAI(@RequestBody DataForGetRecipe dfgr)
	{
		System.out.println("[Log] recipes/searchFromAI commanded");
		
		// 받은 데이터(유저, 재료)에 기반 레시피 ai 검색 후 출력
		
		// 알러지 등 금지 목록 지정
		String BannedIng = "NULL";
		String ToolsHave = Tool.getToolListByCodes(dfgr.getTool());
		// AI 체크
		
		List<Recipe> recipeList = new ArrayList<>();
	    String aiResponse;
	    if (dfgr.getMainIngredients() != null)
	    {
	    	aiResponse = aiCallResponse.MealByMain(dfgr.getMainIngredients().getName(), dfgr.getSubIngList(), BannedIng, ToolsHave);
	    }
	    else
	    {
	    	aiResponse = aiCallResponse.Meal(dfgr.getSubIngList(), BannedIng, ToolsHave);
	    }
	    
	    for (String line : aiResponse.split("\n"))
	    {
	    	Recipe r = UseAi.makeRecipe(line);
	    	
	    	recipeList.add(r);
	    }
	    return ResponseEntity.ok(recipeList);
	}
	@PostMapping("/like")
	public ResponseEntity<Integer> like(@RequestParam("UserID") long userID, @RequestParam("RecipeCode") long recipeCode)
	{
		System.out.println("[Log} recipes/like commanded");
		
		// 해당 레시피의 코드를 유저의 찜 목록에 등록하기 (검사 필수)
		return null;
	}
	@DeleteMapping("/like/{id}")
	public ResponseEntity<Integer> likeDelete(@PathVariable("recipeCode") long recipeCode, @RequestParam("userID") long userID)
	{
		System.out.println("[Log} recipes/like{id}(likeDelete) commanded");
		
		// postID에 해당하는 찜 목록의 레시피 찜 취소
		return null;
	}
	@GetMapping("/liked")
	public ResponseEntity<List<Recipe>> liked(@RequestParam("userID") long userID)
	{
		System.out.println("[Log] recipes/liked commanded");
		
		// 해당 유저가 찜한 모든 레시피 출력
		return null;
	}
	@GetMapping("added")
	public ResponseEntity<List<Recipe>> getRecipeAddedByUser(@RequestBody long userID)
	{
		System.out.println("[Log] recipes/added(GetRecipeAddedByUser) commanded");
		
		// 해당 유저가 등록한 레시피 목록 출력
		return null;
	}
	@PostMapping("added")
	public ResponseEntity<Long> addRecipe(@RequestBody Recipe data)
	{
		System.out.println("[Log] recipes/added(addRecipe) commanded");
		
		// 해당 레시피를 등록. author를 이용하여 해당 유저 id에 연결?
		
		return null;
	}
	@PutMapping("added/{id}")
	public ResponseEntity<Integer> patchRecipe(@PathVariable("id") long id, @RequestBody Recipe data)
	{
		System.out.println("[Log] recipes/added{id}(patchRecipe) commanded");
		
		// 해당 레시피를 수정
		return null;
	}
	@DeleteMapping("added/{id}")
	public ResponseEntity<Integer> deleteRecipe(@PathVariable("id") long id)
	{
		System.out.println("[Log] recipes/added{id}(deleteRecipe) commanded");
		
		// 해당 레시피 삭제
		return null;
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
		
		// 미완
		return null;
	}
}
