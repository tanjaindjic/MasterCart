package com.pma.mastercart.model;

import java.util.ArrayList;

public class Wallet {

    private int id;
    private double balance;
    private ArrayList<Payment> history;

    public Wallet(int id, double balance, ArrayList<Payment> history) {
        this.id = id;
        this.balance = balance;
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
