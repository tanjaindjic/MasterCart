package com.pma.mastercart.model;

import java.util.ArrayList;

public class ShopingCart {
    private int id;
    private ArrayList<CartItem> cartItems;

    public ShopingCart() {
    }

    public ShopingCart(int id, ArrayList<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
