package com.pma.mastercart.model;

import java.util.ArrayList;

public class Shop {

    private int id;
    private int name;
    private int imageResource;
    private String location;
    private String city;
    private String state;
    private short postcode;
    private String phone;
    private String email;
    private boolean isBaseStore;
    private boolean activ;
    private double rating;
    private short numberOfRatings;
    private ArrayList<Product> products;
    private ArrayList<User> seller;
    private ArrayList<Comment> comments;

    public Shop() {
    }

    public Shop(int id, int name, int imageResource, String location, String city, String state, short postcode, String phone, String email, boolean isBaseStore, boolean activ, double rating, short numberOfRatings, ArrayList<Product> products, ArrayList<User> seller, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.location = location;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.phone = phone;
        this.email = email;
        this.isBaseStore = isBaseStore;
        this.activ = activ;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.products = products;
        this.seller = seller;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public short getPostcode() {
        return postcode;
    }

    public void setPostcode(short postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBaseStore() {
        return isBaseStore;
    }

    public void setBaseStore(boolean baseStore) {
        isBaseStore = baseStore;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public short getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(short numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<User> getSeller() {
        return seller;
    }

    public void setSeller(ArrayList<User> seller) {
        this.seller = seller;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
