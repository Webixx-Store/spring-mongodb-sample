package com.example.bot_binnance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentGeneratorService {

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    public  String generateContent(String apiKey, String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        
        // Tạo payload JSON
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", new Object[] {
            new HashMap<String, Object>() {{
                put("parts", new Object[] {
                    new HashMap<String, String>() {{
                        put("text", prompt);
                    }}
                });
            }}
        });

        // Tạo URI với API key
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("key", apiKey)
                .toUriString();

        // Gửi yêu cầu POST và nhận phản hồi
        Map<String, Object> response = restTemplate.postForObject(url, requestBody, Map.class);

        // Trích xuất chuỗi văn bản từ phản hồi
        if (response != null && response.get("candidates") != null) {
            Map<String, Object> candidate = ((List<Map<String, Object>>) response.get("candidates")).get(0);
            Map<String, Object> content = (Map<String, Object>) candidate.get("content");
            List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
            return parts.get(0).get("text");
        }

        throw new RuntimeException("Failed to generate content");
    }
    
}
