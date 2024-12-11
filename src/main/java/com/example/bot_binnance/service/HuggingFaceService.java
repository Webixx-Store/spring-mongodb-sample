package com.example.bot_binnance.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class HuggingFaceService {

    private static final String API_URL = "https://api-inference.huggingface.co/models/google/gemma-2-2b-it";
    private static final String API_KEY = "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"; // Thay thế với API Key của bạn
    
    
    public static String getApiResponse(String inputText) {
        // Dữ liệu đầu vào
        String requestJson = "{\"inputs\": \"" + inputText + "\"}";

        // Cấu hình HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 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"); // Thay thế bằng token chính xác
        headers.set("x-wait-for-model", "true"); // Thêm header này để chờ model sẵn sàng

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        // Sử dụng RestTemplate để gửi yêu cầu và nhận phản hồi
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Gửi yêu cầu POST và nhận phản hồi
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL, HttpMethod.POST, request, String.class);

            // Kiểm tra mã trạng thái
            if (response.getStatusCode() == HttpStatus.OK) {
                // Phân tích phản hồi JSON và lấy giá trị của "generated_text"
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseNode = objectMapper.readTree(response.getBody());

                // Lấy phần "generated_text"
                JsonNode generatedTextNode = responseNode.get(0).get("generated_text");
                if (generatedTextNode != null) {
                    return generatedTextNode.asText(); // Trả về nội dung của "generated_text"
                } else {
                    System.out.println("No 'generated_text' found in response.");
                    return null;
                }
            } else {
                System.out.println("Failed to fetch data. Status: " + response.getStatusCode());
                return null;
            }
        } catch (HttpServerErrorException e) {
            // Ghi nhận lỗi nếu server trả về lỗi
            System.err.println("Server error occurred: " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            // Ghi nhận các lỗi khác
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }


    public static void main(String[] args) {
    	  // Ví dụ gọi hàm với chuỗi đầu vào
        String input = "Bạn hãy phân tích dữ liệu btc này bằng tiếng vi {\"buy_signals\":1,\"current_price\":98128.01,\"decision\":\"SELL\",\"indicators\":{\"bb_lower\":98058.27539592165,\"bb_middle\":98111.59550000001,\"bb_upper\":98164.91560407837,\"macd_line\":34.72532454386237,\"macd_signal\":47.80875250263352,\"rsi\":58.51839310961838,\"sma_20\":98111.59550000001,\"sma_50\":97977.45240000001,\"stoch_d\":99.92512963635704,\"stoch_k\":99.92515658591502},\"sell_signals\":2,\"signals_detail\":[\"MACD below Signal Line - Sell Signal\",\"Stochastic Overbought - Sell Signal\",\"SMA20 above SMA50 - Bullish Trend\"],\"strength\":66.66666666666666,\"timestamp\":\"Wed, 11 Dec 2024 09:04:00 GMT\"}";
        String responseContent = getApiResponse(input);
        
        if (responseContent != null) {
            System.out.println("Response: ");
            System.out.println(responseContent);
        } else {
            System.out.println("No response received.");
        }
    }
    
    
}
