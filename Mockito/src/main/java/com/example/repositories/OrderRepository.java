package com.example.repositories;

import com.example.entities.Order;

import java.util.List;

public interface OrderRepository {
    boolean save(Order order);
    List<Order> findAll();
    Order findById(int id);
}
