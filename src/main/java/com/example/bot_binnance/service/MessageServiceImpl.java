package com.example.bot_binnance.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.model.Message;
import com.example.bot_binnance.repository.MessageRepository;
@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private  MessageRepository messageRepository;
	  // Thêm tin nhắn mới
	@Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Lấy danh sách tin nhắn theo userId (mới nhất trước)
	@Override
    public List<Message> getMessagesByUserId(String userId , int page , int size) {
    	  return messageRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size)).getContent();
    
    }

	@Override
	public List<Message> findUsersWithRecentMessages() {
		// TODO Auto-generated method stub
		return messageRepository.findLatestMessagesByUser();
	}

}
