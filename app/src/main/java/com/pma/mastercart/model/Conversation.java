package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Conversation implements Parcelable {
    private Long id;
    Shop shop;
    User initiator;
    User receiver;
    List<Message> messages;

    public Conversation(Long id, Shop shop, User initiator, User receiver, List<Message> messages) {
        this.id = id;
        this.shop = shop;
        this.initiator = initiator;
        this.receiver = receiver;
        this.messages = messages;
    }

    public Conversation() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.shop, flags);
        dest.writeParcelable(this.initiator, flags);
        dest.writeParcelable(this.receiver, flags);
        dest.writeTypedList(this.messages);
    }

    protected Conversation(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.shop = in.readParcelable(Shop.class.getClassLoader());
        this.initiator = in.readParcelable(User.class.getClassLoader());
        this.receiver = in.readParcelable(User.class.getClassLoader());
        this.messages = in.createTypedArrayList(Message.CREATOR);
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
