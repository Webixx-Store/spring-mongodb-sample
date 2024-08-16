package com.example.bot_binnance.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class GridTradingBot {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String SECRET_KEY = "YOUR_SECRET_KEY";
    private static final String SYMBOL = "BTCUSDT";
    private static final double INITIAL_GRID_SIZE = 1000.0; // Initial grid size
    private static final double QUANTITY = 0.001; // Quantity of BTC per order
    private static final double STOP_LOSS_PERCENT = 5.0; // Stop loss at 5% below buy price
    private static final double TAKE_PROFIT_PERCENT = 5.0; // Take profit at 5% above buy price


    private double gridSize = INITIAL_GRID_SIZE;

    @Autowired LogServiceImpl log ;
    

    public void startTrading() {
    	
        List<Double> prices = log.getPriceLogsFromLastMinutes(20);
        System.out.println(prices);

        
        double currentPrice = getCurrentPrice();
        prices.add(currentPrice);

        if (prices.size() > 20) { // Giữ lại 20 giá gần nhất
            prices.remove(0);
            adjustGridSize(prices);
        }
        placeGridOrders(currentPrice);
        
    }

    private double getCurrentPrice() {
    	ApiBinanceServiceImp binnServiceImp =  new ApiBinanceServiceImp();
        return Double.parseDouble(binnServiceImp.getCurrentPrice().getPrice());
    }

    private void placeGridOrders(double currentPrice) {
        for (double price = currentPrice - gridSize * 5; price < currentPrice + gridSize * 5; price += gridSize) {
            if (price < currentPrice) {
                placeBuyOrder(price);
            } else if (price > currentPrice) {
                placeSellOrder(price);
            }
        }
    }

    private void placeBuyOrder(double price) {
       

        double stopLossPrice = price * (1 - STOP_LOSS_PERCENT / 100);
        double takeProfitPrice = price * (1 + TAKE_PROFIT_PERCENT / 100);

       
    }

    private void placeSellOrder(double price) {
        

        double stopLossPrice = price * (1 + STOP_LOSS_PERCENT / 100);
        double takeProfitPrice = price * (1 - TAKE_PROFIT_PERCENT / 100);

      
    }

    private void adjustGridSize(List<Double> prices) {
        double mean = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = prices.stream().mapToDouble(price -> Math.pow(price - mean, 2)).average().orElse(0.0);
        double stddev = Math.sqrt(variance);

        gridSize = INITIAL_GRID_SIZE + stddev; // Điều chỉnh kích thước lưới dựa trên độ lệch chuẩn
        System.out.println("Adjusted grid size: " + gridSize);
    }

}
