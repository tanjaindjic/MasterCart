package com.pma.mastercart.model.DTO;

import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;

@Deprecated
public class ShopListDTO {
    private ArrayList<Shop> shops;

    public ShopListDTO(ArrayList<Shop> shops) {
        this.shops = shops;
    }

    public ShopListDTO() {
    }


    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }
}
