package com.example.bot_binnance.service;

import java.util.Optional;

import com.example.bot_binnance.model.User;

public interface UserService {
	 Optional<User> findByEmailGetLogin(String email , String password);
	 public User saveOrUpdateUser(User user);
	 
	 Optional<User> findByEmail(String email);
	 Optional<User> findByToken(String token);
}
