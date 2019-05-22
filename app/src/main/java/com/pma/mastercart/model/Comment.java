package com.pma.mastercart.model;

import java.util.UUID;

public class Comment {
    private String id;
    private String itemId; //id radnje ili proizvoda
    private String user;
    private String comment;
    private double rating;

    public Comment(String user, String itemId, String comment, double rating) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.itemId = itemId;
        this.comment = comment;
        this.rating = rating;
    }

    public Comment() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
