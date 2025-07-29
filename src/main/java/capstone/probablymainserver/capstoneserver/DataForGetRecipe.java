package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class DataForGetRecipe {
	private String userID;
	private String password;
	private Ingredient mainIngredients;	// 메인 재료
	private ArrayList<Ingredient> subIngredients;	// 서브 재료
	private Long Banned;
	private Long Tool;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public Long getBanned() {
		return Banned;
	}
	public void setBanned(Long banned) {
		Banned = banned;
	}
	public Long getTool() {
		return Tool;
	}
	public void setTool(Long tool) {
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
