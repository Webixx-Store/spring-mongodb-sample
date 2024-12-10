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

    public static void main(String[] args) {
        // Tạo RestTemplate với timeout
        RestTemplate restTemplate = new RestTemplate();

        // Thêm retry logic và timeout nếu cần
        String apiUrl = "https://api-inference.huggingface.co/models/google/gemma-2-2b-it";

        // Dữ liệu đầu vào
        String requestJson = "{\"inputs\": \"Please tell me price bitcoin today \"}";

        // Cấu hình HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer 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"); // Thay thế bằng token chính xác
        headers.set("x-wait-for-model", "true"); // Thêm header này để chờ model sẵn sàng

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response: ");
                System.out.println(response.getBody());
            } else {
                System.out.println("Failed to fetch data. Status: " + response.getStatusCode());
            }
        } catch (HttpServerErrorException e) {
            // Ghi nhận lỗi nếu server trả về lỗi
            System.err.println("Server error occurred: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Ghi nhận các lỗi khác
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
