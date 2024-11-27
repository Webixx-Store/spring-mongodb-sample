package com.example.bot_binnance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.ProductRewiew;
@Repository
public interface ProductRewiewRepository  extends MongoRepository<ProductRewiew, String> {
	 Page<ProductRewiew> findByProductid(String productid, Pageable pageable);
	 long countByProductid(String productid);

}
