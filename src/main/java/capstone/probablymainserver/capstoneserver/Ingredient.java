package capstone.probablymainserver.capstoneserver;

public class Ingredient extends Item {
	private int type = 0;
	private String count;
	private static final String[] TYPE = {"미분류", "육류", "채소", "과일", "유제품", "가공식품", "기타"};
	
	public int getType()
	{
		return type;
	}
	public String getTypeName()
	{
		return TYPE[type];
	}
	public void setType(int c)
	{
		type = c;
	}
	public void setCount(String s)
	{
		count = s;
	}
	public String getCount()
	{
		return count;
	}
}
