package com.pma.mastercart.model;

import com.google.maps.model.LatLng;

import java.util.ArrayList;

public class Shop {

    private Long id;
    private String name;
    private String imageResource;
    private String location;
    public double lat;
    public double lng;
    private String phone;
    private String email;
    private boolean active;
    private double rating;
    private int numberOfRatings;
    private ArrayList<Product> products;
    private ArrayList<User> seller;
    private ArrayList<Comment> comments;

    public Shop() {
    }

    public Shop(Long id, String name, String imageResource, String location, double lat, double lng, String phone, String email, boolean active, double rating, int numberOfRatings, ArrayList<Product> products, ArrayList<User> seller, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.email = email;
        this.active = active;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.products = products;
        this.seller = seller;
        this.comments = comments;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
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
