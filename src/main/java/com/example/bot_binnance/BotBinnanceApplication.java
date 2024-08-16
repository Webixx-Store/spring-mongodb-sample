package com.example.bot_binnance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BotBinnanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotBinnanceApplication.class, args);
	}
	

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Application has started successfully.");
        };
    }
	
	

}
