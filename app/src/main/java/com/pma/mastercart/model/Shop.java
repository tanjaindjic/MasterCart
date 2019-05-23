package com.pma.mastercart.model;

import android.location.Location;

import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.UUID;

public class Shop {

    private int id;
    private String name;
    private String imageResource;
    private String location;
    private LatLng latlon; //za GoogleMaps
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

    public Shop(int id, String name, String imageResource, String location, LatLng latlon, String phone, String email, boolean active, double rating, int numberOfRatings, ArrayList<Product> products, ArrayList<User> seller, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.location = location;
        this.latlon = latlon;
        this.phone = phone;
        this.email = email;
        this.active = active;
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

    public LatLng getLatlon() {
        return latlon;
    }

    public void setLatlon(LatLng latlon) {
        this.latlon = latlon;
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
