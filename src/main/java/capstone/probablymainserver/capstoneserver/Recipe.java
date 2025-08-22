package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class Recipe {
	private int code = 0;	// 고유 코드. 기본적으로 0이며 코드를 부여받으면 바뀜
	private String author;
	private String name;	// 레시피 이름
	private ArrayList<String> recipe;	// 음식 제작 방법 1, 2, 3... 나열
	private Long tools;	// 필요 도구
	private int type;	// 음식의 종류 ex) 국, 후식 등
	private ArrayList<Ingredient> mainIngredients;	// 메인 재료
	private ArrayList<Ingredient> subIngredients;	// 서브 재료
	private String time;	// 소요 시간
	private int ai;	// ai가 만들었어?
	
	public static Recipe today;
	
	public int getCode()
	{
		return code;
	}
	public void setCode(int c)
	{
		code = c;
		return;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String s)
	{
		author = s;
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
	public int getType()
	{
		return type;
	}
	public void setType(int i)
	{
		type = i;
	}
	/*
	public int[] getIngredients()	// 메인, 서브 합쳐서 반환
	{
		int[] data = new int[mainIngredients.size() + subIngredients.size()];
		
		for (int i = 0; i < mainIngredients.size(); i++)
		{
			data[i] = mainIngredients.get(i);
		}
		for (int i = 0; i < subIngredients.size(); i++)
		{
			data[i+mainIngredients.size()] = subIngredients.get(i);
		}
		return data;
	}
	*/
	public ArrayList<Ingredient> getMainIngredients()	// 반환받은 배열 수정 금지.
	{
		return mainIngredients;
	}
	public ArrayList<Ingredient> getSubIngredients()	// 반환받은 배열 수정 금지
	{
		return subIngredients;
	}
	public void setMainIngredients(ArrayList<Ingredient> ing)
	{
		mainIngredients = ing;
		return;
	}
	public void setSubIngredients(ArrayList<Ingredient> ing)
	{
		subIngredients = ing;
		return;
	}
	public void addMainIngredients(Ingredient i)
	{
		if (mainIngredients == null) mainIngredients = new ArrayList<Ingredient>();
		mainIngredients.add(i);
		return;
	}
	public void addSubIngredients(Ingredient i)
	{
		if (subIngredients == null) subIngredients = new ArrayList<Ingredient>();
		subIngredients.add(i);
		return;
	}
	public boolean removeMainIngredients(Ingredient ing)
	{
		for (int i = 0; i < mainIngredients.size(); i++)
		{
			if (mainIngredients.get(i).getName().compareTo(ing.getName()) > 0)
			{
				if (mainIngredients.get(i).getCode() == ing.getCode())
				{
					mainIngredients.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	public boolean removeMainIngredients(String str)
	{
		for (int i = 0; i < mainIngredients.size(); i++)
		{
			if (mainIngredients.get(i).getName().compareTo(str) > 0)
			{
				mainIngredients.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean removeMainIngredients(int c)
	{
		for (int i = 0; i < mainIngredients.size(); i++)
		{
			if (mainIngredients.get(i).getCode() == c)
			{
				mainIngredients.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean removeSubIngredients(Ingredient ing)
	{
		for (int i = 0; i < subIngredients.size(); i++)
		{
			if (subIngredients.get(i).getName().compareTo(ing.getName()) > 0)
			{
				if (subIngredients.get(i).getCode() == ing.getCode())
				{
					subIngredients.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	public boolean removeSubIngredients(String str)
	{
		for (int i = 0; i < subIngredients.size(); i++)
		{
			if (subIngredients.get(i).getName().compareTo(str) > 0)
			{
				subIngredients.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean removeSubIngredients(int c)
	{
		for (int i = 0; i < subIngredients.size(); i++)
		{
			if (subIngredients.get(i).getCode() == c)
			{
				subIngredients.remove(i);
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> getRecipe()
	{
		return recipe;
	}
	public void setRecipe(ArrayList<String> data)
	{
		recipe = data;
	}
	public void addRecipe(String data)
	{
		recipe.add(data);
		return;
	}
	public Long getTools()
	{
		return tools;
	}
	public boolean setTools(Long l)
	{
		tools = l;
		return true;
	}
	public boolean addTools(Long l)
	{
		if (tools == null) tools = 0L;
		if ((tools & l) == l)
		{
			return false;
		}
		else
		{
			tools += l;
			return true;
		}
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
