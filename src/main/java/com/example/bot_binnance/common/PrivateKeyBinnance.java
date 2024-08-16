package com.example.bot_binnance.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PrivateKeyBinnance {
	
	    public static final String UM_BASE_URL = "https://fapi.binance.com";
	    public static final String CM_BASE_URL = "https://dapi.binance.com";

	    public static final String API_KEY = "xCmQ6graVQdpIg8Yd0pffsyjEJbfNJDfBKnWEf891e4QIMzk4MiOX2gm3Lh4oC21";
	    public static final String SECRET_KEY = "48bJ4pXsZHS2sVUaQGvh0urN6T8cq4Fdf1WVUIgAcr1NRK2UkQ4dV45nrv606XWg";



	    public static final String TESTNET_BASE_URL = "https://testnet.binancefuture.com";
	    public static final String TESTNET_API_KEY = "154ebe97d940fb57eb76275d5991fc647846847eb393726194e575358a442713";
	    public static final String TESTNET_SECRET_KEY = "703c5653f48a5f2e28579633cb60012579f4c1fe5833a214153d204ed38e27b1";

	    public static final double QUANTITY = 0.003d;

	    
		public static String SYMBOL = "BTCUSDT"; 
		public static String LONG  = "LONG";
		public static String SHORT  = "SHORT";
		public static String BUY  = "BUY";
		public static String SELL  = "SELL";
		

		public static double RATE_TP = 0.72d;
		public static double RATE_SL = 0.72d;

		public static double LAVERAGE = 125d;
		
		public static  String timeFrame= "1m";
	    
	    public static String generateOrderId() {
	        // Lấy ngày và giờ hiện tại
	        LocalDateTime now = LocalDateTime.now();

	        // Định dạng ngày và giờ theo yyyymmddhhmmss
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String formattedDateTime = now.format(formatter);

	        // Thêm phần BTCUSDT vào orderId
	        String orderId = formattedDateTime + "BTCUSDT";

	        return orderId;
	   }
	    
	    public static String formatTimestamp(long timestamp) {
	        // Chuyển đổi timestamp thành LocalDateTime
	        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
	        
	        // Định dạng thành chuỗi yyyymmdd hhmmss
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
	        String formattedDateTime = dateTime.format(formatter);
	        
	        return formattedDateTime;
	    }

}
