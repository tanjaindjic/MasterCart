package com.pma.mastercart.model;

import java.util.Date;
import java.util.UUID;

public class Message {
    private int id;
    private Object sender;
    private String message;
    private Date time;

    public Message() {
    }

    public Message(int id, Object sender, String message, Date time) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
