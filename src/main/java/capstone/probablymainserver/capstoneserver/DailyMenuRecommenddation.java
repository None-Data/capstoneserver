package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class DailyMenuRecommenddation {
	public ArrayList<Recipe> getRandomTodayMenus()
	{
		ArrayList<Recipe> data = new ArrayList<Recipe>();
		/* 원래 하단에 임의로 제작하는 레시피 배열은 ai에게 요청하고 받아야 합니다. 일단은 임시로 대충 넣음 */
		/*
		for (int i = 0; i < 5; i++)
		{
			data.add(new Recipe());
			data.get(i).setName("RecipeName"+i);
			data.get(i).setCode(0);
			data.get(i).setRecipe(new ArrayList<String>());
			data.get(i).recipe.add("Recipe_A"+i);
			data.get(i).recipe.add("Recipe_B"+i);
			data.get(i).setMainIngredients(new ArrayList<Ingredient>());
			data.get(i).mainIngredients.add(new Ingredient());
			data.get(i).mainIngredients.get(0).name = "Ing_"+i;
		}*/
		return data;
	}
}
