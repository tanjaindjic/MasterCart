package com.pma.mastercart.model;

import com.pma.mastercart.model.enums.StatusCartItem;

import java.util.UUID;

public class CartItem {
    private String id;
    private int quantity;
    private double total;
    private StatusCartItem statusCartItem;
    private Product item;

    public CartItem() {
        this.id = UUID.randomUUID().toString();
    }

    public CartItem(int quantity, double total, StatusCartItem statusCartItem, Product item) {
        this.id = UUID.randomUUID().toString();
        this.quantity = quantity;
        this.total = total;
        this.statusCartItem = statusCartItem;
        this.item = item;
    }

    public String getId() {
        return id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StatusCartItem getStatusCartItem() {
        return statusCartItem;
    }

    public void setStatusCartItem(StatusCartItem statusCartItem) {
        this.statusCartItem = statusCartItem;
    }

    public Product getItem() {
        return item;
    }

    public void setItem(Product item) {
        this.item = item;
    }
}
