package com.pma.mastercart.model;

import com.pma.mastercart.model.enums.StatusCartItem;

public class CartItem {
    private int id;
    private int quantity;
    private double total;
    private StatusCartItem statusCartItem;
    private Product item;

    public CartItem(int id, int quantity, double total, StatusCartItem statusCartItem, Product item) {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
        this.statusCartItem = statusCartItem;
        this.item = item;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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