package com.pma.mastercart.model;

import java.util.ArrayList;

public class Favourites {
    private int id;
    private ArrayList<Product> prati;

    public Favourites(int id, ArrayList<Product> prati) {
        this.id = id;
        this.prati = prati;
    }

    public Favourites() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Product> getPrati() {
        return prati;
    }

    public void setPrati(ArrayList<Product> prati) {
        this.prati = prati;
    }
}
