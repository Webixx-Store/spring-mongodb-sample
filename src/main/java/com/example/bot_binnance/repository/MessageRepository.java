package com.example.bot_binnance.repository;

import com.example.bot_binnance.model.Message;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
	 Page<Message> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

	 @Aggregation(pipeline = {
			    "{ $sort: { createdAt: -1 } }", // Sắp xếp giảm dần theo thời gian để tin nhắn mới nhất lên đầu
			    "{ $group: { " +
			        "_id: '$userId', " +
			        "userId: { $first: '$userId' }, " +
			        "userName: { $first: '$userName' }, " +
			        "qMessage: { $first: '$qMessage' }, " +
			        "aMessage: { $first: '$aMessage' }, " +
			        "createdAt: { $first: '$createdAt' }" +
			        "qMuti: { $first: '$qMuti' }" +
			        "aMuti: { $first: '$aMuti' }" +
			    "} }"
			})
	  List<Message> findLatestMessagesByUser();
}