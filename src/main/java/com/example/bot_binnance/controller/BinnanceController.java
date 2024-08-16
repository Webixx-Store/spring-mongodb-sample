package com.example.bot_binnance.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.common.BinanceRSI;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.dto.TopLongSortAccountRatioDto;
import com.example.bot_binnance.model.ActionLog;
import com.example.bot_binnance.model.PriceLogDto;
import com.example.bot_binnance.service.ApiBinanceService;
import com.example.bot_binnance.service.LogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class BinnanceController {
	
	@Autowired LogService actionLogService;
	@Autowired ApiBinanceService apiBinanceService;
	
	
	
	
	
	
//   private final static Logger logger = (Logger) LoggerFactory.getLogger(BinnanceController.class);

    
    UMFuturesClientImpl client  = new UMFuturesClientImpl(
    		PrivateKeyBinnance.API_KEY,
    		PrivateKeyBinnance.SECRET_KEY,
    		PrivateKeyBinnance.UM_BASE_URL);
    

    
    @GetMapping("/topLongSortPosition") 
    List<TopLongSortAccountRatioDto>  topLongSortPosition(){
  	try {
  		return  this.apiBinanceService.topLongSortAccountRatio( TimeFrame._15m);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
  	
  	
  }
    
    @GetMapping("/test")
    public ResponseEntity<?> getMethodName() {
    	try {
    		List<Double> priceDoubles = this.apiBinanceService.getClosePrices(PrivateKeyBinnance.timeFrame);
    		
  
    		Double rsi = BinanceRSI.calculateBinanceRSI(priceDoubles, 14);


    		 return ResponseEntity.ok(rsi);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
       
    }
    
    
    @GetMapping("/changeTimeFram/{timeFram}")
    public String changeTimeFram(@PathVariable String timeFram) {
    	PrivateKeyBinnance.timeFrame = timeFram;
        return "Change time frame okie";
        
    }
    
    

    
    
    
    
  
    
  

    
	
	
	

}
