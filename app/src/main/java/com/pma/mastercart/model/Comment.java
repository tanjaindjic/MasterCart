package com.pma.mastercart.model;

public class Comment {
    private int id;
    private int itemId; //da bi se koristilo i za shop i product, samo za prvu kontrolnu tacku dok nemamo prave podatke
    private String user;
    private String comment;

    public Comment(int id, int itemId, String user, String comment) {
        this.id = id;
        this.itemId = itemId;
        this.user = user;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
