package com.example.bot_binnance.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.common.Wuyx59Strategy;
import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.model.Telegram;
import com.example.bot_binnance.task.ScheduledTasks;

import ch.qos.logback.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

//@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    @Autowired ApiBinanceService apiBinanceService;
   
    @Autowired LogService logService;

    public TelegramBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }
    
    @Autowired TickerService tickerService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();
            

            if (messageText.equalsIgnoreCase("/start")) {
                sendOptions(chatId, "Hello " +userName+" nice to know you please choose the following options: ");
                
                // insert or update telegramID;
                Telegram telegram = new Telegram();
                telegram.setTelegramId(String.valueOf(chatId));
                telegram.setUserName(userName);
                logService.insertTelegram(telegram);
                
            } else {
                // Handle user input for key (assuming it's sent in response to the prompt)
                handleKeyInput(chatId, messageText , userName);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            if ("option1".equals(callbackData)) {
            	String price = apiBinanceService.getCurrentPrice().getPrice();
                String responseText = "Price BTC_USDT : " + price + " USDT";
                editMessage(chatId, messageId, responseText);
            } else if ("option2".equals(callbackData)) {
                String promptMessage = "Please enter the public_key and private_key by format XXXX YYYY (with XXXX is public_key and YYYY is private_key):";
                sendPromptMessage(chatId, promptMessage);
            }else if ("option3".equals(callbackData)) {
            	if (ScheduledTasks.getScheduledTaskEnabled() == true) {
            		editMessage(chatId, messageId, "Stop bot is success");
                	ScheduledTasks.disableScheduledTask();
				}else {
					editMessage(chatId, messageId, "Start bot is success");
					ScheduledTasks.enableScheduledTask();
				}
            }else if ("option4".equals(callbackData)) {
            	try {
            		//Double price =  this.createOrder("BUY");
            		
            		List<Double> prices = this.apiBinanceService.getClosePrices("5m");
            		String signal = Wuyx59Strategy.checkTradeSignal(prices);
            		editMessage(chatId, messageId, signal);
				} catch (Exception e) {
					// TODO: handle exception
					editMessage(chatId, messageId, e.getMessage());
				}
            	
            	
            }
            
//            else if ("option5".equals(callbackData)) {
//            	try {
//            		Double price = this.createOrder("SELL");
//            		editMessage(chatId, messageId, "create Order SELL: " + price);
//				} catch (Exception e) {
//					// TODO: handle exception
//					editMessage(chatId, messageId, e.getMessage());
//				}
//            }
            else {
                String responseText = "Unknown option selected!";
                editMessage(chatId, messageId, responseText);
            }
        }
    }

    private void sendOptions(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Get Price BTC");
        button1.setCallbackData("option1");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("add Public Key and Prive Key in Binnance");
        button2.setCallbackData("option2");
        
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        if(ScheduledTasks.getScheduledTaskEnabled() == false) {
        	  button3.setText("Start bot");
        }else {
        	 button3.setText("Stop bot");
        }
       
        button3.setCallbackData("option3");
        
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Get WUYX59 BTC");
        button4.setCallbackData("option4");
        
//        InlineKeyboardButton button5 = new InlineKeyboardButton();
//        button5.setText("Order SELL");
//        button5.setCallbackData("option5");

        rowInline.add(button1);
        //rowInline.add(button2);
        //rowInline.add(button3);
        rowInline.add(button4);
  //      rowInline.add(button5);
        rowsInline.add(rowInline);

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPromptMessage(long chatId, String promptMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(promptMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleKeyInput(long chatId, String key , String userName ) {
        String responseText = "";
        
        
        
        String[] keys = splitKeys(key);
        
        if (keys != null && keys.length == 2) {
        	Telegram telegram = new Telegram();
        	telegram.setUserName(userName);
        	telegram.setTelegramId(String.valueOf(chatId) );
        	telegram.setPublicKey(keys[0]);
        	telegram.setPrivateKey(keys[1]);
        	logService.insertTelegram(telegram);
        	responseText = "add key success you can use bot right now";
        	
        }else {
        	responseText = "key not miss match format please enter the key is correct format";
        }
        
        

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(responseText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(long chatId, int messageId, String newText) {
        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(newText);

        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    
    public static String[] splitKeys(String keyString) {
        if (keyString == null || keyString.trim().isEmpty()) {
            return null;
        }

        // Sử dụng phương thức split để tách chuỗi
        String[] keys = keyString.trim().split("\\s+");

        // Kiểm tra xem có đúng 2 phần tử sau khi tách hay không
        if (keys.length != 2) {
            return null;
        }

        return keys;
    }
    
    private Double createOrder(String side) {
    	OrderDto orderDto =apiBinanceService.createOrder(0d, side, BinanceOrderType.MARKET, 0);
		OrderDto orderResult = apiBinanceService.listOrder(orderDto.getOrderId()).get(0);
		double currentPrice = Double.parseDouble(orderResult.getAvgPrice());
		tickerService.takeProfitAndStoploss("BUY", currentPrice, 1000d, 1000d);
		return currentPrice;

	}
    
    private void closeMarket() {
    	List<PositionDTO> pDto = this.apiBinanceService.positionInformation(PrivateKeyBinnance.SYMBOL);
    	if(pDto.get(0).getUnRealizedProfit() < 0 ) {
    		apiBinanceService.createOrder(0d, "BUY", BinanceOrderType.MARKET, 0);
    	}
    	
        if(pDto.get(0).getUnRealizedProfit() > 0 ) {
        	apiBinanceService.createOrder(0d, "SELL", BinanceOrderType.MARKET, 0);
    	}
    }
    
    
}
