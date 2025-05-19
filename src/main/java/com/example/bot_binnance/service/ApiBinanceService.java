package com.example.bot_binnance.service;

import java.util.List;

import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.PriceDto;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.dto.TopLongSortAccountRatioDto;
import com.example.bot_binnance.dto.trade.CandleStick;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;



public interface ApiBinanceService {
	

	PriceDto getCurrentPrice();
	public List<TopLongSortAccountRatioDto> topLongSortAccountRatio(TimeFrame timeFrame) ;
	OrderDto createOrder(double price, String side , BinanceOrderType type , long orderId);
	List<PositionDTO> positionInformation(String symbol);
	List<OrderDto> listOrder(long orderId);
	void cancelOpenOrder();
	List<Double> getClosePrices(String time);
	List<List<Double>> getCloseHighLowPrices(String time);
	String getBalance() throws JsonMappingException, JsonProcessingException;
	List<CandleStick> getCandleSticks(String time) throws Exception;
	public List<OrderDto> createLimitOrderWithTPAndSL(double entryPrice, double stopLossPrice, double takeProfitPrice,
			String side);
	

}
