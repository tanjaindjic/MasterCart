package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Conversation implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.reciever, flags);
        dest.writeParcelable(this.sender, flags);
        dest.writeTypedList(this.messages);
    }

    protected Conversation(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.reciever = in.readParcelable(Shop.class.getClassLoader());
        this.sender = in.readParcelable(User.class.getClassLoader());
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
