package com.example.bot_binnance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic" , "/price");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Endpoint cho server WebSocket
                .setAllowedOrigins("http://localhost:4200" , "https://dattot-vn.web.app" , "http://localhost:4300" , "https://auth-webixx.web.app" , "https://cryptovuive.web.app" , "https://minhminh-kalivisa.web.app") // Chỉ định nguồn gốc cho phép kết nối
                .withSockJS(); // Sử dụng SockJS nếu cần thiết
    }

//    @Bean
//    public WebSocketConnectionManager wsConnectionManager(MyWebSocketHandler webSocketHandler) {
//        WebSocketConnectionManager manager = new WebSocketConnectionManager(
//            new StandardWebSocketClient(),
//            webSocketHandler,
//            "wss://wbs.mexc.com/ws" // Thay thế URL WebSocket của bạn ở đây
//        );
//        manager.setAutoStartup(true);
//        return manager;
//    }

}