package com.example.bot_binnance.common;

import org.json.*;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;

import java.util.*;
import java.util.stream.*;

public class BinanceEMACrossOver {


    // Sample method to get candlestick data from Binance
    public static List<Candlestick> getCandlestickData(String symbol, String interval) {
   	 UMFuturesClientImpl client  = new UMFuturesClientImpl(
	           PrivateKeyBinnance.TESTNET_API_KEY,
	           PrivateKeyBinnance.TESTNET_SECRET_KEY,
	           PrivateKeyBinnance.TESTNET_BASE_URL);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("interval", interval);
        String result = client.market().klines(parameters);
        JSONArray jsonArray = new JSONArray(result);
        
        List<Candlestick> candlesticks = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray kline = jsonArray.getJSONArray(i);
            Candlestick candlestick = new Candlestick(
                    kline.getLong(0),    // Open time
                    kline.getString(1),  // Open
                    kline.getString(2),  // High
                    kline.getString(3),  // Low
                    kline.getString(4),  // Close
                    kline.getString(5),  // Volume
                    kline.getLong(6),    // Close time
                    kline.getString(7),  // Quote asset volume
                    kline.getInt(8),     // Number of trades
                    kline.getString(9),  // Taker buy base asset volume
                    kline.getString(10), // Taker buy quote asset volume
                    kline.getString(11)  // Ignore
            );
            candlesticks.add(candlestick);
        }
        
        return candlesticks;
    }

    // Example method to calculate EMA
    public static List<Double> calculateEMA(List<Double> closePrices, int period) {
        List<Double> emaValues = new ArrayList<>();
        double k = 2.0 / (period + 1);
        double ema = closePrices.get(0); // Start with the first close price as EMA
        
        for (int i = 1; i < closePrices.size(); i++) {
            ema = closePrices.get(i) * k + ema * (1 - k);
            emaValues.add(ema);
        }
        
        return emaValues;
    }

    public static void main(String[] args) {
        // Example usage
        String symbol = "BTCUSDT"; // Example symbol
        String interval = "15m"; // 15-minute interval
        
        // Assuming you have a Binance API client instance

        
        // Get candlestick data
        List<Candlestick> candlesticks = getCandlestickData(symbol, interval);
        
        // Extract close prices from candlestick data
        List<Double> closePrices = candlesticks.stream()
                .map(c -> Double.parseDouble(c.getClose()))
                .collect(Collectors.toList());
        
        // Calculate EMA values
        List<Double> ema10 = calculateEMA(closePrices, 10); // EMA10
        List<Double> ema50 = calculateEMA(closePrices, 50); // EMA50
        
        System.out.println(ema10.get(ema10.size()-1));
        System.out.println(ema50.get(ema50.size()-1));

    }

    // Example class to represent Candlestick data
    static class Candlestick {
        private final long openTime;
        private final String open;
        private final String high;
        private final String low;
        private final String close;
        private final String volume;
        private final long closeTime;
        private final String quoteAssetVolume;
        private final int numberOfTrades;
        private final String takerBuyBaseAssetVolume;
        private final String takerBuyQuoteAssetVolume;
        private final String ignore;

        public Candlestick(long openTime, String open, String high, String low, String close, String volume, long closeTime, String quoteAssetVolume, int numberOfTrades, String takerBuyBaseAssetVolume, String takerBuyQuoteAssetVolume, String ignore) {
            this.openTime = openTime;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.volume = volume;
            this.closeTime = closeTime;
            this.quoteAssetVolume = quoteAssetVolume;
            this.numberOfTrades = numberOfTrades;
            this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
            this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
            this.ignore = ignore;
        }

        public long getOpenTime() {
            return openTime;
        }

        public String getOpen() {
            return open;
        }

        public String getHigh() {
            return high;
        }

        public String getLow() {
            return low;
        }

        public String getClose() {
            return close;
        }

        public String getVolume() {
            return volume;
        }

        public long getCloseTime() {
            return closeTime;
        }

        public String getQuoteAssetVolume() {
            return quoteAssetVolume;
        }

        public int getNumberOfTrades() {
            return numberOfTrades;
        }

        public String getTakerBuyBaseAssetVolume() {
            return takerBuyBaseAssetVolume;
        }

        public String getTakerBuyQuoteAssetVolume() {
            return takerBuyQuoteAssetVolume;
        }

        public String getIgnore() {
            return ignore;
        }
    }



}
