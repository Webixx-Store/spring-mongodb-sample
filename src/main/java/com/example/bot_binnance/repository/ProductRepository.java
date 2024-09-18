package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
