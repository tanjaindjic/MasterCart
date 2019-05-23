package com.pma.mastercart.model;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private int id;
    private int itemId; //id radnje ili proizvoda
    private String user;
    private String store;
    private String comment;
    private Date time;
    private int review;

    public Comment() {
    }

    public Comment(int id, int itemId, String user, String store, String comment, Date time, int review) {
        this.id = id;
        this.itemId = itemId;
        this.user = user;
        this.store = store;
        this.comment = comment;
        this.time = time;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
