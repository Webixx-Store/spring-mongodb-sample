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
	    public static final String TESTNET_API_KEY = "9fe6b1c6e7d43c8aae7f3bbfc60baf40125a7ddd807f82b8d21f2814ba5b1205";
	    public static final String TESTNET_SECRET_KEY = "06c0cc3bd5b14a74ac90efd6ada9f1642f140b399f333b43e7abc9fd4aff6572";

	    public static final double QUANTITY = 0.004d;

	    
		public static String SYMBOL = "BTCUSDT"; 
		public static String LONG  = "LONG";
		public static String SHORT  = "SHORT";
		public static String BUY  = "BUY";
		public static String SELL  = "SELL";
		

		public static double RATE_TP = 0.72d;
		public static double RATE_SL = 0.72d;

		public static double LAVERAGE = 125d;
		
		public static  String timeFrame= "1h";
	    
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
