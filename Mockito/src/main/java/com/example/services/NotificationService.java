package com.example.services;

import com.example.entities.Order;

public interface NotificationService {
    void confirm(Order order);
}
