package com.pma.mastercart.model;

import java.util.Date;

public class Payment {

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
}
