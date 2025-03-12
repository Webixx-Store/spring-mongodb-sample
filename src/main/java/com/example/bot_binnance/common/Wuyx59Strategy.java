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
    public static String checkTradeSignal(List<Double> prices) {
        if (prices.size() < 9) return "Không đủ dữ liệu để phân tích.";

        List<Double> ema5 = calculateEMA(prices, 5);
        List<Double> ema9 = calculateEMA(prices, 9);

        if (ema5.size() < 2 || ema9.size() < 2) return "Không đủ dữ liệu EMA.";

        double ema5_current = ema5.get(ema5.size() - 1);
        double ema5_prev = ema5.get(ema5.size() - 2);
        double ema9_current = ema9.get(ema9.size() - 1);
        double ema9_prev = ema9.get(ema9.size() - 2);
        
        double close_current = prices.get(prices.size() - 1);
        double close_prev = prices.get(prices.size() - 2);

        // Điều kiện BUY
        if (ema5_prev < ema9_prev && ema5_current > ema9_current && close_current > close_prev) {
            return "BUY";
        }
        // Điều kiện SELL
        else if (ema5_prev > ema9_prev && ema5_current < ema9_current && close_current < close_prev) {
            return "SELL";
        }
        return "No Action";
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
        return new double[]{stopLoss, takeProfit};
    }

}
