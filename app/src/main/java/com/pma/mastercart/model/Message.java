package com.pma.mastercart.model;

import java.util.Date;
import java.util.UUID;

public class Message {
    private int id;
    Shop shopSender;
    User userSender;
    private String message;
    private Date time;
    private Conversation conversation;

    public Message() {
    }

    public Message(int id, Shop shopSender, User userSender, String message, Date time, Conversation conversation) {
        this.id = id;
        this.shopSender = shopSender;
        this.userSender = userSender;
        this.message = message;
        this.time = time;
        this.conversation = conversation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getShopSender() {
        return shopSender;
    }

    public void setShopSender(Shop shopSender) {
        this.shopSender = shopSender;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
