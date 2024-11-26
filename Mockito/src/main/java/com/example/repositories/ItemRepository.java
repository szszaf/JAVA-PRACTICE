package com.example.repositories;

import com.example.entities.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class ItemRepository {
    private final List<Item> items;
    private Long currentIndex;

    public ItemRepository() {
        items = new ArrayList<>();
        currentIndex = 1L;
    }

    public Item save(Item item) {
        item.setId(currentIndex++);
        items.add(item);
        return item;
    }

    public List<Item> saveAll(List<Item> items) {
        return LongStream.range(
                0,
               items.size()
        ).mapToObj(
                i -> {
                    Item itemToSave = items.get((int) i);
                    itemToSave.setId(currentIndex++);
                    return itemToSave;
                }
        ).toList();
    }

    public List<Item> findAll() {
        return items;
    }

    public Item findByName(String name) {
        return items.stream().filter(
                item ->
                        item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Item findById(Long id) {
        return items.stream().filter(
                item -> item.getId().equals(id)
        )
                .findFirst()
                .orElse(null);
    }
}
