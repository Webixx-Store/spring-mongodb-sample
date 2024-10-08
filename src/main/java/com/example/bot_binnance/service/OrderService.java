package com.example.bot_binnance.service;

import com.example.bot_binnance.dto.OrderDetailsDto;
import com.example.bot_binnance.dto.OrderRequestDto;
import com.example.bot_binnance.model.Order;

public interface OrderService {
	Order placeOrder(OrderRequestDto orderRequestDto);
	public OrderDetailsDto getOrderDetails(String orderId, String userid);
}
