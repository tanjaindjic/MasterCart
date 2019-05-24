package com.pma.mastercart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Payment implements Parcelable {

    private Long id;
    private double amount;
    private Date date;
    private Wallet wallet;

    public Payment() {
    }

    public Payment(Long id, double amount, Date date, Wallet wallet) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeDouble(this.amount);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeParcelable(this.wallet, flags);
    }

    protected Payment(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.amount = in.readDouble();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.wallet = in.readParcelable(Wallet.class.getClassLoader());
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel source) {
            return new Payment(source);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
}
