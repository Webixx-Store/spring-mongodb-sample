package com.example.bot_binnance.service;
import java.io.File;
import java.io.IOException;

import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    private String apiUrl = "https://api.imgbb.com/1/upload";
    private String apiKey = "1e390df3b006973254195bd03e3ccb49";

    public String uploadImage(String fileName, MultipartFile img) throws IOException {
        String fullUrl = String.format("%s?key=%s", apiUrl, apiKey);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        
        // Convert MultipartFile to Resource
        ByteArrayResource resource = new ByteArrayResource(img.getBytes()) {
            @Override
            public String getFilename() {
                return img.getOriginalFilename();
            }
        };
        
        // Add the file as a Resource
        body.add("image", resource);
        
        if (fileName != null && !fileName.isEmpty()) {
            body.add("name", fileName);
        }
        
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.POST, entity, String.class);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        
        // Lấy URL từ data.image.url
        String imageUrl = jsonResponse.getJSONObject("data")
                                    .getJSONObject("image")
                                    .getString("url");
        return imageUrl;
    }
}