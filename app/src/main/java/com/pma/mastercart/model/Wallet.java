package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Wallet implements Parcelable {

    private Long id;
    private double balance;
    private ArrayList<Payment> history;

    public Wallet(Long id, double balance, ArrayList<Payment> history) {
        this.id = id;
        this.balance = balance;
        this.history = history;
    }

    public Wallet() {  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Payment> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Payment> history) {
        this.history = history;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeDouble(this.balance);
        dest.writeList(this.history);
    }

    protected Wallet(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.balance = in.readDouble();
        this.history = new ArrayList<Payment>();
        in.readList(this.history, Payment.class.getClassLoader());
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
}
