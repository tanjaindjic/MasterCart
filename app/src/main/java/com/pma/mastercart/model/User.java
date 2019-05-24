package com.pma.mastercart.model;

import com.pma.mastercart.model.enums.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User  implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Role role;
    private String imageResource;
    private ArrayList<Product> favorites;
    private Wallet wallet;
    private ArrayList<CartItem> cartItems;
    private ArrayList<Order> orders;
    private List<Conversation> conversations;

    public User() {
    }

    public User(Long id, String email, String password, String firstName, String lastName, String address, String phone, Role role, String imageResource, ArrayList<Product> favorites, Wallet wallet, ArrayList<CartItem> cartItems, ArrayList<Order> orders, List<Conversation> conversations) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.imageResource = imageResource;
        this.favorites = favorites;
        this.wallet = wallet;
        this.cartItems = cartItems;
        this.conversations = conversations;
    }

    public Long getId() {  return id;  }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {  return password;  }

    public void setPassword(String password) {  this.password = password;   }

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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
