package com.example.repositories;

import com.example.entities.Item;

import java.util.List;

public interface ItemRepository {
    boolean save(Item item);
    List<Item> findAll();
    Item findByName(String name);
    Item findById(int id);
}
