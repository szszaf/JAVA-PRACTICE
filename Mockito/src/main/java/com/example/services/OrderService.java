package com.example.services;

import com.example.entities.Order;
import com.example.repositories.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository,
                        NotificationService notificationService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        this.paymentService = paymentService;
    }

    public boolean placeOrder(Order order) {
        orderRepository.save(order);

        boolean isPaymentSuccessful = paymentService.processPayment(order);
        if (isPaymentSuccessful) {
            order.setPaid(true);
            orderRepository.save(order);
            notificationService.confirm(order);
            return true;
        } else {
            return false;
        }
    }
}
