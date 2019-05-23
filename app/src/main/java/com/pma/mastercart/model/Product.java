package com.pma.mastercart.model;

import java.util.ArrayList;
import java.util.UUID;

public class Product {

    private int id;
    private String name;
    private String imageResource; //mozda path u storage?
    private double price;
    private String description;
    private int onStock;
    private String size;
    private int discount;
    private boolean active;
    private double rating;
    private int numberOfRatings;
    private ArrayList<Comment> comments;

    public Product(int id, String name, String imageResource, double price, String description, int onStock, String size, int discount, boolean active, double rating, int numberOfRatings, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.price = price;
        this.description = description;
        this.onStock = onStock;
        this.size = size;
        this.discount = discount;
        this.active = active;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.comments = comments;
    }

    public Product() {  }

    public int getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOnStock() {
        return onStock;
    }

    public void setOnStock(int onStock) {
        this.onStock = onStock;
    }

    public String getSize() {  return size;  }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
