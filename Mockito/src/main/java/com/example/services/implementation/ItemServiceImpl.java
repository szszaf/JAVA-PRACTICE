package com.example.services.implementation;

import com.example.entities.Item;
import com.example.repositories.ItemRepository;
import com.example.services.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item saveItem(String itemName) {
        Item item = new Item(itemName);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> saveItems(List<String> itemsNames) {
        return itemRepository.saveAll(
                itemsNames.stream().map(
                      itemName ->
                      {
                          Item item = new Item(itemName);
                          return item;
                      }
                ).toList()
        );
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItem(String itemName) {
        return itemRepository.findByName(itemName);
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id);
    }
}
