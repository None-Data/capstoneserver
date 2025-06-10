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
    	
    	String prompt = "랜덤으로 하나의 식재료에 대한 정보를 알려줘"
    			+ "\n밑에 있는 줄의 글과 형태가 똑같이 한줄로 출력해줘. \n고구마@고구마는 강력한 항산화 기능을 가진 식재료로 칼로리가 낮아 다이어트 식품으로 좋다. 고구마는 비타민A, 비타민C 그리고 식이섬유가 풍부하므로 소화기능을 돕고, 안구건조증과 백내장 예방에도 효과적이다. 특히, 고구마에는 '다이어트 폴리페놀'이라는 물질이 들어 있어 혈당 조절과 비만 및 당뇨병 예방에 도움이 된다. 또한, 면역력을 높이고 피로 회복에도 뛰어나다.";
        
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
