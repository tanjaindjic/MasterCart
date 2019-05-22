package com.pma.mastercart.model;

import java.util.ArrayList;
import java.util.UUID;

public class Wallet {

    private String id;
    private double balance;
    private ArrayList<Payment> history;

    public Wallet(double balance, ArrayList<Payment> history) {
        this.id = UUID.randomUUID().toString();
        this.balance = balance;
        this.history = history;
    }

    public Wallet() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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
}
