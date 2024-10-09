package com.example.bot_binnance.controller;

import com.example.bot_binnance.common.CommonUtils;
import com.example.bot_binnance.dto.OrderDetailsDto;
import com.example.bot_binnance.dto.OrderRequestDto;
import com.example.bot_binnance.dto.ResultDto;
import com.example.bot_binnance.model.Order;
import com.example.bot_binnance.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            // Gọi service để xử lý đơn hàng
            Order order = orderService.placeOrder(orderRequestDto);
            ResultDto<Order> result = new ResultDto<Order>(200, "Save Order Suscess", order);
            return CommonUtils.RESULT_OK(result);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	ResultDto<String> result = new ResultDto<String>(500, "Save Order Not Fould", e.getMessage());
        	 return CommonUtils.RESULT_OK(result);
        }
    }
    
    @GetMapping("/{orderId}")
    public OrderDetailsDto getOrderDetails(@PathVariable String orderId, @RequestParam String userId) {
        try {
            return orderService.getOrderDetails(orderId, userId);
        } catch (RuntimeException e) {
            // Return an appropriate response status and message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
