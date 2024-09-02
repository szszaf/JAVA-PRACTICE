package com.example.entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Long id;
    private LocalDate orderDate;
    private List<Item> itemList;
    private boolean isPaid;

    public Order(Long id, LocalDate orderDate, List<Item> itemList, boolean isPaid) {
        this.id = id;
        this.orderDate = orderDate;
        this.itemList = itemList;
        this.isPaid = isPaid;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
