package com.example.bot_binnance.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.bot_binnance.service.TelegramBot;

//@Configuration
public class TelegramBotConfig {

//    @Value("${telegram.bot.username}")
//    private String botUsername;
//
//    @Value("${telegram.bot.token}")
//    private String botToken;
//
//    @Bean
//    public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws TelegramApiException {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(telegramBot);
//        return telegramBotsApi;
//    }
//
//    @Bean
//    public TelegramBot telegramBot() {
//        return new TelegramBot(botUsername, botToken);
//    }
}
