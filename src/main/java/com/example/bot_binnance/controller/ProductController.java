package com.example.bot_binnance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bot_binnance.dto.ProductRewiewDto;
import com.example.bot_binnance.dto.ResultDto;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;
import com.example.bot_binnance.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

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
        response.put("products", products);
        response.put("totalCount",1);	
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
    public ResponseEntity<?> saveRewiew(@RequestBody ProductRewiewDto productRewiew){
    	try {
			ProductRewiew response = this.productService.saveProductRewiew(productRewiew);
			ResultDto<String> result = new ResultDto<String>(200, "Save Proudt Rewiew OK", response.getProductid());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }
}
