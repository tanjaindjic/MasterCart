package com.pma.mastercart.model;

import java.util.List;

public class Conversation {
    private Long id;
    Shop reciever;
    User sender;
    List<Message> messages;

    public Conversation(Long id, Shop receiver, User sender, List<Message> messages) {
        this.id = id;
        this.reciever = receiver;
        this.sender = sender;
        this.messages = messages;
    }

    public Conversation() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getReciever() {
        return reciever;
    }

    public void setReciever(Shop reciever) {
        this.reciever = reciever;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
