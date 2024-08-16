package com.example.bot_binnance.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;

public class BinanceRSI {

    public static Double calculateBinanceRSI(List<Double> closePrices, int period) {
    	
    	if(closePrices.size() < period) return  -1d;
        List<Double> rsi = new ArrayList<>();
        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();
        DecimalFormat decimalFormatPrice = new DecimalFormat("#.##");

        for (int i = 1; i < closePrices.size(); i++) {
            double change = closePrices.get(i) - closePrices.get(i - 1);
            if (change > 0) {
                gains.add(change);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(-change);
            }
        }

        double avgGain = gains.stream().limit(period).mapToDouble(Double::doubleValue).sum() / period;
        double avgLoss = losses.stream().limit(period).mapToDouble(Double::doubleValue).sum() / period;

        for (int i = period; i < gains.size(); i++) {
            avgGain = (avgGain * (period - 1) + gains.get(i)) / period;
            avgLoss = (avgLoss * (period - 1) + losses.get(i)) / period;

            double rs = avgGain / avgLoss;
            rsi.add(100 - (100 / (1 + rs)));
        }

        return Double.parseDouble(decimalFormatPrice.format(rsi.get(rsi.size()-1)))  ;
    }
    
    // Hàm quyết định mua hoặc bán dựa trên RSI
    public static String decideBuySell(double rsi, double overboughtThreshold, double oversoldThreshold) {
        if (rsi >= overboughtThreshold) {
            return "SORT"; // Thị trường quá mua
        } else if (rsi <= oversoldThreshold) {
            return "LONG"; // Thị trường quá bán
        } else {
            return "HOLD"; // Không đưa ra quyết định
        }
    }


}
