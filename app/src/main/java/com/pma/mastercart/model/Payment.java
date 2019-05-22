package com.pma.mastercart.model;

import java.util.Date;
import java.util.UUID;

public class Payment {

    private String id;
    private double amount;
    private Date date;

    public Payment(double amount, Date date) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
    }

    public Payment() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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
}
