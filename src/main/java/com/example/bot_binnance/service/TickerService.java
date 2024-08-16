package com.example.bot_binnance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.example.bot_binnance.common.BinanceRSI;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.common.TrendAnalysis;
import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.model.ActionLog;

@Component
public class TickerService {
	@Autowired
	LogService actionLogService;

	@Autowired
	ApiBinanceService binanceService;

	@Autowired
	TelegramBot telegramBot;

	public static String lastedevent = null;

	public TickerService() {
		// TODO Auto-generated constructor stub
		// klineStrem();
	}

	private static final Logger logger = LoggerFactory.getLogger(TickerService.class);

	public void processTickerEvent(Double currentPrice, List<Double> prices) throws Exception {
		if (prices.size() > 28) {
			int period = 14;
			double rsi = BinanceRSI.calculateBinanceRSI(prices, period);
			logger.error("rsi " + PrivateKeyBinnance.timeFrame + ": " + rsi + " price: " + currentPrice);
			logger.error("rsi before" + PrivateKeyBinnance.timeFrame + ": " + TrendAnalysis.rsi_before + " price: "
					+ TrendAnalysis.price_before);
			String decision = "";

			if (rsi <= 22 || rsi >= 78) {
				// telegramBot.sendMessage("1180457993", "rsi : " + rsi + " price: " +
				// currentPrice);
				TrendAnalysis.rsi_before = rsi;
				TrendAnalysis.price_before = currentPrice;
			}

			if (TrendAnalysis.rsi_before <= 22 && rsi > 33) {
				TrendAnalysis.rsi_before = 0d;
			}

			if (TrendAnalysis.rsi_before >= 78 && rsi < 67) {
				TrendAnalysis.rsi_before = 0d;
			}

			if (TrendAnalysis.rsi_before > 0d && rsi >= 67 && rsi <= 72) {
				decision = "SORT";
			}

			if (TrendAnalysis.rsi_before > 0d && rsi >= 28 && rsi <= 33) {
				decision = "LONG";
			}
			
			decision = "LONG";

			if ("LONG".equals(decision) || "SORT".equals(decision)) {
				// create MARKET
				TrendAnalysis.rsi_before = 0d;
				String side = decision.equals("LONG") ? "BUY" : "SELL";
				OrderDto orderDto = binanceService.createOrder(currentPrice, side, BinanceOrderType.MARKET, 0);
				OrderDto orderResult = binanceService.listOrder(orderDto.getOrderId()).get(0);
				currentPrice = Double.parseDouble(orderResult.getAvgPrice());
				logger.info("entry price : " +orderResult.getAvgPrice() );
				takeProfitAndStoploss(side, currentPrice, 350d, 350d);
			}

		}
	}
	// }

	public void updateStatus() throws Exception {

		List<ActionLog> logs = new ArrayList<>();

		logs = this.actionLogService.findRecentActionLogs();

		if (logs == null) {
			return;
		}
		this.binanceService.cancelOpenOrder();

		for (ActionLog log : logs) {
			OrderDto ord = this.binanceService.listOrder(Long.parseLong(log.getOrderId())).get(0);
			if (ord != null) {
				this.actionLogService.updateStatusByOrderId(String.valueOf(ord.getOrderId()), ord.getStatus());
			}
		}

	}

	public void takeProfitAndStoploss(String side, Double avgPrice, Double TP, Double SL) {
		if ("LONG".equals(side)) {
			// create TP
			binanceService.createOrder(avgPrice + TP, PrivateKeyBinnance.SELL, BinanceOrderType.TAKE_PROFIT, 0);
			// create SL
			binanceService.createOrder(avgPrice - SL, PrivateKeyBinnance.SELL, BinanceOrderType.STOP, 0);
		} else {
			// create TP
			binanceService.createOrder(avgPrice - TP, PrivateKeyBinnance.BUY, BinanceOrderType.TAKE_PROFIT, 0);
			// create SL
			binanceService.createOrder(avgPrice + SL, PrivateKeyBinnance.BUY, BinanceOrderType.STOP, 0);
		}

	}

//	 public void klineStrem() {
//		 
//		 System.out.println("***** begin ****** ");
//         UMWebsocketClientImpl client = new UMWebsocketClientImpl();
//         int streamID = client.klineStream("btcusdt","15m" ,((event)-> {
//             try {
//            	 lastedevent = event;
//             } catch (JSONException e) {
//                 throw new RuntimeException(e);
//             }
//         }));
//         
//         ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//         scheduler.scheduleAtFixedRate(() -> {
//             if (lastedevent != null) {
//            	 
//             }
//         }, 0, 20, TimeUnit.SECONDS);
//		 
//	 }

}
