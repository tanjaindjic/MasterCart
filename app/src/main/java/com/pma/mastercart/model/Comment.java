package com.pma.mastercart.model;

import java.util.Date;

public class Comment {
    private Long id;
    private Shop forShop;
    private Product forProduct;
    private String user;
    private String store;
    private String comment;
    private Date time;
    private int review;

    public Comment() {
    }

    public Comment(Long id, Shop forShop, Product forProduct, String user, String store, String comment, Date time, int review) {
        this.id = id;
        this.forShop = forShop;
        this.forProduct = forProduct;
        this.user = user;
        this.store = store;
        this.comment = comment;
        this.time = time;
        this.review = review;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getForShop() {
        return forShop;
    }

    public void setForShop(Shop forShop) {
        this.forShop = forShop;
    }

    public Product getForProduct() {
        return forProduct;
    }

    public void setForProduct(Product forProduct) {
        this.forProduct = forProduct;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }
}
