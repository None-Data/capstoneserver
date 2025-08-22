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
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String MealByMain(String mainIng, String subIng, String Banned, String Tools) {//메인요리 레시피 추천 프롬프트
    	
    	String subIngredients = subIng;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	String mainIngredients = mainIng;
    	
    	String prompt = "내가 재료를 말하면 그 재료만 활용해서 만들 수 있는 음식 레시피를 추천해줘. 이 재료 외에는 사용하지 말아줘.: " + subIngredients
    			+ "\n이 재료는 반드시 사용해줘.: " + mainIngredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String Meal_AddAllow(String Ing, String Banned, String Tools) {//메인요리 레시피 추천 프롬프트
    	
    	String ingredients = Ing;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	
    	String prompt = "내가 재료를 말하면 그 재료를 활용해서 만들 수 있는 음식 레시피를 추천해줘. 필요하다면 다른 재료도 써도 됨: " + ingredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
                + "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String MealByMain_AddAllow(String mainIng, String subIng, String Banned, String Tools) {//메인요리 레시피 추천 프롬프트
    	
    	String subIngredients = subIng;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	String mainIngredients = mainIng;
    	
    	String prompt = "내가 재료를 말하면 그 재료를 활용해서 만들 수 있는 음식 레시피를 추천해줘. 필요하다면 다른 재료도 써도 됨: " + subIngredients
    			+ "\n이 재료는 반드시 사용해줘.: " + mainIngredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
public static String Dessert(String Ing, String Banned, String Tools) {//디저트 레시피 추천 프롬프트
    	
    	String ingredients = Ing;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	
    	String prompt = "내가 재료를 말하면 그 재료만 활용해서 만들 수 있는 음식 레시피를 추천해줘. 이 재료 외에는 사용하지 말아줘.: " + ingredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n이 음식은 디저트로 한정할게."
                + "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String DessertByMain(String mainIng, String subIng, String Banned, String Tools) {//디저트 레시피 추천 프롬프트
    	
    	String subIngredients = subIng;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	String mainIngredients = mainIng;
    	
    	String prompt = "내가 재료를 말하면 그 재료만 활용해서 만들 수 있는 음식 레시피를 추천해줘. 이 재료 외에는 사용하지 말아줘.: " + subIngredients
    			+ "\n이 재료는 반드시 사용해줘.: " + mainIngredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n이 음식은 디저트로 한정할게."
    			+ "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String Dessert_AddAllow(String Ing, String Banned, String Tools) {//디저트 레시피 추천 프롬프트
    	
    	String ingredients = Ing;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	
    	String prompt = "내가 재료를 말하면 그 재료를 활용해서 만들 수 있는 음식 레시피를 추천해줘. 필요하다면 다른 재료도 써도 됨: " + ingredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n이 음식은 디저트로 한정할게."
                + "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String DessertByMain_AddAllow(String mainIng, String subIng, String Banned, String Tools) {//디저트 레시피 추천 프롬프트
    	
    	String subIngredients = subIng;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	String mainIngredients = mainIng;
    	
    	String prompt = "내가 재료를 말하면 그 재료를 활용해서 만들 수 있는 음식 레시피를 추천해줘. 필요하다면 다른 재료도 써도 됨: " + subIngredients
    			+ "\n이 재료는 반드시 사용해줘.: " + mainIngredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
    			+ "\n이 음식은 디저트로 한정할게."
    			+ "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
    	        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
	public static String ingredientsCommenSense() { //랜덤 식재료 상식 프롬프트
		String prompt = "랜덤으로 하나의 식재료에 대한 정보를 알려줘"
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 한줄로 출력해줘. \n고구마@고구마는 강력한 항산화 기능을 가진 식재료로 칼로리가 낮아 다이어트 식품으로 좋다. 고구마는 비타민A, 비타민C 그리고 식이섬유가 풍부하므로 소화기능을 돕고, 안구건조증과 백내장 예방에도 효과적이다. 특히, 고구마에는 '다이어트 폴리페놀'이라는 물질이 들어 있어 혈당 조절과 비만 및 당뇨병 예방에 도움이 된다. 또한, 면역력을 높이고 피로 회복에도 뛰어나다.";
		
		String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
	}
	/*
	public static String ingredientsIntroduce(String ingredients) { //지정 식재료 상식 프롬프트
		String prompt = ingredients + "에 대한 정보를 알려줘"
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 한줄로 출력해줘. \n고구마@고구마는 강력한 항산화 기능을 가진 식재료로 칼로리가 낮아 다이어트 식품으로 좋다. 고구마는 비타민A, 비타민C 그리고 식이섬유가 풍부하므로 소화기능을 돕고, 안구건조증과 백내장 예방에도 효과적이다. 특히, 고구마에는 '다이어트 폴리페놀'이라는 물질이 들어 있어 혈당 조절과 비만 및 당뇨병 예방에 도움이 된다. 또한, 면역력을 높이고 피로 회복에도 뛰어나다.";
		
		String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
	}
	*/
	public static String TodayMeal(String Time) {//오늘의 요리 레시피 랜덤 추천 프롬프트
    	
    	String prompt = Time + "에 먹을 음식 레시피를 알려줘"
    			+ "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피를 한줄로 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n양배추피자$●양배추▶5장$●양파▶1개$●당근▶1개$●느타리버섯▶1묶음$●피자치즈▶200g$●소금▶약간$●후추▶약간$@양파와 당근, 느타리버섯을 잘게 채썰어주세요.$@프라이팬에 흰양배추 장을 한장 넣고 채썰어 놓은 재료들과 소금, 후추를 넣어주세요.$@마지막으로 피자치즈를 올려 180도 오븐에서 10분간 구워주세요.$#약 20분$○프라이팬,오븐";
        
    	String recipe = getFromAI.getFromAI(prompt);
    	
    	return recipe;
    }
}
