package com.example.bot_binnance.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.bot_binnance.model.Product;

public interface ProductService {
	public  Map<String, Object> getAllProducts(int page, int size);
	public Product getProductById(String id);
}
