package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
