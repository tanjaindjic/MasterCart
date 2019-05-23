package com.pma.mastercart.model.DTO;

import com.pma.mastercart.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListDTO {
    private ArrayList<Product> products;

    public ProductListDTO(ArrayList<Product> products) {
        this.products = products;
    }

    public ProductListDTO() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
