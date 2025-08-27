package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class UseAi {
	public static Recipe makeRecipe(String data)	// ai가 준 string을 파싱해서 레시피 클래스 반환
	{
		Recipe recipe = new Recipe();
		String[] tdata = data.split("\\$");
		ArrayList<Ingredient> ing = new ArrayList<Ingredient>();
		ArrayList<String> r = new ArrayList<String>();
		recipe.setName(tdata[0]);
		for (int i = 1; i < tdata.length; i++)
		{
			char c = tdata[i].charAt(0);
			switch (c)
			{
			case '●':	// 재료
				String[] temp = tdata[i].split("▶");
				Ingredient ting = new Ingredient();
				ting.setName(temp[0].substring(1));
				ting.setCount(temp[1]);
				ing.add(ting);
				break;
			case '@':	// 제작법
				r.add(tdata[i].substring(1));
				break;
			case '#':	// 소요시간
				recipe.setTime(tdata[i].substring(1));
				break;
			case '○':	// 도구
				recipe.addTools(Tool.getCodeByToolList((tdata[i].substring(1))));
				break;
			}
		}
		recipe.setMainIngredients(ing);
		recipe.setRecipe(r);
		return recipe;
	}
	public static Ingredient makeIngredient(String data)
	{
		Ingredient ing = new Ingredient();
		String[] tdata = data.split("@");
		
		ing.setName(tdata[0]);
		ing.setDescription(tdata[1]);
		return ing;
	}
	public static void setTodayIngredient()
	{
		Ingredient.today = UseAi.makeIngredient(aiCallResponse.ingredientsCommenSense());
		return;
	}
	public static void setTodayMeal(String time)
	{
		Recipe.today = UseAi.makeRecipe(aiCallResponse.TodayMeal(time));
		return;
	}
}
