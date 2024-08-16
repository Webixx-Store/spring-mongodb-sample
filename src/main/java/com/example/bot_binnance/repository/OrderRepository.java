package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}