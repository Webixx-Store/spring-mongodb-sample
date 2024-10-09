package com.example.bot_binnance.service;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.client.impl.WebsocketClientImpl;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.PriceDto;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.dto.TopLongSortAccountRatioDto;
import com.example.bot_binnance.model.ActionLog;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ApiBinanceServiceImp implements ApiBinanceService{
	



	
	@Autowired LogService logService;
	//@Autowired TelegramBot telegramBot;
	
	 UMFuturesClientImpl client  = new UMFuturesClientImpl(
			 PrivateKeyBinnance.TESTNET_API_KEY,
			 PrivateKeyBinnance.TESTNET_SECRET_KEY,
	    	 PrivateKeyBinnance.TESTNET_BASE_URL);
	 
	 
	
	

	@Override
	public PriceDto getCurrentPrice(){
		 LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		 parameters.put("symbol", "BTCUSDT");
		 String result = client.market().tickerSymbol(parameters);
		 Gson gson = new Gson();
		 PriceDto price = gson.fromJson(result, PriceDto.class);
		 return price;
	}

	@Override
	public List<TopLongSortAccountRatioDto> topLongSortAccountRatio(TimeFrame timeFrame ) {
		 UMFuturesClientImpl client1 = new UMFuturesClientImpl();
		 LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		 parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		 parameters.put("period", timeFrame.getValue()); //"5m","15m","30m","1h","2h","4h","6h","12h","1d"
		 String result = client1.market().topTraderLongShortAccs(parameters);
		 Gson gson = new Gson();
		 Type listType = new TypeToken<ArrayList<TopLongSortAccountRatioDto>>(){}.getType();
	     List<TopLongSortAccountRatioDto> dtoList = gson.fromJson(result, listType);
	     
	     for (TopLongSortAccountRatioDto dto : dtoList) {
	    	    String formattedDate =  PrivateKeyBinnance.formatTimestamp(Long.parseLong(dto.getTimestamp())) ;
	    	    dto.setTimestamp(formattedDate);
	     }
	     return dtoList;

	}
	
	@Override
	public OrderDto createOrder(double price , String side , BinanceOrderType type , long orderId) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

       
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        DecimalFormat decimalFormatPrice = new DecimalFormat("#.##");
        parameters = new LinkedHashMap<>();
        parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
        parameters.put("side", side);
        parameters.put("quantity", decimalFormat.format(PrivateKeyBinnance.QUANTITY));
        parameters.put("type", type.getValue());
        
        if (orderId != 0) {
        	 parameters.put("orderId", orderId);
		}
        
        if(BinanceOrderType.LIMIT.equals(type) || type.equals(BinanceOrderType.STOP)
        		|| type.equals(BinanceOrderType.TAKE_PROFIT)) {
        	 parameters.put("price", decimalFormatPrice.format(price));
        	 parameters.put("timeInForce", "GTC");
        }
        
        if(type.equals(BinanceOrderType.STOP_MARKET) 
        		|| type.equals(BinanceOrderType.TAKE_PROFIT_MARKET) 
        		|| type.equals(BinanceOrderType.STOP_LOSS_LIMIT)
        		|| type.equals(BinanceOrderType.STOP)
        		|| type.equals(BinanceOrderType.TAKE_PROFIT)) {
       	 parameters.put("stopPrice",  decimalFormatPrice.format(price));
       }
        parameters.put("type", type.getValue());
        String result  =client.account().newOrder(parameters);
        Gson gson = new Gson();
        OrderDto ord = gson.fromJson(result, OrderDto.class);
        
		/* actionLog */   
        if(ord != null) {
        	ActionLog log = new ActionLog();
            log.setOrderId(String.valueOf(ord.getOrderId()));
            if (type.equals(BinanceOrderType.MARKET)) {
            	log.setStatus("FILLED");
			}else {
				log.setStatus(ord.getStatus());
			}
            log.setSymbol(ord.getSymbol());
            log.setTypeOrder(ord.getType());
            log.setSide(side);
            log.setPrice(Double.parseDouble(decimalFormatPrice.format(price)));
            log.setTimeCreate(LocalDateTime.now());
            log.setTimeUpdate(LocalDateTime.now());
            long currentTimestamp = Instant.now().toEpochMilli();
            // Định dạng timestamp thành chuỗi
            String formattedTimestamp = PrivateKeyBinnance.formatTimestamp(currentTimestamp);
            log.setDate(formattedTimestamp);
            this.logService.createActionLog(log);
        }
        
        
        //telegramBot.sendMessage("1180457993",side + " _ " + type.getValue() + " " + PrivateKeyBinnance.QUANTITY +" BTC_USDT at price: " + decimalFormatPrice.format(price) );
        
		return ord;
	}
	

	
	@Override 
	public List<PositionDTO>  positionInformation(String symbol) {
		 LinkedHashMap<String, Object> parameters=new LinkedHashMap<>();
		 parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		 String  result = client.account().positionInformation(parameters);
		 Gson gson = new Gson();
		 Type listType = new TypeToken<ArrayList<PositionDTO>>(){}.getType();
	     List<PositionDTO> dtoList = gson.fromJson(result, listType);
		 return  dtoList;
	}
	
	@Override 
	public List<OrderDto>  listOrder(long orderId) {
		 LinkedHashMap<String, Object> parameters=new LinkedHashMap<>();
		 parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		 parameters.put("orderId", orderId);
		 String  result = client.account().allOrders(parameters);
		 Gson gson = new Gson();
		 Type listType = new TypeToken<ArrayList<OrderDto>>(){}.getType();
	     List<OrderDto> dtoList = gson.fromJson(result, listType);
		 return  dtoList;
	}

	@Override
	public void cancelOpenOrder() {
	 	 LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		 parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
         client.account().cancelAllOpenOrders(parameters);
	}
	
	
	@Override
	public  List<Double> getClosePrices(String time) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
	    parameters.put("interval", time);
	    parameters.put("limit", 200);
	    String result = client.market().klines(parameters);
		JSONArray jsonArray = new JSONArray(result);
        List<Double> closePrices = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray kline = jsonArray.getJSONArray(i);  
            if(i == 199) {
            	closePrices.add(Double.parseDouble(getCurrentPrice().getPrice()))  ;
            }else {
            	closePrices.add(kline.getDouble(4)); 
            }
            
        }
        return closePrices;
	}
	

	
	
	



	
	

	

	


}
