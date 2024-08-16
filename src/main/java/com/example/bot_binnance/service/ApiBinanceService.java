package com.example.bot_binnance.service;

import java.util.List;

import com.example.bot_binnance.dto.BinanceOrderType;
import com.example.bot_binnance.dto.OrderDto;
import com.example.bot_binnance.dto.PositionDTO;
import com.example.bot_binnance.dto.PriceDto;
import com.example.bot_binnance.dto.TimeFrame;
import com.example.bot_binnance.dto.TopLongSortAccountRatioDto;



public interface ApiBinanceService {
	

	PriceDto getCurrentPrice();
	public List<TopLongSortAccountRatioDto> topLongSortAccountRatio(TimeFrame timeFrame) ;
	OrderDto createOrder(double price, String side , BinanceOrderType type , long orderId);
	List<PositionDTO> positionInformation(String symbol);
	List<OrderDto> listOrder(long orderId);
	void cancelOpenOrder();
	List<Double> getClosePrices(String time);
	

}
