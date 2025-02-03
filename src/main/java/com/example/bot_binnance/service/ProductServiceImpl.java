package com.example.bot_binnance.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.dto.ProductRewiewDto;
import com.example.bot_binnance.model.Category;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;
import com.example.bot_binnance.repository.CategoryRepository;
import com.example.bot_binnance.repository.ProductRepository;
import com.example.bot_binnance.repository.ProductRewiewRepository;


@Service
public class ProductServiceImpl implements ProductService {
	
	    @Autowired
	    private CategoryRepository categoryRepository;

	    @Autowired
	    private ProductRepository productRepository;
	    
	    @Autowired
	    private ProductRewiewRepository productRewiewRepository;

	    @Override
	    public Map<String, Object> getAllProducts(int page, int size) {
	    	 Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("updatedAt")));
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
	    
	    @Override
	    public Product saveOrUpdateProduct(String id, Product productDetails) {
	        if(id == null) {
	        	return this.productRepository.save(productDetails);
	        }else {
	        	Optional<Product> optionalProduct = this.productRepository.findById(id);
	        	if(!optionalProduct.isPresent()) {
	        		return null;
	        	}
	        	Product existingProduct =  this.productRepository.findById(id).get();
	            existingProduct.setName(productDetails.getName());
	            existingProduct.setDescription(productDetails.getDescription());
	            existingProduct.setPrice(productDetails.getPrice());
	            existingProduct.setCategory(productDetails.getCategory());
	            existingProduct.setStock(productDetails.getStock());
	            existingProduct.setImg(productDetails.getImg());
	            existingProduct.setBest(productDetails.isBest());
	            existingProduct.setNew(productDetails.isNew());
	            existingProduct.setSale(productDetails.isSale());
	            existingProduct.setRate(productDetails.getRate());
	            existingProduct.setSliders(productDetails.getSliders());
	            existingProduct.setRate(productDetails.getRate());
	            return productRepository.save(existingProduct);
	        }
	    }
	    
	    @Override
	    public long countByProductId(String productId) {
	    	return this.productRewiewRepository.countByProductid(productId);
	    }
	    
	    @Override
	    public Category saveOrUpdateCategory(Category category) {
	    	Optional<Category> optionalCategory = this.categoryRepository.findById(category.getId());
        	if(!optionalCategory.isPresent()) {
        		category.setCreatedAt(LocalDateTime.now());
        	}
	        return categoryRepository.save(category);
	    }

	    // Get all categories
	    @Override
	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    // Get category by ID
	    @Override
	    public Category getCategoryById(String id) {
	        Optional<Category> optionalCategory = categoryRepository.findById(id);
	        return optionalCategory.orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
	    }

	    // Delete category by ID
	    @Override
	    public void deleteCategory(String id) {
	        if (!categoryRepository.existsById(id)) {
	            throw new RuntimeException("Category not found with ID: " + id);
	        }
	        categoryRepository.deleteById(id);
	    }

}
