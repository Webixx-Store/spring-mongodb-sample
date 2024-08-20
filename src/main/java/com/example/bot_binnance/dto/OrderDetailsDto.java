package com.example.bot_binnance.dto;

import com.example.bot_binnance.model.Order;
import com.example.bot_binnance.model.OrderDeli;
import com.example.bot_binnance.model.PaymentMethod;

public class OrderDetailsDto {

    private Order order;
    private OrderDeli orderDeli;
    private PaymentMethod paymentMethod;

    public OrderDetailsDto(Order order, OrderDeli orderDeli, PaymentMethod paymentMethod) {
        this.order = order;
        this.orderDeli = orderDeli;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderDeli getOrderDeli() {
        return orderDeli;
    }

    public void setOrderDeli(OrderDeli orderDeli) {
        this.orderDeli = orderDeli;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
