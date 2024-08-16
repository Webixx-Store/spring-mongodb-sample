package com.example.bot_binnance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.ActionLog;
@Repository
public interface ActionLogRepository extends MongoRepository<ActionLog, String> {
    // Các phương thức truy vấn tùy chỉnh nếu cần
	
	 List<ActionLog> findByTypeOrderInAndStatusOrderByTimeCreateDesc(List<String> typeOrders, String status);
//	 void updateStatusByOrderId(String orderId, String status);
}

