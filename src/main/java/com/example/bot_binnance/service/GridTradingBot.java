package com.example.bot_binnance.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class GridTradingBot {
    private static final double INITIAL_GRID_SIZE = 1000.0; // Initial grid size
    private static final double STOP_LOSS_PERCENT = 5.0; // Stop loss at 5% below buy price
    private static final double TAKE_PROFIT_PERCENT = 5.0; // Take profit at 5% above buy price


    private double gridSize = INITIAL_GRID_SIZE;

    @Autowired LogServiceImpl log ;
    @Autowired ApiBinanceService apiBinnance;
    

    public void startTrading() {
        List<Double> prices = apiBinnance.getClosePrices("1m");
        System.out.println(prices);
        double currentPrice = Double.parseDouble(apiBinnance.getCurrentPrice().getPrice());
        prices.add(currentPrice);
        if (prices.size() > 20) { 
            prices.remove(0);
            adjustGridSize(prices);
        }
        placeGridOrders(currentPrice); 
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
        apiBinnance.createLimitOrderWithTPAndSL(price, stopLossPrice, takeProfitPrice, "BUY");
    }

    private void placeSellOrder(double price) {
        double stopLossPrice = price * (1 + STOP_LOSS_PERCENT / 100);
        double takeProfitPrice = price * (1 - TAKE_PROFIT_PERCENT / 100);
        apiBinnance.createLimitOrderWithTPAndSL(price, stopLossPrice, takeProfitPrice, "SELL");
    }

    private void adjustGridSize(List<Double> prices) {
        double mean = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = prices.stream().mapToDouble(price -> Math.pow(price - mean, 2)).average().orElse(0.0);
        double stddev = Math.sqrt(variance);
        gridSize = INITIAL_GRID_SIZE + stddev; 
        System.out.println("Adjusted grid size: " + gridSize);
    }

}
