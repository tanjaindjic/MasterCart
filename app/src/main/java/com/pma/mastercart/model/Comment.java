package com.pma.mastercart.model;

public class Comment {
    private int id;
    private int productId;
    private String user;
    private String comment;

    public Comment(int id, int productId, String user, String comment) {
        this.id = id;
        this.productId = productId;
        this.user = user;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
