package com.example.bot_binnance.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.dto.ProductRewiewDto;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;
import com.example.bot_binnance.repository.ProductRepository;
import com.example.bot_binnance.repository.ProductRewiewRepository;


@Service
public class ProductServiceImpl implements ProductService {

	    @Autowired
	    private ProductRepository productRepository;
	    
	    @Autowired
	    private ProductRewiewRepository productRewiewRepository;

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
	    
	    @Override 
	    public ProductRewiew saveProductRewiew(ProductRewiewDto dto) {
	    	
	    	ProductRewiew entity = new ProductRewiew();
	    	entity.setCmt(dto.getCmt());
	    	entity.setProductid(dto.getProductid());
	    	entity.setRating(dto.getRating());
	    	entity.setUserid(dto.getUserid());
	    	entity.setImageName(dto.getImageName());
	    	
	    	return this.productRewiewRepository.save(entity);
	    	
	    }
	    
	    @Override
	    public Map<String, Object> getAllProductRewiew(String productid , int page , int size) {
	    	  Pageable pageable = PageRequest.of(page, size);
	          Page<ProductRewiew> rewiews = productRewiewRepository.findByProductid(productid, pageable);
	          Map<String, Object> response = new HashMap<>();
	          response.put("rewiews", rewiews.getContent());
	          response.put("totalCount", rewiews.getTotalElements());
	          return response;
	    }

}
