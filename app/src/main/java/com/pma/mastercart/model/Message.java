package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Message implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.shopSender, flags);
        dest.writeParcelable(this.userSender, flags);
        dest.writeString(this.message);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
        dest.writeParcelable(this.conversation, flags);
    }

    protected Message(Parcel in) {
        this.id = in.readInt();
        this.shopSender = in.readParcelable(Shop.class.getClassLoader());
        this.userSender = in.readParcelable(User.class.getClassLoader());
        this.message = in.readString();
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.conversation = in.readParcelable(Conversation.class.getClassLoader());
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
