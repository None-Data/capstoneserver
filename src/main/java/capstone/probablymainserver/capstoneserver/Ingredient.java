package capstone.probablymainserver.capstoneserver;

public class Ingredient extends Item {
	private int type = 0;
	private String count;
	private static final String[] TYPE = {"미분류", "육류", "해산물", "채소", "과일", "향신료", "축산물", "가공식품"};
	public static Ingredient today;
	
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
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Ingredient that = (Ingredient) o;
	    // 재료의 이름(name)이 같으면 같은 객체로 판단
	    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
	}

	@Override
	public int hashCode() {
	    return getName() != null ? getName().hashCode() : 0;
	}
}
