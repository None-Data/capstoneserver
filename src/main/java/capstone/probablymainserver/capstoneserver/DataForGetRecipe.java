package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class DataForGetRecipe {
	private Long userID = 0L;
	private String password = "";
	private ArrayList<Ingredient> mainIngredients;	// 메인 재료
	private ArrayList<Ingredient> subIngredients;	// 서브 재료
	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Ingredient> getMainIngredients() {
		return mainIngredients;
	}
	public void setMainIngredients(ArrayList<Ingredient> mainIngredients) {
		this.mainIngredients = mainIngredients;
	}
	public ArrayList<Ingredient> getSubIngredients() {
		return subIngredients;
	}
	public void setSubIngredients(ArrayList<Ingredient> subIngredients) {
		this.subIngredients = subIngredients;
	}
}
