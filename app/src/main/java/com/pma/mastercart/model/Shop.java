package com.pma.mastercart.model;

public class Shop {

    private final int id;
    private final int name;
    private final int imageResource;
    private final int location;

    public Shop(int id, int name, int imageResource, int location) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.location = location;
    }

    public int getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
}