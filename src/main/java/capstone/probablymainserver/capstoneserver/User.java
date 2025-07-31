package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class User {
	private int uid;	// 유저 고유 아이디(코드)
	private String name;	// 유저 이름
	private String userId;	// 유저 아이디
	private String password;	// 유저 비번
	private Long tools;	// 보유 도구들
	private ArrayList<Ingredient> ingredients;	// 보유 식재료
	private ArrayList<Recipe> Recipes;	// 저장된 레시피
	private Long banned;	// 금지 식재료
	
	
	public void setUid(int id)
	{
		uid = id;
		return;
	}
	public int getUid()
	{
		return uid;
	}
	public String getName()	
	{
		return name;
	}
	public void setName(String n)
	{
		name = n;
		return;
	}
	public boolean addTools(int l)
	{
		if ((tools & l) == l)
		{
			return false;
		}
		else tools += l;
		return true;
	}
	public boolean removeTools(int l)
	{
		if ((tools & l) == l)
		{
			tools -= l;
			return true;
		}
		else return false;
	}
	public ArrayList<Ingredient> getIngredients()
	{
		return ingredients;
	}
	public void setIngredients(ArrayList<Ingredient> data)
	{
		ingredients = data;
		return;
	}
	public void addIngredients(Ingredient i)
	{
		ingredients.add(i);
		return;
	}
	public void removeIngredients(Ingredient i)
	{
		ingredients.remove(i);
	}
	public boolean addBanned(int l)
	{
		if ((banned & l) == l)
		{
			return false;
		}
		else banned += l;
		return true;
	}
	public boolean removeBanned(int l)
	{
		if ((banned & l) == l)
		{
			banned -= l;
			return true;
		}
		else return false;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getTools() {
		return tools;
	}
	public void setTools(Long tools) {
		this.tools = tools;
	}
	public ArrayList<Recipe> getRecipes() {
		return Recipes;
	}
	public void setRecipes(ArrayList<Recipe> recipes) {
		Recipes = recipes;
	}
	public Long getBanned() {
		return banned;
	}
	public void setBanned(Long banned) {
		this.banned = banned;
	}
	
}
