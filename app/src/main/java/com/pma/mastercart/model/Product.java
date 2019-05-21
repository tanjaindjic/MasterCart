package com.pma.mastercart.model;

public class Product {

    private int id;
    private int name;
    private int imageResource;
    private double price;
    private String description;
    private short onStock;
    private String size;
    private int discount;
    private boolean activ;
    private double rating;
    private short numberOfRatings;

    public Product(int id, int name, int imageResource, double price, String description, short onStock, String size, int discount, boolean activ, double rating, short numberOfRatings) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.price = price;
        this.description = description;
        this.onStock = onStock;
        this.size = size;
        this.discount = discount;
        this.activ = activ;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    public Product() {
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

    public short getOnStock() {
        return onStock;
    }

    public void setOnStock(short onStock) {
        this.onStock = onStock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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
}
