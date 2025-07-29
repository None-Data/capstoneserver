package capstone.probablymainserver.capstoneserver;

import java.util.ArrayList;

public class Tool {
	// 17가지 도구
	final static String[] NAME = new String[] {"프라이팬", "냄비", "웍", "밀대", "믹서기", "핸드블랜더", "거품기", "연육기", "착즙기", "전자레인지", "가스레인지", "오븐", "에어프라이어", "주전자", "압력솥", "토스터", "찜기"};
	
	public static String name(Long code)	// 0, 1, 2, 3 ...에 따라 NAME 반환 -> 프라이팬, 냄비, 웍, 밀대...
	{
		return NAME[code.intValue()];
	}
	public static String nameByBit(Long code)	// 비트기반으로 NAME 반환
	{
		int t = code.intValue() / 2;
		return NAME[t];
	}
	public static Long code(String str)	// 해당하는 도구의 인덱스 반환 (-1은 검색 실패)
	{
		for (int i = 0; i < 17; i++)
		{
			if (str.compareTo(NAME[i]) == 0)
			{
				return (long)i;
			}
		}
		return (long)-1;
	}
	public static Long codeByBit(String str)	// 해당하는 도구의 코드 반환
	{
		Long t = (long)-1;
		for (int i = 0; i < 17; i++)
		{
			if (str.compareTo(NAME[i]) == 0)
			{
				t = (long)i;
				break;
			}
		}
		if (t == -1) return t;
		else
		{
			return (long)Math.pow(2, t);
		}
	}
	public static String getToolListByCodes(long code)
	{
		ArrayList<String> tools = new ArrayList<>();
		
		for (int i = 0; i < NAME.length; i++)
		{
			long mask = 1L << i;
			if ((code & mask) != 0)
			{
				tools.add(NAME[i]);
			}
		}
		return String.join(",", tools);
	}
	public static long getCodeByToolList(String toolList) {
	    long code = 0L;

	    // 문자열을 쉼표 기준으로 나눔
	    String[] tools = toolList.split(",");

	    for (String tool : tools) {
	        tool = tool.trim(); // 공백 제거
	        long bitCode = codeByBit(tool);
	        if (bitCode != -1) {
	            code |= bitCode; // 비트 OR 연산으로 누적
	        }
	    }

	    return code;
	}

}
