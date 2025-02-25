package com.example.bot_binnance.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class CloudinaryImageManager {
    private final Cloudinary cloudinary;

    public CloudinaryImageManager() {
        cloudinary = new Cloudinary("cloudinary://731418236769821:B803jh-n-wFQVTGB7-wN7mujXXI@dktt5amgw");
    }

    public Map<String, Object> uploadLargeImage(InputStream inputStream, String filename) {
        try {
            // Configure upload parameters
            Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
            );

            // Upload the image stream using uploadLarge and return the result
            return cloudinary.uploader().uploadLarge(inputStream, params);
            
        } catch (IOException e) {
            System.err.println("Error uploading large image: " + e.getMessage());
            return null;
        }
    }

    public Map<String, Object> getImageDetails(String publicId) {
        try {
            // Configure parameters for getting image details
            Map<String, Object> params = ObjectUtils.asMap(
                "quality_analysis", true
            );

            // Get and return image details
            return cloudinary.api().resource(publicId, params);

        } catch (Exception e) {
            System.err.println("Error getting image details: " + e.getMessage());
            return null;
        }
    }

    public String getImageUrl(String publicId) {
        // Generate transformed image URL
        return cloudinary.url()
            .transformation(new Transformation()
                .crop("pad")
                .background("auto:predominant"))
            .generate(publicId);
    }
}