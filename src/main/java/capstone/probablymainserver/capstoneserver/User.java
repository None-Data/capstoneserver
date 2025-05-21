package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class User {
	private String name;	// 유저 이름
	private Long tools;	// 보유 도구들
	private ArrayList<Ingredient> ingredients;	// 보유 식재료
	private ArrayList<Integer> uploadedRecipe;	// 저장된 레시피 (코드가 있는)
	private ArrayList<Recipe> storedRecipe;		// 저장된 레시피 (코드가 없는)
	private Long banned;	// 금지 식재료
	
	public String getName()	
	{
		return name;
	}
	public void setName(String n)
	{
		name = n;
		return;
	}
	public boolean setTools(Long l)
	{
		tools = l;
		return true;
	}
	public Long getTools()
	{
		return tools;
	}
	public boolean addTools(Long l)
	{
		if ((tools & l) == l)
		{
			return false;
		}
		else tools += l;
		return true;
	}
	public boolean removeTools(Long l)
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
	public int[] getUploadedRecipe()
	{
		int[] data = new int[uploadedRecipe.size()];
		for (int i = 0; i < uploadedRecipe.size(); i++)
		{
			data[i] = uploadedRecipe.get(i);
		}
		return data;
	}
	public void addUploadedRecipe(int data)
	{
		uploadedRecipe.add(data);
		return;
	}
	public void removeUploadedRecipe(int data)
	{
		uploadedRecipe.remove(Integer.valueOf(data));
		return;
	}
	public ArrayList<Recipe> getStoredRecipe()
	{
		return storedRecipe;
	}
	public void addStoredRecipe(Recipe data)
	{
		storedRecipe.add(data);
	}
	public void removeStoredRecipe(int index)
	{
		storedRecipe.remove(index);
		return;
	}
	public void removeStoredRecipe(Recipe data)
	{
		storedRecipe.remove(data);
		return;
	}
	public boolean setBanned(Long l)
	{
		banned = l;
		return true;
	}
	public Long getBanned()
	{
		return banned;
	}
	public boolean addBanned(Long l)
	{
		if ((banned & l) == l)
		{
			return false;
		}
		else banned += l;
		return true;
	}
	public boolean removeBanned(Long l)
	{
		if ((banned & l) == l)
		{
			banned -= l;
			return true;
		}
		else return false;
	}
}
