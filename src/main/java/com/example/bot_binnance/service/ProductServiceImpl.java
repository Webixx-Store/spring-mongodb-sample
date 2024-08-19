package com.example.bot_binnance.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

	    @Autowired
	    private ProductRepository productRepository;

	    @Override
	    public Map<String, Object> getAllProducts(int page, int size) {
	    	  Pageable pageable = PageRequest.of(page, size);
	          Page<Product> products = productRepository.findAll(pageable);

	          Map<String, Object> response = new HashMap<>();
	          response.put("products", products.getContent());
	          response.put("totalCount", products.getTotalElements());

	          return response;
	    }
	    
	    @Override
	    public Product getProductById(String id) {
	        return productRepository.findById(id).orElse(null);
	    }

}
