package com.example.bot_binnance.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.bot_binnance.dto.ProductRewiewDto;
import com.example.bot_binnance.model.Category;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;

public interface ProductService {
	public  Map<String, Object> getAllProducts(int page, int size);
	public Product getProductById(String id);
	ProductRewiew saveProductRewiew(ProductRewiewDto dto);
	Map<String, Object> getAllProductRewiew(String productid, int page, int size);
	public Product saveOrUpdateProduct(String id, Product productDetails);
	long countByProductId(String productId);
	Category saveOrUpdateCategory(Category category);
	List<Category> getAllCategories();
	Category getCategoryById(String id);
	void deleteCategory(String id);
}
