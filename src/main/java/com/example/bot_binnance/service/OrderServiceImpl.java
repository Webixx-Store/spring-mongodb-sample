package com.example.bot_binnance.service;

import com.example.bot_binnance.dto.OrderDetailsDto;
import com.example.bot_binnance.dto.OrderRequestDto;
import com.example.bot_binnance.model.Order;
import com.example.bot_binnance.model.OrderDeli;
import com.example.bot_binnance.model.User;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.PaymentMethod;
import com.example.bot_binnance.repository.OrderDeliRepository;
import com.example.bot_binnance.repository.OrderRepository;
import com.example.bot_binnance.repository.UserRepository;
import com.example.bot_binnance.repository.ProductRepository;
import com.example.bot_binnance.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderDeliRepository orderDeliRepository;


    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public Order placeOrder(OrderRequestDto orderRequestDto) {
        // Validate User
    	
        Optional<User> user = userRepository.findByEmailOrId(orderRequestDto.getUserid()  , orderRequestDto.getUserid());
     
        
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        
        String userid = user.get().getId();
        
        if(orderRequestDto.getItems().size() == 0 ) {
        	throw new RuntimeException("Items not found");
        }

        // Validate Products
        orderRequestDto.getItems().forEach(item -> {
            Optional<Product> product = productRepository.findById(item.getProductId());
            if (!product.isPresent()) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
            if (product.get().getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + item.getProductId());
            }
            // Update stock after order
            product.get().setStock(product.get().getStock() - item.getQuantity());
            productRepository.save(product.get());
        });

        // Save Payment Method from DTO
        PaymentMethod paymentMethod = orderRequestDto.getPaymentMethod();
        if (paymentMethod != null) {
            paymentMethod.setCreatedAt(LocalDateTime.now());
            paymentMethodRepository.save(paymentMethod);
        } else {
            throw new RuntimeException("Payment method details are missing");
        }
        // Save Order deli from DTO	
        OrderDeli orderDeli = orderRequestDto.getOrderDeli();
        if (orderDeli != null) {
        	orderDeliRepository.save(orderDeli);
        } else {
            throw new RuntimeException("Order deli are missing");
        }
        // Save the Order
        // Create and save the Order
        Order order = new Order();
        order.setUserid(userid);
        order.setItems(orderRequestDto.getItems());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setStatus(orderRequestDto.getStatus());
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setPaymentMethodId(paymentMethod.getId());
        order.setOrderDeliId(orderDeli.getId());
        // Calculate total amount if needed
        double totalAmount = orderRequestDto.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }
    
    @Override
    public OrderDetailsDto getOrderDetails(String orderId, String userid) {
        // Fetch the Order with userid
        Optional<Order> orderOptional = orderRepository.findByIdAndUserid(orderId, userid);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Order not found or user does not have access to this order");
        }
        Order order = orderOptional.get();

        // Fetch the Delivery Details
        Optional<OrderDeli> orderDeliOptional = orderDeliRepository.findById(order.getOrderDeliId());
        if (!orderDeliOptional.isPresent()) {
            throw new RuntimeException("Order delivery details not found");
        }
        OrderDeli orderDeli = orderDeliOptional.get();

        // Fetch the Payment Method
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(order.getPaymentMethodId());
        if (!paymentMethodOptional.isPresent()) {
            throw new RuntimeException("Payment method details not found");
        }
        PaymentMethod paymentMethod = paymentMethodOptional.get();

        // Return the combined details
        return new OrderDetailsDto(order, orderDeli, paymentMethod);
    }



}
