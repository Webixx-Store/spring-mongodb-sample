package com.example.bot_binnance.common;

import java.util.ArrayList;
import java.util.List;

public class TrendAnalysis {
	
	public static Double rsi_before = 0d;
	public static Double price_before = 0d;
	
	public static List<Double> prices = new ArrayList<>();
	public static List<Double> rsi  = new ArrayList<>();
	public static int count = 0;
	
	public static String analyzeTrend(List<Double> arr) {
        int increases = 0;
        int decreases = 0;

        // Duyệt qua danh sách và đếm số lần tăng, giảm
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) > arr.get(i - 1)) {
                increases++;
            } else if (arr.get(i) < arr.get(i - 1)) {
                decreases++;
            }
        }

        // So sánh số lần tăng và giảm để xác định xu hướng
       
		 if (increases > decreases) {
	         return "UP";
	     } else if (decreases > increases) {
	         return "DOWN";
	     } else {
	         return "";
	     }
	       
	    }
	

}
