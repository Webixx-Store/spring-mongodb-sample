package com.example.bot_binnance.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.User;
@Repository
public interface UserRepository    extends MongoRepository<User, String>{
	 Optional<User> findByEmailOrId(String email, String id);
	 Optional<User> findByToken(String token);
}
