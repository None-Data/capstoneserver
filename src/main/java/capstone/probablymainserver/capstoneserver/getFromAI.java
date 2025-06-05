package capstone.probablymainserver.capstoneserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class getFromAI {
	
	private static final String API_KEY = "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";
    private static final int MAX_TOKENS = 4096;
    
	
	public static String getFromAI(String prompt) {//ai 호출, 응답
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
