package com.example.bot_binnance.common;

import java.util.List;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.dto.CrossoverPoint;
import com.example.bot_binnance.dto.PriceDto;
import com.example.bot_binnance.dto.PriceTime;
import com.example.bot_binnance.service.BackTest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MovingAverageCross {

    public static void main(String[] args) {
        // Giả sử bạn có một danh sách giá đóng cửa của 200 phiên giao dịch gần nhất
        List<PriceTime> closingPrices = BackTest.getTimePrices(0,80);
        
        closingPrices.add(new PriceTime(System.currentTimeMillis(), getCurrentPrice()));
        List <Double> prices = new ArrayList<>();
        
       for(int i = 0 ; i < closingPrices.size() ; i++) {
    	   prices.add(closingPrices.get(i).price);
       }

        // Tính toán MA14 và MA24
        List<Double> ma14Values = calculateMA(prices, 14);
        List<Double> ma24Values = calculateMA(prices, 24);

        // Xác định điểm cắt nhau và loại giao cắt
        List<CrossoverPoint> crossoverPoints = findCrossoverPoints(ma14Values, ma24Values);

        // In kết quả
        for (CrossoverPoint point : crossoverPoints) {
            System.out.println("Crossover at index: "+ (point.getIndex() + 24) +
            		" at time: "+ closingPrices.get(point.getIndex() + 24).getTime() 
            		+
            		" at Price: " + closingPrices.get(point.getIndex() + 24).price +
            		
            		" at MA14: " + point.getPriceMaMin() +
            		" at MA24: " + point.getPriceMaMax() +
            		 " - "	 + point.getType());
        }
    }

    public static List<Double> calculateMA(List<Double> prices, int period) {
        List<Double> maValues = new ArrayList<>();
        if (prices.size() < period) {
            throw new IllegalArgumentException("Dữ liệu không đủ để tính MA" + period);
        }

        for (int i = 0; i <= prices.size() - period; i++) {
            double sum = 0;
            for (int j = i; j < i + period; j++) {
                sum += prices.get(j);
            }
            maValues.add(sum / period);
        }

        return maValues;
    }

    public static List<CrossoverPoint> findCrossoverPoints(List<Double> ma14, List<Double> ma24) {
        List<CrossoverPoint> crossoverPoints = new ArrayList<>();

        int size = Math.min(ma14.size(), ma24.size());

        for (int i = 0; i < size - 1; i++) {
            if (ma14.get(i) < ma24.get(i) && ma14.get(i + 1) > ma24.get(i + 1)) {
                crossoverPoints.add(new CrossoverPoint(i + 1, "SELL" ,ma14.get(i) , ma24.get(i) )); // MA14 CẮT LÊN MA24
            } else if (ma14.get(i) > ma24.get(i) && ma14.get(i + 1) < ma24.get(i + 1)) {
                crossoverPoints.add(new CrossoverPoint(i + 1, "BUY" ,ma14.get(i) , ma24.get(i))); // MA14 CẮT XUỐNG MA24
            }
        }

        return crossoverPoints;
    }

    
    
    public static Double getCurrentPrice() {
   	 UMFuturesClientImpl client  = new UMFuturesClientImpl(
			 PrivateKeyBinnance.API_KEY,
			 PrivateKeyBinnance.SECRET_KEY,
	    	 PrivateKeyBinnance.UM_BASE_URL);
	 
	 
    	 LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		 parameters.put("symbol", "BTCUSDT");
		 String result = client.market().tickerSymbol(parameters);
		 Gson gson = new Gson();
		 PriceDto price = gson.fromJson(result, PriceDto.class);
		 return Double.parseDouble(price.getPrice());
    }
}

//Golden Cross (Giao cắt vàng):

//Xảy ra khi đường MA ngắn hạn (MA14) cắt lên trên đường MA dài hạn (MA24).
//Đây thường được coi là tín hiệu mua, cho thấy một xu hướng tăng giá có thể đang hình thành.
//Death Cross (Giao cắt tử thần):
//
//Xảy ra khi đường MA ngắn hạn (MA14) cắt xuống dưới đường MA dài hạn (MA24).
//Đây thường được coi là tín hiệu bán, cho thấy một xu hướng giảm giá có thể đang hình thành.

