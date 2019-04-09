package com.pma.mastercart.model;

public class Comment {
    private int id;
    private int product_id;
    private String user;
    private String review;

    public Comment(int id, int product_id, String user, String review) {
        this.id = id;
        this.product_id = product_id;
        this.user = user;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
