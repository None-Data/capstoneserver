package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class DataForGetRecipe {
	private Ingredient mainIngredients;	// 메인 재료
	private ArrayList<Ingredient> subIngredients;	// 서브 재료
	private int Banned;
	private int Tool;
	
	public Ingredient getMainIngredients() {
		return mainIngredients;
	}
	public void setMainIngredients(Ingredient mainIngredients) {
		this.mainIngredients = mainIngredients;
	}
	public ArrayList<Ingredient> getSubIngredients() {
		return subIngredients;
	}
	public void setSubIngredients(ArrayList<Ingredient> subIngredients) {
		this.subIngredients = subIngredients;
	}
	public int getBanned() {
		return Banned;
	}
	public void setBanned(int banned) {
		Banned = banned;
	}
	public int getTool() {
		return Tool;
	}
	public void setTool(int tool) {
		Tool = tool;
	}
	public String getSubIngList()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < subIngredients.size(); i++)
		{
			sb.append(subIngredients.get(i).getName() + ",");
		}
		return sb.toString();
	}
	
}
