package com.example.bot_binnance.common;

import java.util.List;
import java.util.ArrayList;

public class Wuyx59Strategy {

    // Tính EMA với chu kỳ nhất định
    public static List<Double> calculateEMA(List<Double> prices, int period) {
        List<Double> emaValues = new ArrayList<>();
        if (prices.size() < period) return emaValues;

        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        double emaPrev = sum / period;
        emaValues.add(emaPrev);

        double multiplier = 2.0 / (period + 1);
        for (int i = period; i < prices.size(); i++) {
            double ema = ((prices.get(i) - emaPrev) * multiplier) + emaPrev;
            emaValues.add(ema);
            emaPrev = ema;
        }
        return emaValues;
    }

    // Xác định tín hiệu giao dịch theo WUYX 59
    public static String checkTradeSignal(List<Double> prices , List<Double> highs, List<Double> lows) {
        if (prices.size() < 9) return "Không đủ dữ liệu để phân tích.";

        List<Double> ema5 = calculateEMA(prices, 5);
        List<Double> ema9 = calculateEMA(prices, 9);
        List<Double> cci = calculateCCI(highs, lows, prices, 14);  // CCI sử dụng chu kỳ 14

        if (ema5.size() < 2 || ema9.size() < 2) return "Không đủ dữ liệu EMA.";

        double ema5_current = ema5.get(ema5.size() - 1);
        double ema5_prev = ema5.get(ema5.size() - 2);
        double ema9_current = ema9.get(ema9.size() - 1);
        double ema9_prev = ema9.get(ema9.size() - 2);
        
        double close_current = prices.get(prices.size() - 1);
        double close_prev = prices.get(prices.size() - 2);
        double cci_current = cci.get(cci.size() - 1);

        // Điều kiện BUY
        if (ema5_prev < ema9_prev && ema5_current > ema9_current && close_current > close_prev ) { //&& cci_current < -100
            return "BUY";
        }
        // Điều kiện SELL
        else if (ema5_prev > ema9_prev && ema5_current < ema9_current && close_current < close_prev ) { //&& cci_current > 100
            return "SELL";
        }
        return "No Action";
    }
    
    public static List<Double> calculateCCI(List<Double> highs, List<Double> lows, List<Double> closes, int period) {
        List<Double> cciValues = new ArrayList<>();
        if (highs.size() < period) return cciValues;

        for (int i = period - 1; i < highs.size(); i++) {
            double sumTP = 0;
            List<Double> typicalPrices = new ArrayList<>();

            for (int j = i - (period - 1); j <= i; j++) {
                double tp = (highs.get(j) + lows.get(j) + closes.get(j)) / 3;
                typicalPrices.add(tp);
                sumTP += tp;
            }

            double sma = sumTP / period; // Trung bình động của TP
            double mad = 0;

            for (double tp : typicalPrices) {
                mad += Math.abs(tp - sma);
            }
            mad /= period;

            // Lấy Typical Price tại thời điểm hiện tại (cuối chu kỳ)
            double currentTP = typicalPrices.get(period - 1);
            double cci = (currentTP - sma) / (0.015 * mad);
            cciValues.add(cci);
        }

        return cciValues;
    }


    public static double[] calculateSLTP(List<Double> closes, List<Double> highs, List<Double> lows, String tradeSignal) {
        double close_current = closes.get(closes.size() - 1);
        double high_prev = highs.get(highs.size() - 2);
        double low_prev = lows.get(lows.size() - 2);

        double stopLoss, takeProfit;
        if ("BUY".equals(tradeSignal)) {
            stopLoss = low_prev;
            takeProfit = close_current + (close_current - stopLoss) * 1.5;
        } else if ("SELL".equals(tradeSignal)) {
            stopLoss = high_prev;
            takeProfit = close_current - (stopLoss - close_current) * 1.5;
        } else {
            return new double[]{0, 0};
        }
        stopLoss = Math.round(stopLoss * 100.0) / 100.0;
        takeProfit = Math.round(takeProfit * 100.0) / 100.0;
        return new double[]{stopLoss, takeProfit};
    }

}
