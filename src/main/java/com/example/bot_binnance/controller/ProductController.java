package com.example.bot_binnance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.bot_binnance.dto.ProductRewiewDto;
import com.example.bot_binnance.dto.ResultDto;
import com.example.bot_binnance.model.Category;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;
import com.example.bot_binnance.service.ProductService;
import com.example.bot_binnance.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	  private final String PATH_URL = "upload/product/";
	  
	  @Autowired StorageService storageService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int len) {

    	 Map<String, Object> response = productService.getAllProducts(page, len);
         return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
    	Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        List<Product> products = new ArrayList<Product>();
        products.add(product);
        Map<String, Object> response = new HashMap<>();
        
        long count = this.productService.countByProductId(id);
        response.put("products", products);
        response.put("totalCount",1);	
        response.put("countRewiew", count);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getRewiews")
    public ResponseEntity<?> getRewiews(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int len,
        @RequestParam String productid) {
		Map<String, Object> response = productService.getAllProductRewiew(productid, page, len);
	    return ResponseEntity.ok(response);
    }

    
    @PostMapping("/saveRewiew")
    public ResponseEntity<?> saveRewiew(
            @RequestPart(required = false) MultipartFile fileData,
            @RequestPart(required = false) String productRewiew) { // Sử dụng String cho productRewiew

        try {
            // Chuyển đổi JSON string thành ProductRewiewDto
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRewiewDto productRewiewDto = objectMapper.readValue(productRewiew, ProductRewiewDto.class);

            if (fileData != null) {
                String fileName = this.saveImage(fileData);
                if (fileName != null) {
                    productRewiewDto.setImageName(fileName);
                }
            }

            // Gọi service để lưu review ở đây
            ProductRewiew response = this.productService.saveProductRewiew(productRewiewDto);
            ResultDto<String> result = new ResultDto<>(200, "Save Product Review OK", productRewiewDto.getProductid());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại stack trace lỗi
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(
            @RequestPart(required = false) MultipartFile img,
            @RequestPart(required = false) MultipartFile[] sliders,
            @RequestPart(required = false) String productRequest) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Product product = objectMapper.readValue(productRequest, Product.class);
			List<String> sliderPaths = new ArrayList<>();
			if (sliders != null) {
				for (MultipartFile slider : sliders) {
					if (slider != null) {
						String fileName = this.saveImage(slider);
						if (fileName != null) {
							sliderPaths.add(fileName);
						}
					}
				}
				product.setSliders(sliderPaths);
			}
				
			if(img != null) {
				String fileName = this.saveImage(img);
				if (fileName != null) {
					product.setImg(fileName);
				}
			}
			
			ResultDto<Product> result = new ResultDto<>(200, "Save Product Review OK",
			this.productService.saveOrUpdateProduct(product.getId(), product));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestPart(required = false) MultipartFile img) {
		try {
			String fileName = "";
			if(img != null) {
				fileName = this.saveImage(img);
				if (fileName != null) {
					 return ResponseEntity.ok(new ResultDto<>(200, "Save Product Review OK",fileName));
				}
			}
			return ResponseEntity.ok(new ResultDto<>(500, "No file upload",""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }
    
    @PostMapping("/upload-editor")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) throws Exception {
        try {
            String fileName = "";
            if (file != null) {
                fileName = this.saveImage(file);
                if (fileName != null) {
                    //String fileUrl = "http://localhost:8888/" + fileName;
                    String fileUrl = "https://spring-mongodb-sample.onrender.com/" + fileName;
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("uploaded", true);
                    response.put("url", fileUrl);
                    
                    return ResponseEntity.ok(response);
                }
            }
            
            // Error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("uploaded", false);
            errorResponse.put("error", new HashMap<String, String>() {{
                put("message", "Could not upload file");
            }});
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (IOException e) {
            e.printStackTrace();
            
            // Error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("uploaded", false);
            errorResponse.put("error", new HashMap<String, String>() {{
                put("message", "Could not upload file: " + e.getMessage());
            }});
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    
    @PostMapping("/categories")
    public  ResponseEntity<?> saveOrUpdateCategory(@Validated @RequestPart(required = false) String category) {
      try {
    	  
    	  ObjectMapper objectMapper = new ObjectMapper();
    	  Category categoryRequest = objectMapper.readValue(category, Category.class);
    	  Category savedCategory = productService.saveOrUpdateCategory(categoryRequest);
    	  
    	  ResultDto<Category> result = new ResultDto<>(200, "Save Product Review OK",savedCategory);
          return ResponseEntity.ok(result);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return null;
	}
    }

    // Get all categories
    @GetMapping("/categories/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = productService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get category by ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        Category category = productService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Delete category by ID
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
    	productService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    
    private String saveImage(MultipartFile fileData)
            throws Exception {
        if (fileData != null && !fileData.isEmpty()) {
            String fileName = storageService.store(fileData, "product");
            return PATH_URL + fileName;
        }
        return "";
    }
}
