package com.example.services;

import com.example.entities.Order;

public interface PaymentService {
    boolean processPayment(Order order);
}
