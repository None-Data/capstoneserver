package capstone.probablymainserver.capstoneserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RecipeAIHelper {//RecipeAIHelper.getRecipeFromAI(prompt)를 호출하면 기능을 사용할 수 있다.

    private static final String API_KEY = "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";
    private static final int MAX_TOKENS = 4096;
    
    
    public static void main(String[] args) {
    	String a = subIngredientsTemporaryEntry();
    	String d = mainIngredientsTemporaryEntry();
    	String b = prohibitedFoodsTemporaryEntry();
    	String c = cookerTemporaryEntry();
    	
    	String recipe = Meal(a, b, c, d);
        System.out.println("\n=== AI가 추천하는 레시피 ===\n");
        System.out.println(recipe);
    }
    
    public static String Meal(String subIng, String Banned, String Tools, String mainIng) {//메인요리 레시피 추천 프롬프트
    	
    	String subIngredients = subIng;
    	String prohibitedFoods = Banned;
    	String cooker = Tools;
    	String mainIngredients = mainIng;
    	
    	String prompt = "내가 재료를 말하면 그 재료만 활용해서 만들 수 있는 음식 레시피를 추천해줘. 이 재료 외에는 사용하지 말아줘.: " + subIngredients
    			+ "\n이 재료는 반드시 사용해줘.: " + mainIngredients
    			+ "\n이 재료들은 사용하면 안돼.: " + prohibitedFoods
    			+ "\n이 요리도구들만 활용할 수 있어. 다른 요리도구는 없어.: " + cooker
                + "\n음식레시피를 작성할 때는 재료/음식 만드는 과정과 각 과정에서 넣어야 하는 재료와 재료의 양 그리고 각 단계에서 음식을 만들면서 걸리는 시간들을 정확히 기재해 줘. 보기 쉽고 간결하게 작성하는 걸 잊지 마."
                + "\n밑에 있는 줄의 글과 형태가 똑같이 다른 내용의 레시피 3개를 한줄씩 출력해줘. 레시피만 출력하고 다른 글자는 출력하지 말아줘. \n두부 감자 볶음$●두부▶1모$●감자▶2개$●양파▶1개$●대파▶1대$●고추장▶2스푼$●간장▶1스푼$●다진 마늘▶1스푼$●설탕▶1스푼$●참기름▶1스푼$@두부는 먼저 물기를 제거하고 2cm 정도의 크기로 썰어주세요.$@감자와 양파는 굵은 사이즈로 썰고, 대파는 어슷하게 잘라두세요.$@팬에 참기름을 두르고 두부를 볶다가 감자와 양파를 넣어 다같이 볶아줍니다.$@감자가 반쯤 익었을 때 고추장, 간장, 설탕, 다진 마늘을 넣고 잘 섞어 볶아줍니다.$@볶다가 감자가 완전히 익으면 대파를 넣고 살짝만 더 볶아 주면 완성입니다.$#약 20분";
        
    	String recipe = getRecipeFromAI(prompt);
    	
    	return recipe;
    }
    
    public static String mainIngredientsTemporaryEntry() {//서브 재료 임시 기입
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("메인 재료(필수)를 입력하세요 (쉼표로 구분): ");
    	String ingredients = scanner.nextLine();

    	return ingredients;
    }
    
    public static String subIngredientsTemporaryEntry() {//서브 재료 임시 기입
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("사용 가능한 재료를 입력하세요 (쉼표로 구분): ");
    	String subingredients = scanner.nextLine();

    	return subingredients;
    }
    
    public static String prohibitedFoodsTemporaryEntry() {//금지식품 임시 기입
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("금지 식품을 입력하세요 (쉼표로 구분): ");
    	String prohibitedFoods = scanner.nextLine();

    	return prohibitedFoods;
    }
    
    public static String cookerTemporaryEntry() {//도구 임시 기입
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("사용 가능한 도구를 입력하세요 (쉼표로 구분): ");
    	String cooker = scanner.nextLine();

    	return cooker;
    }

    public static String getRecipeFromAI(String prompt) {//ai 호출, 응답
        String model = "gpt-4";
        String url = "https://api.openai.com/v1/chat/completions";

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("max_tokens", MAX_TOKENS);

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            messages.add(message);
            requestBody.put("messages", messages);

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toJSONString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray choices = (JSONArray) jsonResponse.get("choices");

            if (choices != null && !choices.isEmpty()) {
                JSONObject firstChoice = (JSONObject) choices.get(0);
                JSONObject messageObject = (JSONObject) firstChoice.get("message");
                return (String) messageObject.get("content");
            } else {
                return "응답이 없습니다.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "오류가 발생했습니다.";
        }
    }
}
