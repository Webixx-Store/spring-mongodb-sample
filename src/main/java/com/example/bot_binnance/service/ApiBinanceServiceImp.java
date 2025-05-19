package com.example.bot_binnance.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.example.bot_binnance.client.impl.WebsocketClientImpl;
import com.example.bot_binnance.common.PrivateKeyBinnance;
import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.PriceDto;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.dto.TopLongSortAccountRatioDto;
import com.example.bot_binnance.dto.trade.CandleStick;
import com.example.bot_binnance.model.ActionLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ApiBinanceServiceImp implements ApiBinanceService {

	@Autowired
	LogService logService;

	// UMFuturesClientImpl client = new UMFuturesClientImpl(
	// PrivateKeyBinnance.API_KEY,
	// PrivateKeyBinnance.SECRET_KEY,
	// PrivateKeyBinnance.UM_BASE_URL);

	UMFuturesClientImpl client = new UMFuturesClientImpl(
			PrivateKeyBinnance.TESTNET_API_KEY,
			PrivateKeyBinnance.TESTNET_SECRET_KEY,
			PrivateKeyBinnance.TESTNET_BASE_URL);

	@Override
	public PriceDto getCurrentPrice() {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", "BTCUSDT");
		String result = client.market().tickerSymbol(parameters);
		Gson gson = new Gson();
		PriceDto price = gson.fromJson(result, PriceDto.class);
		return price;
	}

	@Override
	public List<TopLongSortAccountRatioDto> topLongSortAccountRatio(TimeFrame timeFrame) {
		UMFuturesClientImpl client1 = new UMFuturesClientImpl();
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("period", timeFrame.getValue()); // "5m","15m","30m","1h","2h","4h","6h","12h","1d"
		String result = client1.market().topTraderLongShortAccs(parameters);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<TopLongSortAccountRatioDto>>() {
		}.getType();
		List<TopLongSortAccountRatioDto> dtoList = gson.fromJson(result, listType);

		for (TopLongSortAccountRatioDto dto : dtoList) {
			String formattedDate = PrivateKeyBinnance.formatTimestamp(Long.parseLong(dto.getTimestamp()));
			dto.setTimestamp(formattedDate);
		}
		return dtoList;

	}

	@Override
	public OrderDto createOrder(double price, String side, BinanceOrderType type, long orderId) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		DecimalFormat decimalFormatQuantity = new DecimalFormat("#.###");
		DecimalFormat decimalFormatPrice = new DecimalFormat("#.##");

		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("side", side);
		parameters.put("quantity", decimalFormatQuantity.format(PrivateKeyBinnance.QUANTITY));
		parameters.put("type", type.getValue());


		if (BinanceOrderType.LIMIT.equals(type) || BinanceOrderType.STOP.equals(type) || BinanceOrderType.TAKE_PROFIT.equals(type)) {
			parameters.put("price", decimalFormatPrice.format(price));
			parameters.put("timeInForce", "GTC"); 
		}

		if (BinanceOrderType.STOP_MARKET.equals(type) ||
				BinanceOrderType.TAKE_PROFIT_MARKET.equals(type) ||
				BinanceOrderType.STOP.equals(type) ||
				BinanceOrderType.TAKE_PROFIT.equals(type)) {
			double stopPrice;
			if ("BUY".equalsIgnoreCase(side)) {
				if (BinanceOrderType.TAKE_PROFIT.equals(type) || BinanceOrderType.TAKE_PROFIT_MARKET.equals(type)) {
					stopPrice = price + 15;
				} else {
					stopPrice = price - 15;
				}
			} else {
				if (BinanceOrderType.TAKE_PROFIT.equals(type) || BinanceOrderType.TAKE_PROFIT_MARKET.equals(type)) {
					stopPrice = price - 15;
				} else {
					stopPrice = price + 15;
				}
			}
			parameters.put("stopPrice", decimalFormatPrice.format(stopPrice));
		}

		if (orderId != 0) {
			parameters.put("orderId", orderId);
		}
		parameters.put("newClientOrderId", "order-" + System.currentTimeMillis());
		String result = client.account().newOrder(parameters);
		Gson gson = new Gson();
		return gson.fromJson(result, OrderDto.class) ;
	}

	@Override
	public List<OrderDto> createLimitOrderWithTPAndSL(double entryPrice, double stopLossPrice, double takeProfitPrice,
			String side) {
		List<OrderDto> orders = new ArrayList<>();
		BinanceOrderType mainType = BinanceOrderType.LIMIT;
		OrderDto mainOrder = createOrder(entryPrice, side, mainType, 0);
		orders.add(mainOrder);
		String oppositeSide = side.equalsIgnoreCase("BUY") ? "SELL" : "BUY";
		OrderDto slOrder = createOrder(stopLossPrice, oppositeSide,
		BinanceOrderType.STOP, 0);
		orders.add(slOrder);
		OrderDto tpOrder = createOrder(takeProfitPrice, oppositeSide,
		BinanceOrderType.TAKE_PROFIT, 0);
		orders.add(tpOrder);
		return orders;
	}

	@Override
	public List<PositionDTO> positionInformation(String symbol) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		String result = client.account().positionInformation(parameters);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<PositionDTO>>() {
		}.getType();
		List<PositionDTO> dtoList = gson.fromJson(result, listType);
		return dtoList;
	}

	@Override
	public List<OrderDto> listOrder(long orderId) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("orderId", orderId);
		String result = client.account().allOrders(parameters);
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<OrderDto>>() {
		}.getType();
		List<OrderDto> dtoList = gson.fromJson(result, listType);
		return dtoList;
	}

	@Override
	public void cancelOpenOrder() {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		client.account().cancelAllOpenOrders(parameters);
	}

	@Override
	public List<Double> getClosePrices(String time) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("interval", time);
		parameters.put("limit", 200);
		String result = client.market().klines(parameters);
		JSONArray jsonArray = new JSONArray(result);
		List<Double> closePrices = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONArray kline = jsonArray.getJSONArray(i);
			if (i == 199) {
				closePrices.add(Double.parseDouble(getCurrentPrice().getPrice()));
			} else {
				closePrices.add(kline.getDouble(4));
			}

		}
		return closePrices;
	}

	@Override
	public List<List<Double>> getCloseHighLowPrices(String time) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("interval", time);
		parameters.put("limit", 200);
		String result = client.market().klines(parameters);
		JSONArray jsonArray = new JSONArray(result);

		List<Double> closePrices = new ArrayList<>();
		List<Double> highPrices = new ArrayList<>();
		List<Double> lowPrices = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONArray kline = jsonArray.getJSONArray(i);

			if (i == 199) {
				closePrices.add(Double.parseDouble(getCurrentPrice().getPrice()));
			} else {
				closePrices.add(kline.getDouble(4)); // Giá đóng
				highPrices.add(kline.getDouble(2)); // Giá cao nhất
				lowPrices.add(kline.getDouble(3)); // Giá thấp nhất
			}
		}

		// Trả về danh sách chứa các danh sách (closePrices, highPrices, lowPrices)
		return Arrays.asList(closePrices, highPrices, lowPrices);
	}

	@Override
	public String getBalance() throws JsonMappingException, JsonProcessingException {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		// Gọi API lấy thông tin tài khoản
		String result = client.account().accountInformation(parameters);

		// Chuyển đổi JSON thành Object
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(result);

		// Lọc số dư USDT từ danh sách assets
		JsonNode assets = rootNode.path("assets");
		String usdtBalance = "0";

		for (JsonNode asset : assets) {
			if (asset.path("asset").asText().equals("USDT")) {
				usdtBalance = asset.path("walletBalance").asText();
				break;
			}
		}

		// In ra số USDT hiện có
		System.out.println("Số dư USDT hiện có: " + usdtBalance);
		return "Số dư USDT hiện có: " + usdtBalance;

	}

	@Override
	public List<CandleStick> getCandleSticks(String time) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("symbol", PrivateKeyBinnance.SYMBOL);
		parameters.put("interval", time);
		parameters.put("limit", 200);

		String result = client.market().klines(parameters);
		JSONArray jsonArray = new JSONArray(result);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(result);

		List<CandleStick> candles = new ArrayList<>();
		for (JsonNode node : rootNode) {
			CandleStick candle = new CandleStick(
					node.get(0).asLong(), // openTime
					node.get(1).asDouble(), // open
					node.get(2).asDouble(), // high
					node.get(3).asDouble(), // low
					node.get(4).asDouble(), // close
					node.get(5).asDouble(), // volume
					node.get(6).asLong(), // closeTime
					node.get(7).asDouble(), // quoteAssetVolume
					node.get(8).asInt(), // numberOfTrades
					node.get(9).asDouble(), // takerBuyBaseAssetVolume
					node.get(10).asDouble() // takerBuyQuoteAssetVolume
			);
			candles.add(candle);
		}
		return candles;
	}

}
