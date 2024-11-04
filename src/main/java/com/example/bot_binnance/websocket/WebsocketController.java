package com.example.bot_binnance.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.bot_binnance.dto.ChatMessage;
import com.example.bot_binnance.service.ContentGeneratorService;



@Controller
@CrossOrigin
public class WebsocketController {
	@Autowired ContentGeneratorService contentGeneratorService;
	
//	 @Autowired
//    private PriceBroadcastService priceBroadcastService;

//    @MessageMapping("/startBroadcast")
//    @SendTo("/price")
//    public String startBroadcast(String subscriptionMessage) {
//        priceBroadcastService.startBroadcasting(subscriptionMessage);
//        return "Started broadcasting with subscription: " + subscriptionMessage;
//    }


	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/{roomId}")
	
	public ChatMessage chat(@DestinationVariable String roomId , ChatMessage message) {
		System.out.println(message);
		
		if(roomId.equals("BOT") && message.getFlag()) {
			String messString = contentGeneratorService.generateContent("AIzaSyCNHcjHExhYkmoIekWcwCKveNqd5i60yXs" , message.getMessage());
			message.setUser("BOT_API");
			message.setMessage(messString);
		}
		return new ChatMessage(message.getMessage(), message.getUser() , message.getImage() , message.getFlag());
	}
	
   


}