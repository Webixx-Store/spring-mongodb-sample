package com.example.bot_binnance.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.bot_binnance.model.Telegram;

public interface TelegramRepository extends MongoRepository<Telegram, String> {
    Optional<Telegram> findByTelegramId(String telegramId);
}