package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Product;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	@Query("{'$and': ["
			+ "{'name': {$regex: ?0, $options: 'i'}},"
			+ "{'category': ?1}"
			+ "]}")
	Page<Product> searchProducts(String name, String category, Pageable pageable);
	
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
