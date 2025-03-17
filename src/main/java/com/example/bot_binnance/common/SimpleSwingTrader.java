package com.example.bot_binnance.common;

import java.util.ArrayList;
import java.util.List;

import com.example.bot_binnance.dto.trade.CandleStick;

public class SimpleSwingTrader  {
    // Các tham số có thể tùy chỉnh
    private static final int CANDLES_CHECK = 100;
    private static final int RSI_PERIOD = 14;
    private static final int VOLUME_PERIOD = 20;
    private static final int ATR_PERIOD = 14;
    private static final double VOLUME_THRESHOLD = 1.5;
    private static final double RSI_OVERSOLD = 30;
    private static final double RSI_OVERBOUGHT = 70;
    
    public static class TradeSignal {
        private final String signal;
        private final String message;
        private final double entryPrice;
        private final double stopLoss;
        private final double takeProfit;
        private final double riskRewardRatio;
        private final MarketCondition marketCondition;

        public TradeSignal(String signal, String message, double entryPrice, 
                          double stopLoss, double takeProfit, 
                          double riskRewardRatio, MarketCondition marketCondition) {
            this.signal = signal;
            this.message = message;
            this.entryPrice = entryPrice;
            this.stopLoss = stopLoss;
            this.takeProfit = takeProfit;
            this.riskRewardRatio = riskRewardRatio;
            this.marketCondition = marketCondition;
        }

        @Override
        public String toString() {
            if (signal.equals("WAIT")) {
                return message;
            }
            return String.format("""
                Tín hiệu: %s
                %s
                Điều kiện thị trường: %s
                Giá vào lệnh: %.2f
                Stop Loss: %.2f
                Take Profit: %.2f
                Tỷ lệ Risk/Reward: 1:%.1f
                """, signal, message, marketCondition, entryPrice, stopLoss, takeProfit, riskRewardRatio);
        }

        // Getters
        public String getSignal() { return signal; }
        public String getMessage() { return message; }
        public double getEntryPrice() { return entryPrice; }
        public double getStopLoss() { return stopLoss; }
        public double getTakeProfit() { return takeProfit; }
        public double getRiskRewardRatio() { return riskRewardRatio; }
        public MarketCondition getMarketCondition() { return marketCondition; }
    }

    public enum MarketCondition {
        STRONG_UPTREND,
        WEAK_UPTREND,
        RANGING,
        WEAK_DOWNTREND,
        STRONG_DOWNTREND,
        UNKNOWN
    }

    private static class TechnicalIndicators {
        double rsi;
        double atr;
        double averageVolume;
        double ema20;
        double ema50;
        double ema200;
        boolean hasVolumeTrend;
        boolean hasPriceMomentum;
        MarketCondition marketCondition;
    }

    public TradeSignal analyzeMarket(List<CandleStick> candles) {
        if (candles.size() < CANDLES_CHECK) {
            return new TradeSignal("WAIT", "Không đủ dữ liệu để phân tích", 0, 0, 0, 0, MarketCondition.UNKNOWN);
        }

        TechnicalIndicators indicators = calculateIndicators(candles);
        double currentPrice = candles.get(candles.size() - 1).getClose();
        
        // Phân tích trend và momentum
        boolean isUptrend = isUptrend(indicators);
        boolean isDowntrend = isDowntrend(indicators);
        
        // Kiểm tra điều kiện vào lệnh
        if (shouldBuy(indicators, candles)) {
            double[] levels = calculateTradingLevels(candles, indicators, "BUY");
            double stopLoss = levels[0];
            double takeProfit = levels[1];
            double riskRewardRatio = Math.abs(takeProfit - currentPrice) / Math.abs(currentPrice - stopLoss);
            
            String message = generateBuySignalMessage(indicators, currentPrice);
            return new TradeSignal("BUY", message, currentPrice, stopLoss, takeProfit, 
                                 riskRewardRatio, indicators.marketCondition);
        }

        if (shouldSell(indicators, candles)) {
            double[] levels = calculateTradingLevels(candles, indicators, "SELL");
            double stopLoss = levels[0];
            double takeProfit = levels[1];
            double riskRewardRatio = Math.abs(currentPrice - takeProfit) / Math.abs(stopLoss - currentPrice);
            
            String message = generateSellSignalMessage(indicators, currentPrice);
            return new TradeSignal("SELL", message, currentPrice, stopLoss, takeProfit, 
                                 riskRewardRatio, indicators.marketCondition);
        }

        return new TradeSignal("WAIT", generateWaitMessage(indicators), 0, 0, 0, 0, indicators.marketCondition);
    }

    private TechnicalIndicators calculateIndicators(List<CandleStick> candles) {
        TechnicalIndicators indicators = new TechnicalIndicators();
        
        indicators.rsi = calculateRSI(candles, RSI_PERIOD);
        indicators.atr = calculateATR(candles, ATR_PERIOD);
        indicators.averageVolume = calculateAverageVolume(candles, VOLUME_PERIOD);
        indicators.ema20 = calculateEMA(candles, 20);
        indicators.ema50 = calculateEMA(candles, 50);
        indicators.ema200 = calculateEMA(candles, 200);
        indicators.hasVolumeTrend = checkVolumeTrend(candles);
        indicators.hasPriceMomentum = checkPriceMomentum(candles);
        indicators.marketCondition = determineMarketCondition(indicators);
        
        return indicators;
    }

    private double calculateRSI(List<CandleStick> candles, int period) {
        if (candles.size() < period + 1) return 50.0;

        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();

        for (int i = 1; i < candles.size(); i++) {
            double change = candles.get(i).getClose() - candles.get(i - 1).getClose();
            gains.add(Math.max(0, change));
            losses.add(Math.max(0, -change));
        }

        double avgGain = calculateSMA(gains, period);
        double avgLoss = calculateSMA(losses, period);

        for (int i = period + 1; i < gains.size(); i++) {
            avgGain = (avgGain * (period - 1) + gains.get(i)) / period;
            avgLoss = (avgLoss * (period - 1) + losses.get(i)) / period;
        }

        if (avgLoss == 0) return 100;
        double rs = avgGain / avgLoss;
        return 100 - (100 / (1 + rs));
    }

    private double calculateATR(List<CandleStick> candles, int period) {
        List<Double> trueRanges = new ArrayList<>();
        
        for (int i = 1; i < candles.size(); i++) {
            CandleStick current = candles.get(i);
            CandleStick previous = candles.get(i - 1);
            
            double tr1 = current.getHigh() - current.getLow();
            double tr2 = Math.abs(current.getHigh() - previous.getClose());
            double tr3 = Math.abs(current.getLow() - previous.getClose());
            
            trueRanges.add(Math.max(Math.max(tr1, tr2), tr3));
        }
        
        return calculateSMA(trueRanges, period);
    }

    private double calculateEMA(List<CandleStick> candles, int period) {
        if (candles.size() < period) return 0;

        double multiplier = 2.0 / (period + 1);
        double initialSMA = 0;
        
        for (int i = 0; i < period; i++) {
            initialSMA += candles.get(i).getClose();
        }
        initialSMA /= period;

        double ema = initialSMA;
        for (int i = period; i < candles.size(); i++) {
            ema = (candles.get(i).getClose() - ema) * multiplier + ema;
        }
        
        return ema;
    }

    private double calculateSMA(List<Double> values, int period) {
        if (values.size() < period) return 0;
        
        double sum = 0;
        for (int i = values.size() - period; i < values.size(); i++) {
            sum += values.get(i);
        }
        return sum / period;
    }

    private double calculateAverageVolume(List<CandleStick> candles, int period) {
        if (candles.size() < period) return 0;
        
        double sum = 0;
        for (int i = candles.size() - period; i < candles.size(); i++) {
            sum += candles.get(i).getVolume();
        }
        return sum / period;
    }

    private boolean checkVolumeTrend(List<CandleStick> candles) {
        double currentVolume = candles.get(candles.size() - 1).getVolume();
        double avgVolume = calculateAverageVolume(candles, VOLUME_PERIOD);
        return currentVolume > avgVolume * VOLUME_THRESHOLD;
    }

    private boolean checkPriceMomentum(List<CandleStick> candles) {
        int last = candles.size() - 1;
        return Math.abs(candles.get(last).getClose() - candles.get(last - 1).getClose()) > 
               calculateATR(candles, ATR_PERIOD);
    }

    private MarketCondition determineMarketCondition(TechnicalIndicators indicators) {
        double currentPrice = indicators.ema20;
        
        if (currentPrice > indicators.ema50 && indicators.ema50 > indicators.ema200) {
            return indicators.hasPriceMomentum ? MarketCondition.STRONG_UPTREND : MarketCondition.WEAK_UPTREND;
        } else if (currentPrice < indicators.ema50 && indicators.ema50 < indicators.ema200) {
            return indicators.hasPriceMomentum ? MarketCondition.STRONG_DOWNTREND : MarketCondition.WEAK_DOWNTREND;
        } else {
            return MarketCondition.RANGING;
        }
    }

    private boolean isUptrend(TechnicalIndicators indicators) {
        return indicators.ema20 > indicators.ema50 && indicators.ema50 > indicators.ema200;
    }

    private boolean isDowntrend(TechnicalIndicators indicators) {
        return indicators.ema20 < indicators.ema50 && indicators.ema50 < indicators.ema200;
    }

    private boolean shouldBuy(TechnicalIndicators indicators, List<CandleStick> candles) {
        return indicators.rsi < RSI_OVERSOLD &&
               indicators.hasVolumeTrend &&
               indicators.hasPriceMomentum &&
               (indicators.marketCondition == MarketCondition.WEAK_DOWNTREND || 
                indicators.marketCondition == MarketCondition.RANGING);
    }

    private boolean shouldSell(TechnicalIndicators indicators, List<CandleStick> candles) {
        return indicators.rsi > RSI_OVERBOUGHT &&
               indicators.hasVolumeTrend &&
               indicators.hasPriceMomentum &&
               (indicators.marketCondition == MarketCondition.WEAK_UPTREND || 
                indicators.marketCondition == MarketCondition.RANGING);
    }

    private double[] calculateTradingLevels(List<CandleStick> candles, 
                                          TechnicalIndicators indicators, 
                                          String signal) {
        double atr = indicators.atr;
        double currentPrice = candles.get(candles.size() - 1).getClose();
        double stopLoss, takeProfit;

        if (signal.equals("BUY")) {
            stopLoss = currentPrice - (atr * 2);
            takeProfit = currentPrice + (atr * 4);
        } else {
            stopLoss = currentPrice + (atr * 2);
            takeProfit = currentPrice - (atr * 4);
        }

        return new double[]{stopLoss, takeProfit};
    }

    private String generateBuySignalMessage(TechnicalIndicators indicators, double currentPrice) {
        return String.format("""
            Tín hiệu mua mạnh:
            - RSI: %.2f (oversold)
            - Volume tăng mạnh: %.2f%%
            - Momentum: Tích cực
            - ATR: %.2f
            """, 
            indicators.rsi,
            ((indicators.averageVolume / calculateAverageVolume(List.of(), VOLUME_PERIOD)) - 1) * 100,
            indicators.atr
        );
    }

    private String generateSellSignalMessage(TechnicalIndicators indicators, double currentPrice) {
        return String.format("""
            Tín hiệu bán mạnh:
            - RSI: %.2f (overbought)
            - Volume tăng mạnh: %.2f%%
            - Momentum: Tiêu cực
            - ATR: %.2f
            """,
            indicators.rsi,
            ((indicators.averageVolume / calculateAverageVolume(List.of(), VOLUME_PERIOD)) - 1) * 100,
            indicators.atr
        );
    }

    private String generateWaitMessage(TechnicalIndicators indicators) {
        return String.format("""
            Chưa có tín hiệu giao dịch:
            - RSI: %.2f
            - Trạng thái thị trường: %s
            - Độ biến động (ATR): %.2f
            """,
            indicators.rsi,
            indicators.marketCondition,
            indicators.atr
        );
    }
}