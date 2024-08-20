package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Order;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
	
	 // Find order by orderId and userid
    Optional<Order> findByIdAndUserid(String orderId, String userid);
}