package com.example.bot_binnance.service;

import java.util.List;
import java.util.Map;
import com.example.bot_binnance.model.Message;

public interface MessageService {
	public Message saveMessage(Message message);
	public List<Message> getMessagesByUserId(String userId , int page , int size);
	List<Message>  findUsersWithRecentMessages();
}
