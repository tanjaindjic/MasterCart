package com.pma.mastercart.model;

import java.util.ArrayList;

public class Wallet {

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
}
