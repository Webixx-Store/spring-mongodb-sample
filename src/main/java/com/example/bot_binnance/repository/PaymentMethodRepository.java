package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, String> {
}