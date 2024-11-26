package com.example.entities;

public class Item {
    private Long id;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item(Item originalItem) {
        this.id = originalItem.id;
        this.name = originalItem.name;
    }

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item[id=" + id + ", name='" + name + "']";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return id.equals(item.id) && name.equals(item.name);
    }
}
