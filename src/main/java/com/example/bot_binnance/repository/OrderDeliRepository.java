package com.example.bot_binnance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.OrderDeli;

@Repository
public interface OrderDeliRepository  extends MongoRepository<OrderDeli, String> {

}
