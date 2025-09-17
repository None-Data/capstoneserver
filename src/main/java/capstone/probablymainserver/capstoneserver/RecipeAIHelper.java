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

    private static final String API_KEY = "sk-proj-wQC6iyhdUVnP8csN0w1FGY_1UPizt11-74CMMB5FbJ0GPJeCVnmJp7pkppPeHJalXbyMEMeMjbT3BlbkFJ-A6S2YI0l0-NgBBFxr1DSubEqm__jwoei8Hj6jU1VKKcrwjIwPvMTFfjkE-YXDKXvbY-1aEA5f1pAzb4PB_n9gdZWjgjSJskT3BlbkFJc1avLfPL5pYpKfusCu6VEx5SqeWHd0Xw9bRPkxB2_Zht1et6mpgKg7dJO6xqkJPDlHFGUUURkA";
    private static final int MAX_TOKENS = 4096;
    
    
    public static void main(String[] args) {
    	String a = subIngredientsTemporaryEntry();
    	String b = prohibitedFoodsTemporaryEntry();
    	String c = cookerTemporaryEntry();
    	
    	String recipe = Meal(a,b,c);
        System.out.println(recipe);
    }
    
    public static String Meal(String Ing, String Banned, String Tools) {//메인요리 레시피 추천 프롬프트
    	
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
