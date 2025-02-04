package com.example.bot_binnance.controller;

import com.example.bot_binnance.model.Message;
import com.example.bot_binnance.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // API thêm tin nhắn mới
    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    // API lấy tin nhắn theo userId
    @GetMapping("/{userId}")
    public List<Message> getMessagesByUserId(@PathVariable String userId,@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int len) {
        return messageService.getMessagesByUserId(userId , page, len);
    }
    
    @GetMapping("/users/lasted")
    public List<Message> getUsersWithRecentMessages() {
        return messageService.findUsersWithRecentMessages();
    }
}
