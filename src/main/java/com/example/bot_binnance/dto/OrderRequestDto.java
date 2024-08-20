package com.example.bot_binnance.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.bot_binnance.model.OrderDeli;
import com.example.bot_binnance.model.PaymentMethod;

public class OrderRequestDto {
	private String id;

    private String userId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;
    private String shippingAddress;
    private String paymentMethodId;
    
    private PaymentMethod paymentMethod;
    
    private OrderDeli  orderDeli;
 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public OrderDeli getOrderDeli() {
		return orderDeli;
	}

	public void setOrderDeli(OrderDeli orderDeli) {
		this.orderDeli = orderDeli;
	}
    
    
}
