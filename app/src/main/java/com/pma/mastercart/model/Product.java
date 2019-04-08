package com.pma.mastercart.model;

public class Product {
    private final int name;
    private final int imageResource;
    private final int price;

    public Product(int name, int imageResource, int price) {
        this.name = name;
        this.imageResource = imageResource;
        this.price = price;
    }

    public int getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getPrice() {
        return price;
    }
}
