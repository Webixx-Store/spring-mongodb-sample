package com.example.bot_binnance.service;

import com.example.bot_binnance.dto.OrderRequestDto;
import com.example.bot_binnance.model.Order;
import com.example.bot_binnance.model.User;
import com.example.bot_binnance.model.Product;
import com.example.bot_binnance.model.PaymentMethod;
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
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public Order placeOrder(OrderRequestDto orderRequestDto) {
        // Validate User
        Optional<User> user = userRepository.findById(orderRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
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

        // Create and save the Order
        Order order = new Order();
        order.setCustomerId(orderRequestDto.getUserId());
        order.setItems(orderRequestDto.getItems());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setStatus(orderRequestDto.getStatus());
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setPaymentMethodId(paymentMethod.getId());

        // Calculate total amount if needed
        double totalAmount = orderRequestDto.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }


}
