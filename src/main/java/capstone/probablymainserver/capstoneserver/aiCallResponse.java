package capstone.probablymainserver.capstoneserver;

import org.springframework.stereotype.Service;

@Service
public class aiCallResponse {
	public static String Meal(String Ing, String Banned, String Tools) {//메인요리 레시피 추천 프롬프트
    	
    	String ingredients = Ing;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	
    	String prompt = "내가 재료를 말하면 그 재료만 활용해서 만들 수 있는 음식 레시피를 추천해줘. 이 재료 외에는 사용하지 말아줘.: " + ingredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
                + "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n두부 감자 볶음$●두부▶1모$●감자▶2개$●양파▶1개$●대파▶1대$●고추장▶2스푼$●간장▶1스푼$●다진 마늘▶1스푼$●설탕▶1스푼$●참기름▶1스푼$@두부는 먼저 물기를 제거하고 2cm 정도의 크기로 썰어주세요.$@감자와 양파는 굵은 사이즈로 썰고, 대파는 어슷하게 잘라두세요.$@팬에 참기름을 두르고 두부를 볶다가 감자와 양파를 넣어 다같이 볶아줍니다.$@감자가 반쯤 익었을 때 고추장, 간장, 설탕, 다진 마늘을 넣고 잘 섞어 볶아줍니다.$@볶다가 감자가 완전히 익으면 대파를 넣고 살짝만 더 볶아 주면 완성입니다.$#약 20분";
        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
}
