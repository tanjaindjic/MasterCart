package com.pma.mastercart.model;

import java.util.Date;
import java.util.UUID;

public class Message {
    private String id;
    private Object sender;
    private String message;
    private Date time;

    public Message(Object sender, String message, Date time) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public Message() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
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
}
