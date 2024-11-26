package com.example.services;

import com.example.entities.Item;
import java.util.List;

public interface ItemService {
    Item saveItem(String itemName);
    List<Item> saveItems(List<String> itemsNames);
    List<Item> getAllItems();
    Item getItem(String itemName);
    Item getItem(Long id);
}
