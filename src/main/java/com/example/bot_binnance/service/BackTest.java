package com.example.bot_binnance.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.common.BinanceRSI;
import com.example.bot_binnance.common.MovingAverageCross;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.common.TrendAnalysis;
import com.example.bot_binnance.dto.CrossoverPoint;
import com.example.bot_binnance.dto.PriceTime;

public class BackTest {
	
	private static final Logger logger = LoggerFactory.getLogger(BackTest.class);
	
	
	static long DATE = (24 * 60 * 60 * 1000);
	static long HOUR = (60 * 60 * 1000);
	static long MINUS = (60 * 1000);
	static long SECOND = 1000;
	
	public static boolean isBuying = false;
	public static double  priceIn = 0d;
	public static long countFail = 0;
	public static long countSuccess = 0;
	public static UMFuturesClientImpl client  = new UMFuturesClientImpl(
	    		PrivateKeyBinnance.API_KEY,
	    		PrivateKeyBinnance.SECRET_KEY,
	    		PrivateKeyBinnance.UM_BASE_URL);
	
	public static String formatTime( long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
	}
	
	public static List<PriceTime> getTimePrices(long passTime ,int limit ) {
		
		try {
			
			
			
			LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
			parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		    parameters.put("interval", "1m");
		    parameters.put("limit", limit);
		    if (passTime != 0) {
		    	long startTime = passTime - MINUS*81;
		    	long endTime = passTime;
		    	parameters.put("startTime", startTime);
			    parameters.put("endTime", endTime);
			}
		    String result = client.market().klines(parameters);
			JSONArray jsonArray = new JSONArray(result);
	        List<PriceTime> closePrices = new ArrayList<>();
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONArray kline = jsonArray.getJSONArray(i);  
	            closePrices.add(new PriceTime(kline.getLong(6), kline.getDouble(4))); 
	        }
	        return closePrices;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}
	
  public static List<Double> getPrices(long passTime , int limit ) {
		
		try {
			long startTime = passTime -  (4*60 * 60 * 1000);
			long endTime  = passTime;
			
			
			
			LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
			parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		    parameters.put("interval", "1m");
		    parameters.put("startTime", startTime);
		    parameters.put("endTime", endTime);
		    parameters.put("limit", limit);
		    String result = client.market().klines(parameters);
			JSONArray jsonArray = new JSONArray(result);
	        List<Double> closePrices = new ArrayList<>();
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONArray kline = jsonArray.getJSONArray(i);  
	            closePrices.add(kline.getDouble(4)); 
	        }
	        return closePrices;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}
	
	
	public static String chooseOrder(double currentPrice ,  double rsi) {
		
		if (rsi <= 22 || rsi >= 78) {
        	TrendAnalysis.rsi_before= rsi;
		}
        
        if(TrendAnalysis.rsi_before <= 22 && rsi > 33) {
        	TrendAnalysis.rsi_before = 0d;
        }
        
        if(TrendAnalysis.rsi_before >= 78 && rsi < 67) {
        	TrendAnalysis.rsi_before = 0d;
        }
        
        if(TrendAnalysis.rsi_before > 0d && rsi >= 67 && rsi <= 72) {
        	isBuying = true;
        	priceIn = currentPrice;
        	TrendAnalysis.rsi_before = 0d;
        	return "BUY";        	
        }
        
        if(TrendAnalysis.rsi_before > 0d  && rsi >= 28 && rsi <=33) {
        	isBuying = true;
        	priceIn = currentPrice;
        	TrendAnalysis.rsi_before = 0d;
        	return "SELL";        	
        }
		
		return "HOLD";
		
	}
	
	public static void checkPriceProfit(double currentPrice , String side) {
		double tp =200d; 
		if(isBuying) {
			if(side.equals("BUY")) {
				if(currentPrice >= priceIn + tp) {
					isBuying = false;
					priceIn = 0;
					countSuccess ++;
				}else if (currentPrice <= priceIn - tp) {
					isBuying = false;
					priceIn = 0;
					countFail ++;
				}
			}
			
			if(side.equals("SELL")) {
				if(currentPrice >= priceIn + tp) {
					isBuying = false;
					priceIn = 0;
					countFail ++;
				}else if (currentPrice <= priceIn - tp) {
					isBuying = false;
					priceIn = 0;
					countSuccess ++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String side = "";
		
		System.out.println(formatTime(1720852619999l));
		
		long fromDate =  System.currentTimeMillis() - (DATE/4) ;
		long toDate = System.currentTimeMillis();
		while(fromDate < toDate) {
			List<PriceTime> closingPrices = BackTest.getTimePrices(fromDate,80);
			List<Double> rsiPrice = BackTest.getPrices(fromDate,80);
			Double currentPrice = closingPrices.get(closingPrices.size()-1).price;
			List <Double> prices = new ArrayList<>();
	        
	        for(PriceTime item : closingPrices) {
	        	prices.add(item.price);
	        }

	        // Tính toán MA14 và MA24
	        List<Double> ma14Values = MovingAverageCross.calculateMA(prices, 14);
	        List<Double> ma24Values = MovingAverageCross.calculateMA(prices, 24);
	        if (!isBuying) {
	        	 List<CrossoverPoint> crossoverPoints = MovingAverageCross.findCrossoverPoints(ma14Values, ma24Values);

	 	        // In kết quả
	 	        List <CrossoverPoint> result = new ArrayList<>();
	 	        for (CrossoverPoint point : crossoverPoints) {
	 	           if (point.getIndex() + 24 >= 75) {
	 	        	   result.add(point);
	 		  	   }
	 	        }
	 	        double rsi = BinanceRSI.calculateBinanceRSI(rsiPrice, 14);
	 	        if(result.size() == 1 && (rsi < 40 || rsi > 60)) {
	 	        	
	 	        	if(rsi < 40) {
	 	        		side = "BUY";
	 	        	}
	 	        	if(rsi > 60) {
	 	        		side = "SELL";
	 	        	}
	 	        	isBuying = true;
	 	        	priceIn = currentPrice;
	 	        	logger.info(side + " at price: " +currentPrice 
	 	        			+ " at time : " + formatTime(fromDate) );
	 	        	
	 	        }
	        }
	        checkPriceProfit(currentPrice, side);
	        logger.info( "AT TIME  : "+formatTime(fromDate));
    		logger.info( "FAIL : "+countFail);
    		logger.info( "SUCCESS : "+countSuccess);
			fromDate = fromDate +  MINUS; 
		}
//		
//		while (fromDate < toDate) {
//			
//			List<Double> prices =  getPrices(fromDate , 200);
//			Double currentPrice =prices.get(prices.size()-1) ;
//			int period = 14; 
//			
//            double rsi = BinanceRSI.calculateBinanceRSI(prices, period);
//            
//            if (!isBuying) {
//            	 side = chooseOrder(currentPrice, rsi);
//			}
//            checkPriceProfit(currentPrice, side);
//			logger.info( "RSI at time : " + formatTime(fromDate)  + " :"+rsi);
//			logger.info( "FAIL : "+countFail);
//			logger.info( "SUCCESS : "+countSuccess);
//			fromDate = fromDate + (10 * oneSecond);
//		}
		
		//getPrices(passTime);
	}
	
	

	
	

}

