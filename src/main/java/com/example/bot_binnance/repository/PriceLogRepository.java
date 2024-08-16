package com.example.bot_binnance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.PriceLogDto;
@Repository
public interface PriceLogRepository extends MongoRepository<PriceLogDto, String> {
    // Các phương thức truy vấn tùy chỉnh nếu cần
}

