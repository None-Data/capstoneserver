package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class RecipeSearch {
	private ArrayList<Ingredient> mainIngredient;
	private ArrayList<Ingredient> subIngredient;
	
	/*------------*/
	/* get Recipe */
	/*------------*/
	public ArrayList<Recipe> getMealRecipe(Long userID)
	{
		ArrayList<Recipe> data = new ArrayList<Recipe>();
		/* 해당 코드 라인에는 ai에게 레시피를 3개 요청하고 받은 string을 makeRecipe를 통해 파싱하고 데이터화 합니다 */
		/* 다만 지금은 임시로 임의의 값을 넣겠습니다 */
		
		for (int i = 1; i < 4; i++)
		{
			Recipe r = new Recipe();
			r.setCode(i);
			r.setName(i + "번 레시피");
			r.setAuthor(i + "번 작성자");
			r.setTools(0L);
			r.setType(0);
			r.setTime(i+"분");
			ArrayList<String> als = new ArrayList<String>();
			als.add(i + "번 레시피의 첫 번째 제작법");
			als.add(i + "번 레시피의 두 번째 제작법");
			r.setRecipe(als);
			ArrayList<Ingredient> m = new ArrayList<Ingredient>();
			ArrayList<Ingredient> s = new ArrayList<Ingredient>();
			Ingredient ing1 = new Ingredient();
			ing1.setName("메인 재료");
			Ingredient ing2 = new Ingredient();
			ing2.setName("서브 재료");
			m.add(ing1);
			s.add(ing2);
			r.setMainIngredients(m);
			r.setSubIngredients(s);
			data.add(r);
		}
		
		return data;
	}
	public ArrayList<Recipe> getDesertRecipe(Long userID)
	{
		ArrayList<Recipe> data = new ArrayList<Recipe>();
		/* 해당 코드 라인에는 ai에게 레시피를 3개 요청하고 받은 string을 makeRecipe를 통해 파싱하고 데이터화 합니다 */
		
		return data;
	}

	/*---------------*/
	/* getter setter */
	/*---------------*/
	public ArrayList<Ingredient> getMainIngredient() {
		return mainIngredient;
	}
	public void setMainIngredient(ArrayList<Ingredient> mainIngredient) {
		this.mainIngredient = mainIngredient;
	}
	public ArrayList<Ingredient> getSubIngredient() {
		return subIngredient;
	}
	public void setSubIngredient(ArrayList<Ingredient> subIngredient) {
		this.subIngredient = subIngredient;
	}
	
}
