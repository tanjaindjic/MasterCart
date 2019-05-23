package com.pma.mastercart.model;

import com.pma.mastercart.model.enums.Role;
import java.util.UUID;
import java.util.ArrayList;

public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Role role;
    private String imageResource;
    private ArrayList<Product> favorites;
    private Wallet wallet;
    private ArrayList<CartItem> cartItems;
    private ArrayList<Orders> orders;

    public User() {
    }

    public User(int id, String email, String firstName, String lastName, String address, String phone, Role role, String imageResource, ArrayList<Product> favorites, Wallet wallet, ArrayList<CartItem> cartItems, ArrayList<Orders> orders) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.imageResource = imageResource;
        this.favorites = favorites;
        this.wallet = wallet;
        this.cartItems = cartItems;
    }

    public int getId() {  return id;  }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public ArrayList<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Product> favorites) {
        this.favorites = favorites;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders) {
        this.orders = orders;
    }
}
